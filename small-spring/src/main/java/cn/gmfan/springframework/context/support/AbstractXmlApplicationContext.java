package cn.gmfan.springframework.context.support;

import cn.gmfan.springframework.beans.factory.support.DefaultListableBeanFactory;
import cn.gmfan.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @author gmfan
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext{

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory, this);
        String[] configLocations = getConfigLocations();
        if (configLocations != null) {
            reader.loadBeanDefinitions(configLocations);
        }
    }

    /**
     * 获取XML配置文件路径
     * @return
     */
    protected abstract String[] getConfigLocations();
}
