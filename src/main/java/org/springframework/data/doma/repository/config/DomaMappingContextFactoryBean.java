package org.springframework.data.doma.repository.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.doma.mapping.DomaMappingContext;

class DomaMappingContextFactoryBean extends
        AbstractFactoryBean<DomaMappingContext> implements
        ApplicationContextAware {

    @SuppressWarnings("unused")
    private ListableBeanFactory beanFactory;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.beanFactory = applicationContext;
    }

    @Override
    public Class<?> getObjectType() {
        return DomaMappingContext.class;
    }

    @Override
    protected DomaMappingContext createInstance() throws Exception {
        return new DomaMappingContext();
    }

}
