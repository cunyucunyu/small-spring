package cn.gmfan.springframework.beans.factory;

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
