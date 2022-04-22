package cn.gmfan.smallspring.beans.factory.config;

import cn.gmfan.smallspring.beans.factory.HierarchicalBeanFactory;
import cn.gmfan.smallspring.core.convert.ConversionService;
import cn.gmfan.smallspring.util.StringValueResolver;

/**
 * 大多数工厂将实现此配置接口，提供配置Bean创建方式配置。以及提供给客户端添加
 * 自定义方法的接口。
 *
 * @author gmfan
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {
    /**
     * 单例
     */
    String SCOPE_SINGLETON = "singleton";
    /**
     * 原型
     */
    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 添加客户端自定义执行方法
     *
     * @param beanPostProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 销毁单例对象
     */
    void destroySingletons();

    /**
     * Add a String resolver for embedded values such as annotation attributes.
     * @param valueResolver the String resolver to apply to embedded values
     */
    void addEmbeddedValueResolver(StringValueResolver valueResolver);

    /**
     * 处理给定的字符串表达式用于获取实际的属性值
     * @param value
     * @return
     */
    String resolveEmbeddedValue(String value);

    /**
     * 设置类型转换服务
     * @param conversionService
     */
    void setConversionService(ConversionService conversionService);

    /**
     * 获取类型转换服务
     * @return
     */
    ConversionService getConversionService();
}
