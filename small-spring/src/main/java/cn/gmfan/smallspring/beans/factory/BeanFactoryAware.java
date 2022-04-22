package cn.gmfan.smallspring.beans.factory;

import cn.gmfan.smallspring.beans.BeansException;

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
