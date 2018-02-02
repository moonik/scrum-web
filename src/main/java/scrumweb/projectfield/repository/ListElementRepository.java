package scrumweb.projectfield.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import scrumweb.projectfield.domain.ListElement;

import java.util.Set;

@Repository
public interface ListElementRepository extends JpaRepository<ListElement, Long> {

    @Query("select element from ListElement  element where element.id in :ids")
    Set<ListElement> getListElements(Set<Long> ids);
}
