package scrumweb.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scrumweb.project.field.CheckBox;

@Repository
public interface CheckBoxRepository extends JpaRepository<CheckBox, Long> {
}
