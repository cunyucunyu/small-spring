package cn.gmfan.springframework.beans.factory.support;

import cn.gmfan.springframework.beans.BeansException;
import cn.gmfan.springframework.core.io.Resource;
import cn.gmfan.springframework.core.io.ResourceLoader;

/**
 * @author gmfan
 */
public interface BeanDefinitionReader {
    /**
     * 获取BeanDefinitionRegistry
     *
     * @return
     */
    BeanDefinitionRegistry getRegistry();

    /**
     * 获取资源累加器
     *
     * @return
     */
    ResourceLoader getResourceLoader();

    /**
     * 加载BeanDefinition
     * @param resource
     * @throws BeansException
     */
    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(Resource... resource) throws BeansException;

    void loadBeanDefinitions(String location) throws BeansException;

    void loadBeanDefinitions(String... locations) throws BeansException;
}
