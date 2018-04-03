package scrumweb.issue.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import scrumweb.common.SecurityContextService;
import scrumweb.common.asm.IssueAsm;
import scrumweb.common.asm.IssueCommentAsm;
import scrumweb.common.asm.UserProfileAsm;
import scrumweb.common.asm.fieldcontent.FieldContentConverter;
import scrumweb.dto.fieldcontent.FieldContentDto;
import scrumweb.dto.issue.IssueCommentDto;
import scrumweb.dto.issue.IssueDetailsDto;
import scrumweb.dto.issue.IssueTypeDto;
import scrumweb.dto.user.UserProfileDto;
import scrumweb.exception.CantAssignToIssueException;
import scrumweb.issue.domain.Issue;
import scrumweb.issue.domain.IssueComment;
import scrumweb.issue.domain.IssueType;
import scrumweb.issue.fieldcontent.FieldContent;
import scrumweb.issue.repository.IssueCommentRepository;
import scrumweb.issue.repository.IssueRepository;
import scrumweb.issue.repository.IssueTypeRepository;
import scrumweb.project.domain.Project;
import scrumweb.projectfield.repository.ProjectFieldRepository;
import scrumweb.project.repository.ProjectRepository;
import scrumweb.user.account.domain.UserAccount;
import scrumweb.user.account.repository.UserAccountRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class IssueService {

    private IssueRepository issueRepository;
    private UserAccountRepository userAccountRepository;
    private SecurityContextService securityContextService;
    private ProjectFieldRepository projectFieldRepository;
    private ProjectRepository projectRepository;
    private FieldContentConverter fieldContentAsm;
    private IssueTypeRepository issueTypeRepository;
    private IssueCommentRepository issueCommentRepository;

    public IssueDetailsDto create(IssueDetailsDto issueDetailsDto, Set<FieldContentDto> fieldContentsDto, String projectKey) {
        final Project project = projectRepository.findByKey(projectKey);
        Set<Issue> issues = project.getIssues();
        String issueKey = project.getKey().concat("-").concat(project.getIssues().size() + 1 + "");
        issues.add(createIssue(issueDetailsDto, fieldContentsDto, issueKey, project));
        projectRepository.saveAndFlush(project);
        return issueDetailsDto;
    }

    protected Issue createIssue(IssueDetailsDto issueDetailsDto, Set<FieldContentDto> fieldContentsDto, String issueKey, Project project) {
        final UserAccount reporter = securityContextService.getCurrentUserAccount();
        Set<UserAccount> assignees = new HashSet<>();
        if (!issueDetailsDto.getAssignees().isEmpty()) {
            assignees = userAccountRepository.findUsers(extractUserNames(issueDetailsDto.getAssignees()));
        }
        Set<FieldContent> fieldContents = extractContents(fieldContentsDto);
        IssueType issueType = getIssueType(project.getIssueTypes(), issueDetailsDto.getIssueType());
        Issue issue = IssueAsm.createIssueEntityObject(issueDetailsDto, assignees, reporter, fieldContents, issueType);
        issue.setKey(issueKey);
        return issue;
    }

    public IssueDetailsDto getDetails(String issueKey) {
        Issue issue = issueRepository.findIssueByKey(issueKey);
        Set<FieldContentDto> fieldsContentsDto = extractConents(issue.getFieldContents());
        IssueDetailsDto issueDetailsDto = IssueAsm.createIssueDetailsDto(issue);
        issueDetailsDto.setFieldContents(fieldsContentsDto);
        return issueDetailsDto;
    }

    public Set<IssueTypeDto> createIssueType(String projectKey, Set<IssueTypeDto> issueTypes) {
        Project project = projectRepository.findByKey(projectKey);
        Set<IssueType> issueTypesToBeSaved = project.getIssueTypes();
        issueTypesToBeSaved.addAll(convertIssueTypes(issueTypes, project));
        projectRepository.save(project);
        return convertIssueTypes(issueTypesToBeSaved);
    }

    private Set<IssueType> convertIssueTypes(Set<IssueTypeDto> issueTypes, Project project) {
        Set<IssueType> issueTypesEntities = new HashSet<>();
        issueTypes.forEach(type -> {
            if (type.getId() != null && !type.getIsDefault()) {
                IssueType issueType = issueTypeRepository.findOne(type.getId());
                issueType.edit(type.getIssueType());
                issueTypesEntities.add(issueType);
            } else
                issueTypesEntities.add(new IssueType(type.getIssueType(), project, false));
        });
        return issueTypesEntities;
    }

    private Set<IssueTypeDto> convertIssueTypes(Collection<IssueType> issueTypes) {
        return issueTypes.stream()
                .map(type -> new IssueTypeDto(type.getId(), type.getName(), type.getIsDefault()))
                .collect(Collectors.toSet());
    }

    private Set<String> extractUserNames(Set<UserProfileDto> userProfileDtos) {
        return userProfileDtos.stream()
            .map(UserProfileDto::getUsername)
            .collect(Collectors.toSet());
    }

    private Set<FieldContent> extractContents(Set<FieldContentDto> fieldContentsDto) {
        return fieldContentsDto.stream()
            .map(fieldContentDto -> fieldContentAsm.createObjectEntity(
                projectFieldRepository.findOne(fieldContentDto.getProjectFieldId()), fieldContentDto))
            .collect(Collectors.toSet());
    }

    private Set<FieldContentDto> extractConents(Set<FieldContent> fieldContents) {
        return fieldContents.stream()
                .map(fieldContent -> fieldContentAsm.createDtoObject(fieldContent))
                .collect(Collectors.toSet());
    }

    private IssueType getIssueType(Set<IssueType> issueTypes, String issueType) {
        return issueTypes.stream()
            .filter(i -> i.getName().equalsIgnoreCase(issueType))
            .findFirst()
            .orElse(null);
    }

    private boolean checkIfMember(String username, Issue issue) {
        return projectRepository.findAll().stream()
            .filter(p -> p.getIssues().contains(issue))
            .reduce((a, b) -> null)
            .map(project -> project.getMembers().stream()
                .map(m -> m.getUserAccount().getUsername())
                .collect(Collectors.toList()).contains(username))
            .orElse(false);
    }

    public void assignToIssue(String issueKey, String username) {
        Issue issue = issueRepository.findIssueByKey(issueKey);
        UserAccount user = userAccountRepository.findByUsername(username);

        if (checkIfMember(username, issue)) {
            issue.getAssignees().add(user);
            issueRepository.save(issue);
        } else {
            throw new CantAssignToIssueException();
        }
    }

    public void unAssignFromIssue(String issueKey, String username) {
        Issue issue = issueRepository.findIssueByKey(issueKey);
        UserAccount user = userAccountRepository.findByUsername(username);
        if (checkIfMember(username, issue)) {
            issue.getAssignees().remove(user);
            issueRepository.save(issue);
        } else {
            throw new CantAssignToIssueException(username);
        }
    }

    public void editIssueType(IssueTypeDto issueTypeDto) {
        IssueType issueType = issueTypeRepository.findOne(issueTypeDto.getId());
        if (!issueType.getIsDefault()) {
            issueType.edit(issueTypeDto.getIssueType());
            issueTypeRepository.saveAndFlush(issueType);
        }
    }

    public void deleteIssueType(Long id) {
        IssueType issueType = issueTypeRepository.findOne(id);
        if (!issueType.getIsDefault()) {
            issueTypeRepository.delete(issueType);
        }
    }
    public IssueCommentDto addComment(IssueCommentDto issueCommentDto, Long id){
        Issue issue = issueRepository.findOne(id);
        UserAccount commentOwner = securityContextService.getCurrentUserAccount();

        List<IssueComment> comments = issue.getComments();

        IssueComment issueComment = new IssueComment(commentOwner, issueCommentDto.getContent(), LocalDateTime.now(), issue);

        comments.add(issueComment);
        issue.setComments(comments);
        issueCommentRepository.save(comments);

        return IssueCommentAsm.createDtoObject(issueComment, UserProfileAsm.makeUserProfileDto(commentOwner));
    }

    public List<IssueCommentDto> getCommentsForIssue(Long id){
        Issue issue = issueRepository.findOne(id);

        return issue.getComments().stream()
                .map(comment -> IssueCommentAsm.createDtoObject(comment, UserProfileAsm.makeUserProfileDto(comment.getOwner())))
                .collect(Collectors.toList());

    }

    public void deleteComment(Long commentId, Long issueId) {
        Issue issue = issueRepository.findOne(issueId);
        List<IssueComment> comments = issue.getComments().stream().filter(c -> !c.getId().equals(commentId)).collect(Collectors.toList());
        issue.setComments(comments);
        issueRepository.saveAndFlush(issue);
        issueCommentRepository.delete(commentId);

    }

    public String editComment(Long commentId, String content) {
        IssueComment comment = issueCommentRepository.findOne(commentId);
        comment.setContent(content);
        issueCommentRepository.save(comment);

        return content;
    }
}
