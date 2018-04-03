package scrumweb.issue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scrumweb.issue.domain.IssueComment;

@Repository
public interface IssueCommentRepository extends JpaRepository<IssueComment, Long> {
}
