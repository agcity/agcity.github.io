package io.github.agcity.netty.server.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationcontext) throws BeansException {
        SpringContext.context = applicationcontext;
    }

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clazz) {
        String[] beanNames = context.getBeanNamesForType(clazz);
        if (beanNames == null || beanNames.length == 0) {
            throw new IllegalArgumentException("There are no bean of type " + clazz.getName());
        } else if (beanNames.length > 1) {
            throw new IllegalArgumentException("There are more than one bean of type " + clazz.getName());
        }
        return (T) getBean(beanNames[0]);
    }

}