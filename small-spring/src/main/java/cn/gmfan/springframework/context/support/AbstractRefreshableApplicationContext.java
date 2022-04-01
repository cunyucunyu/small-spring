package cn.gmfan.springframework.context.support;

import cn.gmfan.springframework.beans.BeansException;
import cn.gmfan.springframework.beans.factory.ConfigurableListableBeanFactory;
import cn.gmfan.springframework.beans.factory.support.DefaultListableBeanFactory;

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
