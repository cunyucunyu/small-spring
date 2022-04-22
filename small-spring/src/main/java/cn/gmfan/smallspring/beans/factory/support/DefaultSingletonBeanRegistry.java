package cn.gmfan.smallspring.beans.factory.support;

import cn.gmfan.smallspring.beans.BeansException;
import cn.gmfan.smallspring.beans.factory.DisposableBean;
import cn.gmfan.smallspring.beans.factory.ObjectFactory;
import cn.gmfan.smallspring.beans.factory.config.SingletonBeanRegistry;

import java.util.*;
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

    /**
     * 缓存没有完全实例化的对象，二级缓存
     */
    protected final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>();

    /**
     * 缓存单例工厂对象，三级缓存，存放代理对象
     */
    private final Map<String, ObjectFactory<?>> singletonFactories = new ConcurrentHashMap<>();

    /**
     * 需要执行销毁方法的对象
     */
    private final Map<String, DisposableBean> disposableBeanMap = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        Object singleton = singletonObjects.get(beanName);
        if (singleton == null) {
            //二级缓存获取
            singleton = earlySingletonObjects.get(beanName);
            if (singleton == null) {
                //从三级缓存获取
                ObjectFactory<?> singletonFactory = singletonFactories.get(beanName);
                if (singletonFactory != null) {
                    //使用工厂对象创建对象
                    singleton = singletonFactory.getObject();
                    if (singleton != null) {
                        //将都对象放入二级缓存，并从三级缓存中移除
                        earlySingletonObjects.put(beanName, singleton);
                        singletonFactories.remove(beanName);
                    }else{
                        throw new BeansException("没有找到类：" + beanName);
                    }
                }
            }
        }
        return singleton;
    }

    /**
     * 注册单例对象
     * @param beanName bean name
     * @param singletonObject bean instance
     */
    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
        //二级缓存移除
        earlySingletonObjects.remove(beanName);
        //三级缓存移除
        singletonFactories.remove(beanName);
    }

    /**
     * 三级缓存不存在则添加工厂对象，并将二级缓存中的对象移除。
     * @param beanName bean name
     * @param singletonFactory bean instance
     */
    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory) {
        //一级缓存
        if (!singletonFactories.containsKey(beanName)) {
            //三级缓存
            singletonFactories.put(beanName, singletonFactory);
            //二级缓存
            earlySingletonObjects.remove(beanName);
        }
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
