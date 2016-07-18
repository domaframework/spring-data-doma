package org.springframework.data.doma.repository.support;

import java.io.Serializable;

import org.seasar.doma.Id;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.ReflectionEntityInformation;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

public class DomaRepositoryFactory extends RepositoryFactorySupport {

    @Override
    public <T, ID extends Serializable> EntityInformation<T, ID> getEntityInformation(
            Class<T> domainClass) {
        // TOOD
        return new ReflectionEntityInformation<>(domainClass, Id.class);
    }

    @Override
    protected Object getTargetRepository(RepositoryInformation metadata) {
        return new Object();
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return Object.class;
    }

}
