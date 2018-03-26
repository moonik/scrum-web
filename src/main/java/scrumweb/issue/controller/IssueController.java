package scrumweb.issue.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import scrumweb.dto.fieldcontent.FieldsContentCollector;
import scrumweb.dto.issue.IssueCommentDto;
import scrumweb.dto.issue.IssueDetailsDto;
import scrumweb.dto.issue.IssueDto;
import scrumweb.issue.service.IssueService;

import java.util.List;

import static scrumweb.common.ApplicationConstants.API_URL;

@RestController
@RequestMapping(API_URL + "project/issue")
@AllArgsConstructor
public class IssueController {

    private IssueService issueService;
    private FieldsContentCollector fieldsContentCollector;

    @PostMapping("/{projectKey}")
    @ResponseStatus(HttpStatus.OK)
    public IssueDetailsDto createIssue(@PathVariable String projectKey, @RequestBody IssueDetailsDto issueDetailsDto) {
        return issueService.create(issueDetailsDto, fieldsContentCollector.extractFields(issueDetailsDto.getProjectFields()), projectKey);
    }

    @GetMapping("/details/{id}")
    @ResponseStatus(HttpStatus.OK)
    public IssueDetailsDto getDetails(@PathVariable Long id) {
        return issueService.getDetails(id);
    }

    @PostMapping("/addcomment/{id}")
    @ResponseStatus(HttpStatus.OK)
    public IssueCommentDto addComment(@PathVariable Long id, @RequestBody IssueCommentDto issueCommentDto) {
        return issueService.addComment(issueCommentDto, id);
    }

    @GetMapping("/showcomments/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<IssueCommentDto> showComments(@PathVariable Long id){
        return issueService.getCommentsForIssue(id);
    }

    @DeleteMapping("/comments/delete/{commentId}/{issueId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable Long commentId, @PathVariable Long issueId){
        issueService.deleteComment(commentId, issueId);
    }

    @PostMapping("/comments/edit/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public String editComment(@PathVariable Long commentId, @RequestBody String content){
        return issueService.editComment(commentId, content);
    }

}
