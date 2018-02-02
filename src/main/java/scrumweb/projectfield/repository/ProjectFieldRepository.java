package scrumweb.projectfield.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scrumweb.projectfield.domain.ProjectField;

@Repository
public interface ProjectFieldRepository extends JpaRepository<ProjectField, Long> {
}
