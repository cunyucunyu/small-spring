package cn.gmfan.springframework.beans.event;

import cn.gmfan.springframework.context.ApplicationListener;
import cn.gmfan.springframework.context.event.ContextRefreshedEvent;

/**
 * @author gmfan
 */
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("刷新事件：" + this.getClass().getName());
    }

}
