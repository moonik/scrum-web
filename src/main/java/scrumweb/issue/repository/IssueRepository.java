package scrumweb.issue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scrumweb.issue.domain.Issue;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long>{
}
