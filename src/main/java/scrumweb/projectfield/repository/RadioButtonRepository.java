package scrumweb.projectfield.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scrumweb.projectfield.domain.RadioButton;

@Repository
public interface RadioButtonRepository extends JpaRepository<RadioButton, Long> {
}
