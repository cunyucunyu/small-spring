package cn.gmfan.smallspring.beans.factory;

import cn.gmfan.smallspring.beans.BeansException;
import cn.gmfan.smallspring.beans.factory.support.DefaultSingletonBeanRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 此类主要负责处理有关Factory Bean这一类的对象注册操作。
 * @author gmfan
 */
public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {
    /**
     * 缓存使用 FactoryBean 创建的 Singleton Bean
     */
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    protected Object getCachedObjectForFactoryBean(String beanName) {
        Object obj = factoryBeanObjectCache.get(beanName);
        return obj == NULL_OBJECT ? null : obj;
    }

    /**
     * 从BeanFactory中获取对象，如果对象是Singleton则会先从缓存中获取对象，缓存中不存在对象
     * 则会再从BeanFactory中获取对象。
     * @param factory
     * @param beanName
     * @return
     */
    protected Object getObjectFromFactoryBean(FactoryBean factory, String beanName) {
        if (factory.isSingleton()) {
            Object obj = factoryBeanObjectCache.get(beanName);
            if (obj == null) {
                obj = doGetObjectFromFactoryBean(factory, beanName);
                factoryBeanObjectCache.put(beanName, obj == null ? NULL_OBJECT : obj);
            }
            return obj == NULL_OBJECT ? null : obj;
        }else{
            return doGetObjectFromFactoryBean(factory, beanName);
        }
    }

    /**
     * 调用FactoryBean对象的getObject方法创建对象。
     * @param factory
     * @param beanName
     * @return
     */
    private Object doGetObjectFromFactoryBean(final FactoryBean factory, final String beanName) {
        try {
            return factory.getObject();
        } catch (Exception e) {
            throw new BeansException("FactoryBean抛出获取或创建对象：" + beanName + "错误", e);
        }
    }
}
