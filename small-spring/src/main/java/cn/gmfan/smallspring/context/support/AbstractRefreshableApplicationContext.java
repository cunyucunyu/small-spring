package cn.gmfan.smallspring.context.support;

import cn.gmfan.smallspring.beans.BeansException;
import cn.gmfan.smallspring.beans.factory.ConfigurableListableBeanFactory;
import cn.gmfan.smallspring.beans.factory.support.DefaultListableBeanFactory;

/**
 * @author gmfan
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {
    /**
     * DefaultListableBeanFactory定义的是BeanDefinition相关操作
     */
    private DefaultListableBeanFactory beanFactory;

    @Override
    protected void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    /**
     * 加载BeanDefinition
     * @param beanFactory
     */
    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);

    @Override
    protected ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }
}
