package cn.gmfan.smallspring.context;

import java.util.EventObject;

/**
 * 定义具备事件功能的抽象类，所有后续事件的类都需要继承这个类
 * @author gmfan
 */
public abstract class ApplicationEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
