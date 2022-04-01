package cn.gmfan.springframework.context.support;

import cn.gmfan.springframework.beans.BeansException;
import cn.gmfan.springframework.beans.factory.config.BeanPostProcessor;
import cn.gmfan.springframework.context.ApplicationContext;
import cn.gmfan.springframework.context.ApplicationContextAware;

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
