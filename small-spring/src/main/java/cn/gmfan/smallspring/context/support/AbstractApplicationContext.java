package cn.gmfan.smallspring.context.support;

import cn.gmfan.smallspring.beans.BeansException;
import cn.gmfan.smallspring.beans.factory.ConfigurableListableBeanFactory;
import cn.gmfan.smallspring.beans.factory.config.BeanFactoryPostProcessor;
import cn.gmfan.smallspring.beans.factory.config.BeanPostProcessor;
import cn.gmfan.smallspring.context.ApplicationEvent;
import cn.gmfan.smallspring.context.ApplicationListener;
import cn.gmfan.smallspring.context.ConfigurableApplicationContext;
import cn.gmfan.smallspring.context.event.ApplicationEventMulticaster;
import cn.gmfan.smallspring.context.event.ContextClosedEvent;
import cn.gmfan.smallspring.context.event.ContextRefreshedEvent;
import cn.gmfan.smallspring.context.event.SimpleApplicationEventMulticaster;
import cn.gmfan.smallspring.core.convert.ConversionService;
import cn.gmfan.smallspring.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

/**
 * 使用模板方法定义好refresh的骨架，方便实现容器上下文。
 *
 * @author gmfan
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader
        implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

    @Override
    public void refresh() throws BeansException {
        //1.创建BeanFactory，并加载BeanDefinition
        refreshBeanFactory();
        //2.获取BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        //3.添加ApplicationContextAwareProcessor，使得继承自ApplicationContextAware的
        //Bean对象能感知所属的ApplicationContext
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        //4.在Bean实例化之前，执行BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        //5.注册BeanPostProcessor，其需要提前与其他Bean对象实例化之前。
        registerBeanPostProcessor(beanFactory);

        //6.初始化发布者
        initApplicationEventMulticaster();

        //7.注册事件监听器
        registerListeners();

        //8. 设置类型转换器、提前实例化单例Bean对象
        finishBeanFactoryInitialization(beanFactory);

        //9.发布容器刷新完成事件
        finishRefresh();
    }

    /**
     * 设置类型转换器，提前实例化单例Bean对象，类型转换器要最先实例化
     * @param beanFactory
     */
    protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
        //设置类型转换器
        if (beanFactory.containsBean("conversionService")) {
            Object conversionService = beanFactory.getBean("conversionService");
            if (conversionService instanceof ConversionService) {
                beanFactory.setConversionService((ConversionService) conversionService);
            }
        }

        //提前实例化单例Bean对象
        beanFactory.preInstantiateSingletons();
    }

    @Override
    public boolean containsBean(String name) {
        return getBeanFactory().containsBean(name);
    }

    private void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    @Override
    public void publishEvent(ApplicationEvent event){
        applicationEventMulticaster.multicastEvent(event);
    }

    /**
     * 注册监听器
     */
    private void registerListeners() {
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener listener : applicationListeners) {
            applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    /**
     * 初始化发布者
     */
    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME,applicationEventMulticaster);
    }

    /**
     * 刷新BeanFactory
     *
     * @throws BeansException
     */
    protected abstract void refreshBeanFactory() throws BeansException;

    /**
     * 获取BeanFactory
     *
     * @return
     */
    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    /**
     * 加载BeanDefinition之后对其进行修改
     *
     * @param beanFactory
     */
    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    /**
     * 注册用户自定义实例化操作（BeanPostProcessor）
     *
     * @param beanFactory
     */
    private void registerBeanPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requireType) throws BeansException {
        return getBeanFactory().getBean(name, requireType);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {

        return getBeanFactory().getBean(requiredType);
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        //发布容器关闭事件
        publishEvent(new ContextClosedEvent(this));
        //执行销毁单例bean方法
        getBeanFactory().destroySingletons();
    }
}
