package cn.gmfan.smallspring.beans.factory;

/**
 * @author gmfan
 */
public interface BeanNameAware extends Aware {
    /**
     * 感知BeanName
     * @param name
     */
    void setBeanName(String name);
}
