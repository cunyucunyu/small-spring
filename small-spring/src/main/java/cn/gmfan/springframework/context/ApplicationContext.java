package cn.gmfan.springframework.context;

import cn.gmfan.springframework.beans.factory.ListableBeanFactory;

/**
 * 容器的中心接口
 * @author gmfan
 */
public interface ApplicationContext extends ListableBeanFactory
        ,ApplicationEventPublisher {
}
