package scrumweb.issue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scrumweb.issue.fieldcontent.FieldContent;

@Repository
public interface FieldContentRepository extends JpaRepository<FieldContent, Long> {
}
