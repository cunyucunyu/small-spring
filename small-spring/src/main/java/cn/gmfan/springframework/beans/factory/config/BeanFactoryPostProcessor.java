package cn.gmfan.springframework.beans.factory.config;

import cn.gmfan.springframework.beans.BeansException;
import cn.gmfan.springframework.beans.factory.ConfigurableListableBeanFactory;

/**
 * @author gmfan
 */
public interface BeanFactoryPostProcessor {
    /**
     * 在所有BeanDefinition加载之后，Bean实例化之前，提供修改BeanDefinition属性的机制
     * @param beanFactory
     * @throws BeansException
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
