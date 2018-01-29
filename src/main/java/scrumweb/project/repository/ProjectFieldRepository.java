package scrumweb.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scrumweb.project.field.ProjectField;

@Repository
public interface ProjectFieldRepository extends JpaRepository<ProjectField, Long> {
}
