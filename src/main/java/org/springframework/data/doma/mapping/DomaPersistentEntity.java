package org.springframework.data.doma.mapping;

import org.springframework.data.mapping.PersistentEntity;

public interface DomaPersistentEntity<T> extends
        PersistentEntity<T, DomaPersistentProperty> {
}
