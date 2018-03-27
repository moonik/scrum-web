package scrumweb.common;

import java.util.Set;

public interface FieldsExtractor<T> {
    Set<T> extractFields();
}
