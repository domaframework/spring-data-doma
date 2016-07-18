package org.springframework.data.doma.repository.config;

import java.lang.annotation.Annotation;

import org.springframework.data.repository.config.RepositoryBeanDefinitionRegistrarSupport;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

public class DomaRepositoriesRegistrar extends
        RepositoryBeanDefinitionRegistrarSupport {

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return EnableDomaRepositories.class;
    }

    @Override
    protected RepositoryConfigurationExtension getExtension() {
        return new DomaRepositoryConfigExtension();
    }

}
