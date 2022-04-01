package cn.gmfan.springframework.context.event;

import cn.gmfan.springframework.context.ApplicationContext;
import cn.gmfan.springframework.context.ApplicationEvent;

/**
 * 此类是事件的抽象类，所有的事件包括关闭、刷新、以及用户自己实现的事件，都需要继承这个类
 * @author gmfan
 */
public class ApplicationContextEvent extends ApplicationEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    /**
     * 获取ApplicationContext
     * @return
     */
    public final ApplicationContext getApplicationContext(){
        return (ApplicationContext) getSource();
    }
}
