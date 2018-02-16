package scrumweb.security.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scrumweb.security.domain.Authority;
import scrumweb.security.domain.AuthorityName;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority,Long>{

    Authority findByName(AuthorityName authorityName);
}
