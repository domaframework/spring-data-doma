package org.springframework.data.doma.mapping;

import java.util.List;

import org.seasar.doma.jdbc.entity.EntityPropertyType;
import org.seasar.doma.jdbc.entity.EntityType;
import org.seasar.doma.jdbc.entity.Property;
import org.springframework.data.annotation.Version;
import org.springframework.data.mapping.IdentifierAccessor;
import org.springframework.data.mapping.model.BasicPersistentEntity;
import org.springframework.data.util.TypeInformation;

public class DomaPersistentEntityImpl<T> extends
        BasicPersistentEntity<T, DomaPersistentProperty> implements
        DomaPersistentEntity<T> {

    private static final String INVALID_VERSION_ANNOTATION = "%s is annotated with "
            + org.springframework.data.annotation.Version.class.getName()
            + " but needs to use "
            + org.seasar.doma.Version.class.getName()
            + " to trigger optimistic locking correctly!";

    private final EntityType<T> entityType;

    public DomaPersistentEntityImpl(TypeInformation<T> information,
            EntityType<T> entityType) {
        super(information);
        this.entityType = entityType;
    }

    @Override
    protected DomaPersistentProperty returnPropertyIfBetterIdPropertyCandidateOrNull(
            DomaPersistentProperty property) {
        return property.isIdProperty() ? property : null;
    }

    @Override
    public IdentifierAccessor getIdentifierAccessor(Object bean) {
        List<EntityPropertyType<T, ?>> idPropertyTypes = entityType
                .getIdPropertyTypes();
        if (idPropertyTypes.size() != 1) {
            // TODO
            throw new RuntimeException();
        }
        EntityPropertyType<T, ?> idPropertyType = idPropertyTypes.get(0);
        return new IdentifierAccessor() {

            @SuppressWarnings("unchecked")
            @Override
            public Object getIdentifier() {
                Property<T, ?> property = idPropertyType.createProperty();
                property.load((T) bean);
                return property.get();
            }
        };
    }

    @Override
    public void verify() {
        super.verify();
        DomaPersistentProperty versionProperty = getVersionProperty();
        if (versionProperty == null) {
            return;
        }
        if (versionProperty.isAnnotationPresent(Version.class)) {
            throw new IllegalArgumentException(String.format(
                    INVALID_VERSION_ANNOTATION, versionProperty));
        }
    }

}
