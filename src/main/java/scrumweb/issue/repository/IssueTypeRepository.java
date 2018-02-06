package scrumweb.issue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scrumweb.issue.domain.IssueType;


@Repository
public interface IssueTypeRepository extends JpaRepository<IssueType, Long> {
    IssueType findByName(String name);
}
