package cn.gmfan.smallspring.beans.factory;

import cn.gmfan.smallspring.beans.BeansException;
import cn.gmfan.smallspring.beans.factory.config.AutowireCapableBeanFactory;
import cn.gmfan.smallspring.beans.factory.config.BeanDefinition;
import cn.gmfan.smallspring.beans.factory.config.ConfigurableBeanFactory;

/**
 * 聚合一个Listable工厂所需的接口
 *
 * @author gmfan
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {
    /**
     * 获取BeanDefinition
     *
     * @param beanName
     * @return
     * @throws BeansException
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 实例化之前执行
     *
     * @throws BeansException
     */
    void preInstantiateSingletons() throws BeansException;

}
