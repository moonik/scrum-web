package scrumweb.issue.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scrumweb.dto.IssueDetailsDto;
import scrumweb.issue.service.IssueService;

import static scrumweb.common.ApplicationConstants.API_URL;

@RestController
@RequestMapping(API_URL + "issue")
@AllArgsConstructor
public class IssueController {

    private IssueService issueService;

    @PostMapping("/type/{type}")
    public void createFieldsForType(@PathVariable String type, @RequestBody FieldsDto fieldsDto) {
        issueService.createFields(fieldsDto, type);
    }

    @PostMapping("/project/{id}")
    public IssueDetailsDto createIssue(@PathVariable Long id, @RequestBody IssueDetailsDto issueDetailsDto) {
        return issueService.create(issueDetailsDto, id);
    }

    @GetMapping("/{id}")
    public IssueDetailsDto getIssue(@PathVariable Long id) {
        return issueService.getIssue(id);
    }
}
