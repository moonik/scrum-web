package scrumweb.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scrumweb.project.field.RadioButton;

@Repository
public interface RadioButtonRepository extends JpaRepository<RadioButton, Long> {
}