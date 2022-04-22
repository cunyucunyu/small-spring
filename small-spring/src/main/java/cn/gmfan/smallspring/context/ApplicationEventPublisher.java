package cn.gmfan.smallspring.context;

/**
 * @author gmfan
 */
public interface ApplicationEventPublisher {

    /**
     * 向所有注册的监听器发布事件
     * @param event
     */
    void publishEvent(ApplicationEvent event);
}
