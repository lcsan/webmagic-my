package org.webmagic.spring;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;

public class PropertyUtils extends PropertyPlaceholderConfigurer {

    private Properties props;

    private static PropertyUtils instance;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
            throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        this.props = props;
        instance = this;
    }

    @Override
    public void setLocations(Resource... locations) {
        super.setLocations(locations);
    }

    public static String getProperty(String key) {
        return instance.props.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return instance.props.getProperty(key, defaultValue);
    }

}
