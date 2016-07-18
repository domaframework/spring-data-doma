package org.springframework.data.doma.mapping;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

import org.seasar.doma.Embeddable;
import org.seasar.doma.Transient;
import org.seasar.doma.jdbc.entity.EntityPropertyType;
import org.springframework.data.mapping.Association;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.model.AnnotationBasedPersistentProperty;
import org.springframework.data.mapping.model.SimpleTypeHolder;

public class DomaPersistentPropertyImpl extends
        AnnotationBasedPersistentProperty<DomaPersistentProperty> implements
        DomaPersistentProperty {

    private final EntityPropertyType<?, ?> entityPropertyType;

    public DomaPersistentPropertyImpl(Field field,
            PropertyDescriptor propertyDescriptor,
            PersistentEntity<?, DomaPersistentProperty> owner,
            SimpleTypeHolder simpleTypeHolder,
            EntityPropertyType<?, ?> entityPropertyType) {
        super(field, propertyDescriptor, owner, simpleTypeHolder);
        this.entityPropertyType = entityPropertyType;
    }

    @Override
    public boolean isIdProperty() {
        return entityPropertyType.isId();
    }

    @Override
    public boolean isEntity() {
        return false;
    }

    @Override
    public boolean isAssociation() {
        return getType().isAnnotationPresent(Embeddable.class);
    }

    @Override
    public boolean isTransient() {
        return isAnnotationPresent(Transient.class) || super.isTransient();
    }

    @Override
    protected Association<DomaPersistentProperty> createAssociation() {
        return new Association<DomaPersistentProperty>(this, null);
    }

    @Override
    public boolean usePropertyAccess() {
        return false;
    }

    @Override
    public boolean isVersionProperty() {
        return entityPropertyType.isVersion();
    }

    @Override
    public boolean isWritable() {
        return entityPropertyType.isUpdatable() && super.isWritable();
    }

}
