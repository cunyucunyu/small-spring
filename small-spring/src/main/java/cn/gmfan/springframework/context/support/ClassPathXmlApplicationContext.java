package cn.gmfan.springframework.context.support;

import cn.gmfan.springframework.beans.BeansException;

/**
 * XML配置方式的应用的上下文
 *
 * @author gmfan
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {
    /**
     * XML配置文件路径
     */
    private String[] configLocations;

    public ClassPathXmlApplicationContext(){}

    public ClassPathXmlApplicationContext(String configLocation) throws BeansException {
        this(new String[]{configLocation});
    }

    /**
     * 使用传入的XML配置文件，来加载BeanDefinition，并刷新上下文
     * @param configLocations
     */
    public ClassPathXmlApplicationContext(String[] configLocations) throws BeansException{
        this.configLocations = configLocations;
        refresh();
    }

    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }
}
