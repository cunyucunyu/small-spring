package cn.gmfan.smallspring.context;

import cn.gmfan.smallspring.beans.BeansException;
import cn.gmfan.smallspring.beans.factory.Aware;

/**
 * @author gmfan
 */
public interface ApplicationContextAware extends Aware {
    /**
     * 感知获取ApplicationContext
     * @param applicationContext
     * @throws BeansException
     */
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
