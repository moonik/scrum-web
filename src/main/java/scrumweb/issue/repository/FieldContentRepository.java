package scrumweb.issue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scrumweb.project.field.ProjectField;

@Repository
public interface FieldContentRepository extends JpaRepository<ProjectField, Long> {
}
