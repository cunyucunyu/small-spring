package cn.gmfan.springframework.beans.event;

import cn.gmfan.springframework.context.ApplicationListener;
import cn.gmfan.springframework.context.event.ContextClosedEvent;

/**
 * @author gmfan
 */
public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("关闭事件：" + this.getClass().getName());
    }

}
