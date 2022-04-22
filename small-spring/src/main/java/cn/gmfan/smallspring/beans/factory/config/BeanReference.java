package cn.gmfan.smallspring.beans.factory.config;

/**
 * @author gmfan
 */
public class BeanReference {
    private final String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName(){
        return beanName;
    }
}
