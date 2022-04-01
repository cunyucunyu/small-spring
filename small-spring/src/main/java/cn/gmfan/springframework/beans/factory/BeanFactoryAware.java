package cn.gmfan.springframework.beans.factory;

import cn.gmfan.springframework.beans.BeansException;

/**
 * @author gmfan
 */
public interface BeanFactoryAware extends Aware {
    /**
     * 感知获取BeanFactory
     * @param beanFactory
     * @throws BeansException
     */
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
