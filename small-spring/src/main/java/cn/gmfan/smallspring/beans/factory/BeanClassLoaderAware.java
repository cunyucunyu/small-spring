package cn.gmfan.smallspring.beans.factory;

/**
 * @author gmfan
 */
public interface BeanClassLoaderAware extends Aware {
    /**
     * 感知获取ClassLoader
     * @param classLoader
     */
    void setBeanClassLoader(ClassLoader classLoader);
}
