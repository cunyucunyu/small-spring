package cn.gmfan.smallspring.beans.factory.support;

import cn.gmfan.smallspring.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * 实例化策略
 *
 * @author gmfan
 */
public interface InstantiationStrategy {
    /**
     * 可以根据构造函数和给定参数进行实例化对象。
     * @param beanDefinition
     * @param beanName
     * @param constructor
     * @param args
     * @return
     */
    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor constructor, Object[] args);
}
