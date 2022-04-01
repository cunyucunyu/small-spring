package cn.gmfan.springframework.beans.factory.config;

import cn.gmfan.springframework.beans.BeansException;

/**
 * @author gmfan
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
    /**
     * 在对象实例化之前执行此方法
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;
}
