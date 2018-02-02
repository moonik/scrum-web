package scrumweb.projectfield.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scrumweb.projectfield.domain.CheckBox;

@Repository
public interface CheckBoxRepository extends JpaRepository<CheckBox, Long> {
}
