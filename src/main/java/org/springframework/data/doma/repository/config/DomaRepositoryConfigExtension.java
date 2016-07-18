package org.springframework.data.doma.repository.config;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;

import org.seasar.doma.Entity;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.data.doma.repository.support.DomaRepositoryFactoryBean;
import org.springframework.data.repository.config.RepositoryConfigurationExtensionSupport;
import org.springframework.data.repository.config.RepositoryConfigurationSource;

public class DomaRepositoryConfigExtension extends
        RepositoryConfigurationExtensionSupport {

    @Override
    protected String getModulePrefix() {
        return "doma";
    }

    @Override
    public String getRepositoryFactoryClassName() {
        return DomaRepositoryFactoryBean.class.getName();
    }

    @Override
    protected Collection<Class<? extends Annotation>> getIdentifyingAnnotations() {
        return Arrays.asList(Entity.class);
    }

    @Override
    public void postProcess(BeanDefinitionBuilder builder,
            RepositoryConfigurationSource source) {
        // TODO
        builder.addPropertyReference("mappingContext", "domaMappingContext");
    }

    @Override
    public void registerBeansForRoot(BeanDefinitionRegistry registry,
            RepositoryConfigurationSource config) {

        super.registerBeansForRoot(registry, config);

        Object source = config.getSource();

        // TODO
        registerIfNotAlreadyRegistered(new RootBeanDefinition(
                DomaMappingContextFactoryBean.class), registry,
                "domaMappingContext", source);
    }
}
