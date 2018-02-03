package scrumweb.projectfield.repository;

import org.assertj.core.util.Sets;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.Set;

@Repository
public abstract class FieldElementsRepoImpl<T> implements FieldElementsRepository<T> {

    private Class<T> clazz;

    public FieldElementsRepoImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<T> getElements(Set<Long> ids) {
        if(CollectionUtils.isEmpty(ids)) {
            return Sets.newHashSet();
        }
        return new HashSet<T>(entityManager
                .createQuery("from " + clazz.getName() +
                        " where id in :ids", clazz)
                .setParameter("ids", ids)
                .getResultList());
    }
}
