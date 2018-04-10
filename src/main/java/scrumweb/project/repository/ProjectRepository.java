package scrumweb.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import scrumweb.project.domain.Project;
import scrumweb.user.account.domain.UserAccount;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findByKey(String key);

    @Query("SELECT p FROM Project p WHERE p.key LIKE %:param% OR p.name LIKE %:param%")
    List<Project> findProjectsByKeyQuery(@Param("param") String param);

    @Query("SELECT p FROM Project p JOIN p.members members WHERE :user IN (members)")
    List<Project> findProjects(@Param("user") UserAccount user);
}