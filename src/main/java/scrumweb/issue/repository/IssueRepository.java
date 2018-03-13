package scrumweb.issue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import scrumweb.issue.domain.Issue;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long>{

    @Query(value = "SELECT i FROM Issue i WHERE i.key LIKE %:param%")
    List<Issue> findIssuesByKeyQuery(@Param("param") String param);


}
