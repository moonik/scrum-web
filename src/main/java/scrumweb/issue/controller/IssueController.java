package scrumweb.issue.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import scrumweb.dto.fieldcontent.FieldsContentCollector;
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

    @PostMapping("/{id}/assign/{username}")
    public ResponseEntity<?> assignToIssue(@PathVariable Long id, @PathVariable String username) {
        return ResponseEntity.status(issueService.assignToIssue(id, username)).build();
    }

//    @PostMapping("/{id}/request/{username}/accept")
//    public ResponseEntity<?> acceptAssignRequest(@PathVariable Long id, @PathVariable String username) {
//        return ResponseEntity.status(issueService.acceptAssignRequest(id, username)).build();
//    }
//
//    @DeleteMapping("/{id}/request/{username}/decline")
//    public ResponseEntity<?>declineAssignRequest(@PathVariable Long id, @PathVariable String username) {
//        return ResponseEntity.status(issueService.declineAssignRequest(id, username)).build();
//    }

}
