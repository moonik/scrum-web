package scrumweb.projectfield.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface FieldElementsRepository<T> extends JpaRepository<T, Long> {
    Set<T> getElements(Set<Long> ids);
}