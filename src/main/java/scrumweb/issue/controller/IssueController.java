package scrumweb.issue.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import scrumweb.dto.fieldcontent.FieldsContentCollector;
import scrumweb.dto.issue.IssueDetailsDto;
import scrumweb.issue.service.IssueService;

import static scrumweb.common.ApplicationConstants.API_URL;

@RestController
@RequestMapping(API_URL + "issue")
@AllArgsConstructor
public class IssueController {

    private IssueService issueService;
    private FieldsContentCollector fieldsContentCollector;

    @PostMapping("/project/{id}")
    public IssueDetailsDto createIssue(@PathVariable Long id, @RequestBody IssueDetailsDto issueDetailsDto) {
        return issueService.create(issueDetailsDto, fieldsContentCollector.extractFields(issueDetailsDto.getProjectFields()), id);
    }

    @GetMapping("/{id}")
    public IssueDetailsDto getIssue(@PathVariable Long id) {
        return issueService.getIssue(id);
    }
}
