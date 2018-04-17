package scrumweb.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import scrumweb.project.domain.ProjectMember;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
}
