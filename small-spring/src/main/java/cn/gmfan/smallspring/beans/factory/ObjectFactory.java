package cn.gmfan.smallspring.beans.factory;

import cn.gmfan.smallspring.beans.BeansException;

/**
 * 定义一个工厂可以返回一个对象实例
 * @author gmfan
 */
public interface ObjectFactory<T> {
    /**
     * 获取工厂创建的对象
     * @return 工厂创建的对象
     */
    T getObject() throws BeansException;
}
