package cz.cvut.fel.aos.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextHolder implements ApplicationContextAware {

    private static final ApplicationContextHolder INSTANCE = new ApplicationContextHolder();
    private static ApplicationContext applicationContext;

    private ApplicationContextHolder() {
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.applicationContext = applicationContext;
    }

    public static ApplicationContextHolder getInstance() {
        return INSTANCE;
    }
}
