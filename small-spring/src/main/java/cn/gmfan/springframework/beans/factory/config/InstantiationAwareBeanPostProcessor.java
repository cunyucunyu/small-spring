package cn.gmfan.springframework.beans.factory.config;

import cn.gmfan.springframework.beans.BeansException;
import cn.gmfan.springframework.beans.factory.PropertyValues;

/**
 * 实现此接口的类将会获得对象实例化感知能力
 * @author gmfan
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
    /**
     * 在对象实例化之前执行此方法
     *
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    /**
     * 对象初始化之后执行此方法
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException;

    /**
     * 在BeanFactory创建bean之前处理propertyValues
     * <p></p>
     * 对象初始化之后执行此方法
     * @param propertyValues
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    PropertyValues postProcessPropertyValues(PropertyValues propertyValues, Object bean, String beanName) throws BeansException;
}
