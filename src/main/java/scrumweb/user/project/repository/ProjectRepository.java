package scrumweb.user.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scrumweb.user.project.domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findByName(String name);
}
