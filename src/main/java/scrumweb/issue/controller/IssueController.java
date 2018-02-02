package scrumweb.issue.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scrumweb.common.SecurityContextService;
import scrumweb.common.asm.IssueAsm;
import scrumweb.common.asm.UserProfileAsm;
import scrumweb.dto.issue.IssueDetailsDto;
import scrumweb.issue.repository.IssueRepository;
import scrumweb.issue.repository.IssueTypeRepository;
import scrumweb.issue.service.IssueService;
import scrumweb.project.repository.ProjectRepository;
import scrumweb.projectfield.repository.ProjectFieldRepository;
import scrumweb.user.account.repository.UserAccountRepository;

import static scrumweb.common.ApplicationConstants.API_URL;

@RestController
@RequestMapping(API_URL + "issue")
public abstract class IssueController extends IssueService {

    @PostMapping("/project/{id}")
    public IssueDetailsDto createIssue(@PathVariable Long id, @RequestBody IssueDetailsDto issueDetailsDto) {
        return create(issueDetailsDto, id);
    }

    @GetMapping("/{id}")
    public IssueDetailsDto getIssue(@PathVariable Long id) {
        return getIssue(id);
    }
}
