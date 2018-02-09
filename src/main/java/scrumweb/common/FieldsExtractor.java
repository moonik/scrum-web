package scrumweb.common;

import java.util.Set;

public interface FieldsExtractor<T, Q> {
    Set<T> extractFields(Q fieldsCollector);
}
