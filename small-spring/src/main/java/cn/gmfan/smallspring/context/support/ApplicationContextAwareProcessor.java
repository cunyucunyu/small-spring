package cn.gmfan.smallspring.context.support;

import cn.gmfan.smallspring.beans.BeansException;
import cn.gmfan.smallspring.beans.factory.config.BeanPostProcessor;
import cn.gmfan.smallspring.context.ApplicationContext;
import cn.gmfan.smallspring.context.ApplicationContextAware;

/**
 * 感知获取ApplicationContex不能直接在创建Bean的时候拿到，
 * 所以需要在refresh操作时，把ApplicationContext写入到BeanPostProcessor中
 * @author gmfan
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {
    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware)bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
