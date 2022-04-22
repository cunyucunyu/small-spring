package cn.gmfan.smallspring.context.event;

import cn.gmfan.smallspring.context.ApplicationEvent;
import cn.gmfan.smallspring.context.ApplicationListener;

/**
 * 事件广播器接口
 * @author gmfan
 */
public interface ApplicationEventMulticaster {

    /**
     * 添加一个事件监听器
     *
     * @param listener
     */
    void addApplicationListener(ApplicationListener<?> listener);

    /**
     * 从通知列表中移除一个监听器
     *
     * @param listener 要移除的监听器
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * 将给定的ApplicationEvent多播到适当的监听器
     * @param event
     */
    void multicastEvent(ApplicationEvent event);
}
