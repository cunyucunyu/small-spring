package cn.gmfan.smallspring.beans.factory;

import cn.gmfan.smallspring.beans.BeansException;

import java.util.Map;

/**
 * @author gmfan
 */
public interface ListableBeanFactory extends BeanFactory {
    /**
     * 按照类型返回Bean的实例
     *
     * @param type
     * @param <T>
     * @return
     * @throws BeansException
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    /**
     * 返回注册表中所有Bean的名称
     * @return
     */
    String[] getBeanDefinitionNames();
}
