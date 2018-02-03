package scrumweb.projectfield.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import scrumweb.projectfield.domain.CheckBox;

import java.util.Set;

@Repository
public interface CheckBoxRepository extends JpaRepository<CheckBox, Long> {

    @Query("select c from CheckBox c where c.id in :ids")
    Set<CheckBox> getCheckBoxes(Set<Long> ids);
 }
