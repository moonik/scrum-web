package scrumweb.issue.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
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
    public String addComment(@PathVariable Long id, @RequestBody String content) {
        issueService.addComment(content, id);
        return content;
    }

    @GetMapping("/showcomments/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<IssueCommentDto> showComments(@PathVariable Long id){
        return issueService.getCommentsForIssue(id);
    }

}
