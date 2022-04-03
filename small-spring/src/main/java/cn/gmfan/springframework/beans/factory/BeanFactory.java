package cn.gmfan.springframework.beans.factory;

import cn.gmfan.springframework.beans.BeansException;

/**
 * @author gmfan
 */
public interface BeanFactory {
    /**
     * 获取bean对象
     *
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object getBean(String beanName) throws BeansException;

    Object getBean(String name,Object... args) throws BeansException;

    <T> T getBean(String name, Class<T> requiredType) throws BeansException;

    <T> T getBean(Class<T> requiredType) throws BeansException;
}
