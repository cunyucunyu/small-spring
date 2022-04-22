package cn.gmfan.smallspring.context.event;

import cn.gmfan.smallspring.beans.factory.BeanFactory;
import cn.gmfan.smallspring.context.ApplicationEvent;
import cn.gmfan.smallspring.context.ApplicationListener;

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
