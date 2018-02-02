package scrumweb.projectfield.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scrumweb.projectfield.domain.ListElement;

@Repository
public interface ListElementRepository extends JpaRepository<ListElement, Long> {
}
