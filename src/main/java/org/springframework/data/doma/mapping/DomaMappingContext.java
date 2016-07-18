package org.springframework.data.doma.mapping;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

import org.seasar.doma.jdbc.ConfigSupport;
import org.seasar.doma.jdbc.entity.EntityPropertyType;
import org.seasar.doma.jdbc.entity.EntityType;
import org.seasar.doma.jdbc.entity.EntityTypeFactory;
import org.seasar.doma.jdbc.entity.EntityTypeNotFoundException;
import org.springframework.data.mapping.context.AbstractMappingContext;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.util.TypeInformation;

public class DomaMappingContext
        extends
        AbstractMappingContext<DomaPersistentEntityImpl<?>, DomaPersistentProperty> {

    @Override
    protected <T> DomaPersistentEntityImpl<?> createPersistentEntity(
            TypeInformation<T> typeInformation) {
        EntityType<T> entityType = EntityTypeFactory.getEntityType(
                typeInformation.getType(), ConfigSupport.defaultClassHelper);
        if (entityType == null) {
            throw new RuntimeException();
        }
        return new DomaPersistentEntityImpl<T>(typeInformation, entityType);
    }

    @Override
    protected DomaPersistentProperty createPersistentProperty(Field field,
            PropertyDescriptor descriptor, DomaPersistentEntityImpl<?> owner,
            SimpleTypeHolder simpleTypeHolder) {
        EntityType<?> entityType = EntityTypeFactory.getEntityType(
                owner.getType(), ConfigSupport.defaultClassHelper);
        EntityPropertyType<?, ?> entityPropertyType = entityType
                .getEntityPropertyType(field.getName());
        return new DomaPersistentPropertyImpl(field, descriptor, owner,
                simpleTypeHolder, entityPropertyType);
    }

    @Override
    protected boolean shouldCreatePersistentEntityFor(TypeInformation<?> type) {
        try {
            return EntityTypeFactory.getEntityType(type.getType(),
                    ConfigSupport.defaultClassHelper) != null;
        } catch (EntityTypeNotFoundException ignored) {
            return false;
        }
    }

}
