package scrumweb.issue.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import scrumweb.common.SecurityContextService;
import scrumweb.dto.IssueDto;
import scrumweb.issue.repository.IssueRepository;
import scrumweb.user.account.domain.UserAccount;

@Service
@AllArgsConstructor
public class IssueService {
    private IssueRepository issueRepository;
    private SecurityContextService securityContextService;

    public IssueDto create(IssueDto issueDto) {
        final UserAccount reporter = securityContextService.getCurrentUserAccount();

    }
}
