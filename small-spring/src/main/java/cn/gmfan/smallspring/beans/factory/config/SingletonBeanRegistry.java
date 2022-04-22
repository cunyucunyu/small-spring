package cn.gmfan.smallspring.beans.factory.config;

/**
 * @author gmfan
 */
public interface SingletonBeanRegistry {
    /**
     * 获取单例对象
     * @param beanName
     * @return
     */
    Object getSingleton(String beanName);

    /**
     * 注册bean对象
     * @param beanName
     * @param singletonObject
     */
    void registerSingleton(String beanName, Object singletonObject);
}
