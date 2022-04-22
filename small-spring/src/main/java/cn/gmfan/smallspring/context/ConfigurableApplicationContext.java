package cn.gmfan.smallspring.context;

import cn.gmfan.smallspring.beans.BeansException;

/**
 * 可自定义的容器接口
 * @author gmfan
 */
public interface ConfigurableApplicationContext extends ApplicationContext {

    /**
     * 刷新容器
     *
     * @throws BeansException
     */
    void refresh() throws BeansException;

    /**
     * 结束钩子
     */
    void registerShutdownHook();

    /**
     * 关闭
     */
    void close();
}
