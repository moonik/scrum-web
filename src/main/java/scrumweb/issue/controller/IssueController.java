package scrumweb.issue.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scrumweb.dto.ProjectFieldDto;
import scrumweb.issue.service.IssueService;

import java.util.Set;

import static scrumweb.common.ApplicationConstants.API_URL;

@RestController
@RequestMapping(API_URL + "issue")
@AllArgsConstructor
public class IssueController {

    private IssueService issueService;

    @PostMapping("/type/{type}")
    public void createFieldsForType(@PathVariable String type, @RequestBody Set<ProjectFieldDto> projectFieldsDto) {
        issueService.createFields(projectFieldsDto, type);
    }
}
