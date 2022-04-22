package cn.gmfan.smallspring.beans.factory;

/**
 * @author gmfan
 */
public interface FactoryBean<T> {
    /**
     * 获取Bean对象
     *
     * @return
     * @throws Exception
     */
    T getObject() throws Exception;

    /**
     * 获取Bean的Class类
     *
     * @return
     */
    Class<?> getObjectType();

    /**
     * 是否是Singleton
     * @return
     */
    boolean isSingleton();
}
