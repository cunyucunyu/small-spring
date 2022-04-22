package cn.gmfan.smallspring.context.support;

import cn.gmfan.smallspring.beans.factory.support.DefaultListableBeanFactory;
import cn.gmfan.smallspring.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @author gmfan
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {

    private XmlBeanDefinitionReader reader;

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        reader = new XmlBeanDefinitionReader(beanFactory, this);
        String[] configLocations = getConfigLocations();
        if (configLocations != null) {
            reader.loadBeanDefinitions(configLocations);
        }
    }

    public XmlBeanDefinitionReader getReader(){
        return reader;
    }

    /**
     * 获取XML配置文件路径
     *
     * @return
     */
    protected abstract String[] getConfigLocations();
}
