package scrumweb.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scrumweb.dto.project.ProjectDto;
import scrumweb.project.domain.Project;
import scrumweb.user.account.domain.UserAccount;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findByKey(String key);
    List<Project> findProjectsByOwner(UserAccount userAccount);
}
