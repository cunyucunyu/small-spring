package cn.gmfan.smallspring.context;

import cn.gmfan.smallspring.beans.factory.ListableBeanFactory;

/**
 * 容器的中心接口
 * @author gmfan
 */
public interface ApplicationContext extends ListableBeanFactory
        ,ApplicationEventPublisher {
}
