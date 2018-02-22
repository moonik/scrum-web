package scrumweb.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import scrumweb.project.domain.Project;


import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findByKey(String key);

    @Query(value = "SELECT p FROM Project p WHERE p.key LIKE %:param% OR p.name LIKE %:param%")
    List<Project> findProjectsByKeyQuery(@Param("param")String param);

}

