package cn.gmfan.smallspring.beans.factory.support;

import cn.gmfan.smallspring.beans.BeansException;
import cn.gmfan.smallspring.beans.factory.FactoryBean;
import cn.gmfan.smallspring.beans.factory.FactoryBeanRegistrySupport;
import cn.gmfan.smallspring.beans.factory.config.BeanDefinition;
import cn.gmfan.smallspring.beans.factory.config.BeanPostProcessor;
import cn.gmfan.smallspring.beans.factory.config.ConfigurableBeanFactory;
import cn.gmfan.smallspring.core.convert.ConversionService;
import cn.gmfan.smallspring.util.ClassUtil;
import cn.gmfan.smallspring.util.StringValueResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gmfan
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport
        implements ConfigurableBeanFactory {

    private ClassLoader beanClassLoader = ClassUtil.getDefaultClassLoader();
    /**
     * 要在createBean中应用的BeanPostProcessors
     */
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    /**
     * 类型服务转换
     */
    ConversionService conversionService;

    /**
     * 应用于注释的字符串解析器
     */
    private final List<StringValueResolver> embeddedValueResolver = new ArrayList<>();

    @Override
    public Object getBean(String name) throws BeansException{
        return doGetBean(name, null);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException{
        return doGetBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T)getBean(name);
    }

    protected <T> T doGetBean(final String name, final Object[] args) {

        //从缓存中获取bean
        Object bean = getSingleton(name);
        if (bean != null) {
            return (T)getObjectForBeanInstance(bean,name);
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);

        bean = createBean(name, beanDefinition, args);
        return (T) getObjectForBeanInstance(bean, name);
    }

    /**
     * 根据传入对象获取Bean，如果传入的对象是FactoryBean类型则会调用FactoryBean相关
     * 方法获取对象，否则直接返回对象。
     * @param beanInstance
     * @param beanName
     * @return
     */
    private Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }

        Object obj = getCachedObjectForFactoryBean(beanName);

        if (obj == null) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            obj = getObjectFromFactoryBean(factoryBean, beanName);
        }

        return obj;
    }

    @Override
    public boolean containsBean(String name) {
        return containsBeanDefinition(name);
    }

    /**
     * 是否存在 beanName 的 BeanDefinition。
     * @param beanName
     * @return
     */
    protected abstract boolean containsBeanDefinition(String beanName);

    /**
     * 获取BeanDefinition
     *
     * @param beanName
     * @return
     * @throws Exception
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 创建Bean对象
     *
     * @param beanName
     * @param beanDefinition
     * @param args
     * @return
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args);

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    /**
     * 返回将应用于使用此工厂创建的bean的BeanPostProcessor列表。
     */
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

    @Override
    public void addEmbeddedValueResolver(StringValueResolver valueResolver) {
        this.embeddedValueResolver.add(valueResolver);
    }

    @Override
    public String resolveEmbeddedValue(String value) {
        String res = value;
        for (StringValueResolver resolver : embeddedValueResolver) {
            res = resolver.resolveStringValue(res);
        }
        return res;
    }

    @Override
    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public ConversionService getConversionService() {
        return conversionService;
    }
}
