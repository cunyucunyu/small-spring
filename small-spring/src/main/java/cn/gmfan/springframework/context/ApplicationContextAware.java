package cn.gmfan.springframework.context;

import cn.gmfan.springframework.beans.BeansException;
import cn.gmfan.springframework.beans.factory.Aware;

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
