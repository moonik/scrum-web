package scrumweb.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scrumweb.project.field.ListElement;

@Repository
public interface ListElementRepository extends JpaRepository<ListElement, Long> {
}
