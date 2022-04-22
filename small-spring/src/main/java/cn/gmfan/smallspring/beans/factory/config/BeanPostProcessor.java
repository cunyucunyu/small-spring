package cn.gmfan.smallspring.beans.factory.config;

import cn.gmfan.smallspring.beans.BeansException;


/**
 * 此接口为Factory的钩子接口，提供给客户端在创建Bean实例后（执行初始化方法前后）对其
 * 进行修改的能力
 *
 * @author gmfan
 */
public interface BeanPostProcessor {
    /**
     * 在Bean对象<b>实例化之后</b>执行初始化方法之前执行此方法。
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 在Bean对象<b>实例化之后</b>执行初始化方法之后执行此方法。
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
