package scrumweb.projectfield.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import scrumweb.projectfield.domain.RadioButton;

import java.util.Set;

@Repository
public interface RadioButtonRepository extends JpaRepository<RadioButton, Long> {

    @Query("select c from RadioButton c where c.id in :ids")
    Set<RadioButton> getRadioButtons(@Param("ids") Set<Long> ids);
}
