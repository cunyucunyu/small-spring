package cn.gmfan.springframework.common;

import cn.gmfan.springframework.beans.BeansException;
import cn.gmfan.springframework.beans.factory.ConfigurableListableBeanFactory;
import cn.gmfan.springframework.beans.factory.PropertyValue;
import cn.gmfan.springframework.beans.factory.PropertyValues;
import cn.gmfan.springframework.beans.factory.config.BeanDefinition;
import cn.gmfan.springframework.beans.factory.config.BeanFactoryPostProcessor;

/**
 * @author gmfan
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition definition = beanFactory.getBeanDefinition("userService");
        PropertyValues values = definition.getPropertyValues();
        values.addPropertyValue(new PropertyValue("company","改为：字节跳动"));
    }
}
