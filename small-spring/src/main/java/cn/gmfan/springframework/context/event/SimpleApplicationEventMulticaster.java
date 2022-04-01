package cn.gmfan.springframework.context.event;

import cn.gmfan.springframework.beans.factory.BeanFactory;
import cn.gmfan.springframework.context.ApplicationEvent;
import cn.gmfan.springframework.context.ApplicationListener;

/**
 * @author gmfan
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }
    @Override
    public void multicastEvent(ApplicationEvent event) {
        for (final ApplicationListener listener : getApplicationListeners(event)) {
            listener.onApplicationEvent(event);
        }
    }
}
