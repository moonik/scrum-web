package scrumweb.issue.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import scrumweb.dto.issue.IssueCommentDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import scrumweb.dto.issue.IssueDetailsDto;
import scrumweb.dto.issue.IssueTypeDto;
import scrumweb.issue.service.IssueService;

import java.util.List;
import java.util.Set;

import static scrumweb.common.ApplicationConstants.API_URL;

@RestController
@RequestMapping(API_URL + "project/issue")
@AllArgsConstructor
public class IssueController {

    private IssueService issueService;

    @PostMapping("/{projectKey}")
    @ResponseStatus(HttpStatus.OK)
    public IssueDetailsDto createIssue(@PathVariable String projectKey, @RequestBody IssueDetailsDto issueDetailsDto) {
        return issueService.create(issueDetailsDto, issueDetailsDto.getFieldsContentCollector().extractFields(), projectKey);
    }

    @GetMapping("/details/{issueKey}")
    @ResponseStatus(HttpStatus.OK)
    public IssueDetailsDto getDetails(@PathVariable String issueKey) {
        return issueService.getDetails(issueKey);
    }

    @PostMapping("/types/{projectKey}")
    public Set<IssueTypeDto> createIssueType(@PathVariable String projectKey, @RequestBody Set<IssueTypeDto> issueTypes) {
        return issueService.createIssueType(projectKey, issueTypes);
    }

    @PutMapping("/edit/types")
    public void editIssueType(@RequestBody IssueTypeDto issueTypeDto) {
        issueService.editIssueType(issueTypeDto);
    }

    @DeleteMapping("/types/{id}")
    public void deleteIssueType(@PathVariable Long id) {
        issueService.deleteIssueType(id);
    }

    @PostMapping("/{issueKey}/assign/{username}")
    @ResponseStatus(HttpStatus.OK)
    public void assignToIssue(@PathVariable String issueKey, @PathVariable String username) {
        issueService.assignToIssue(issueKey, username);
    }

    @DeleteMapping("/{issueKey}/assign/{username}")
    @ResponseStatus(HttpStatus.OK)
    public void unAssignFromIssue(@PathVariable String issueKey, @PathVariable String username) {
        issueService.unAssignFromIssue(issueKey, username);
    }

    @PostMapping("/comment/{issueKey}")
    @ResponseStatus(HttpStatus.OK)
    public IssueCommentDto addComment(@PathVariable String issueKey, @RequestBody IssueCommentDto issueCommentDto) {
        return issueService.addComment(issueCommentDto, issueKey);
    }

    @GetMapping("/comments/{issueKey}")
    @ResponseStatus(HttpStatus.OK)
    public List<IssueCommentDto> getComments(@PathVariable String issueKey){
        return issueService.getComments(issueKey);
    }

    @DeleteMapping("/comments/delete/{commentId}/{issueKey}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable Long commentId){
        issueService.deleteComment(commentId);
    }

    @PostMapping("/comments/edit/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public String editComment(@PathVariable Long commentId, @RequestBody IssueCommentDto issueCommentDto){
        return issueService.editComment(commentId, issueCommentDto.getContent());
    }


}
