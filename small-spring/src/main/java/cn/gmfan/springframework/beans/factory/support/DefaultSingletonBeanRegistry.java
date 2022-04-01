package cn.gmfan.springframework.beans.factory.support;

import cn.gmfan.springframework.beans.BeansException;
import cn.gmfan.springframework.beans.factory.DisposableBean;
import cn.gmfan.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gmfan
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * 空单例对象，用于作为并发标记，因为ConcurrentHashMap不能存储null值
     */
    protected static final Object NULL_OBJECT = new Object();

    /**
     * 单例对象缓存
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    private final Map<String, DisposableBean> disposableBeanMap = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }

    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }

    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeanMap.put(beanName, bean);
    }

    public void destroySingletons() {
        for (Map.Entry<String, DisposableBean> entry : disposableBeanMap.entrySet()) {
            DisposableBean disposableBean = entry.getValue();
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("执行类：" + entry.getKey() + "的销毁方法抛出异常", e);
            }
        }
    }
}
