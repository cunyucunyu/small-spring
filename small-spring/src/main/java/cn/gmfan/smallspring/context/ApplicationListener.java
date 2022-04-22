package cn.gmfan.smallspring.context;

import java.util.EventListener;

/**
 * 此接口有应用程序事件监听器实现
 * @author gmfan
 */
public interface ApplicationListener<E extends ApplicationEvent>
        extends EventListener {

    /**
     * 处理事件
     * @param event 要相应的事件
     */
    void onApplicationEvent(E event);
}
