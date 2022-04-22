package cn.gmfan.smallspring.beans.factory.support;

import cn.gmfan.smallspring.beans.BeansException;
import cn.gmfan.smallspring.beans.factory.*;
import cn.gmfan.smallspring.beans.factory.config.*;
import cn.gmfan.smallspring.beans.util.BeanUtil;
import cn.gmfan.smallspring.beans.util.BeanUtilException;
import cn.gmfan.smallspring.core.convert.ConversionService;
import cn.gmfan.smallspring.util.StringUtil;
import cn.hutool.core.util.TypeUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author gmfan
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {
    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();
//    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) {
        Object bean = null;
        //处理对象实例化之前需要处理的方法，并判断是否返回代理Bean对象，不过目前此方法只会返回null
        bean = resolveBeforeInstantiation(beanName, beanDefinition);
        if (bean != null) {
            return bean;
        }
        return doCreateBean(beanName, beanDefinition, args);
    }

    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition, Object[] args) {
        Object bean=null;
        try {
            //实例化Bean
            bean = createBeanInstance(beanDefinition,beanName,args);

            //处理循环依赖，将实例化后的Bean对象提前放入缓存中暴露出来，添加到三级缓存
            if (beanDefinition.isSingleton()) {
                Object finalBean = bean;
                addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, beanDefinition, finalBean));

            }

            //实例化后执行InstantiationAwareBeanPostProcessor及其子类对象的postProcessAfterInstantiation方法
            boolean canContinue = applyBeanPostProcessorsAfterInstantiation(beanName, bean);
            if (!canContinue) {
                return bean;
            }

            //设置Bean属性之前，允许BeanPostProcessor修改属性值，注入注解也是在此阶段处理的
            applyBeanPostProcessorsBeforeApplyingPropertyValues(beanName, bean, beanDefinition);

            //填充Bean属性
            applyPropertyValues(beanName,bean,beanDefinition);

            //执行用户自定义初始化方法和BeanPostProcessor的前后置方法以及代理拦截再次设置
            bean = initializeBean(beanName, bean, beanDefinition);
        } catch (BeansException e) {
            throw new BeansException("实例化bean失败", e);
        }catch (BeanUtilException e){
            throw e;
        }

        //注册实现了DisposableBean接口的Bean对象
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

        Object exposeObject = bean;
        //判断是否是单例
        if (beanDefinition.isSingleton()) {
            //获取代理对象
            exposeObject = getSingleton(beanName);
            registerSingleton(beanName,exposeObject);
        }
        return exposeObject;
    }

    protected Object getEarlyBeanReference(String beanName, BeanDefinition beanDefinition, Object bean) {
        Object exposedObject = bean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                exposedObject = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).getEarlyBeanReference(bean, beanName);
                if (exposedObject == null) {
                    return null;
                }
            }
        }
        return exposedObject;
    }

    /**
     * 如果存在代理则返回代理对象
     *
     * @param beanName
     * @param beanDefinition
     * @return
     */
    protected Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition) {

        Object bean = applyBeanPostProcessorBeforeInstantiation(beanDefinition.getBeanClass(), beanName);

        if (bean != null) {
            //执行after initialization方法
            bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        }
        return bean;
    }

    /**
     * 在设置Bean属性之前，允许BeanPostProcessor修改属性的值
     *
     * @param beanName
     * @param bean
     */
    protected void applyBeanPostProcessorsBeforeApplyingPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {

        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                PropertyValues pvs = ((InstantiationAwareBeanPostProcessor) beanPostProcessor)
                        .postProcessPropertyValues(beanDefinition.getPropertyValues(), bean, beanName);
                if (pvs != null) {
                    for (PropertyValue propertyValue : pvs.getPropertyValues()) {
                        beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
                    }
                }
            }
        }
    }

    /**
     * 在对象实例化之前执行
     * @param beanClass
     * @param beanName
     * @return
     */
    protected Object applyBeanPostProcessorBeforeInstantiation(Class<?> beanClass, String beanName) {

        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                Object result = ((InstantiationAwareBeanPostProcessor) beanPostProcessor)
                        .postProcessBeforeInstantiation(beanClass, beanName);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    /**
     * 执行 Bean 的初始化方法和 BeanPostProcessor 的前置和后置处理方法
     *
     * @param beanName
     * @param bean
     * @return
     */
    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        if (bean instanceof Aware) {
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware)bean).setBeanFactory(this);
            }
            if (bean instanceof BeanClassLoaderAware) {
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
            }
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware)bean).setBeanName(beanName);
            }
        }
        //1、执行BeanPostProcessor的Before处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        //执行Bean对象的初始化
        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("执行初始化类：" + beanName + "的init Method失败", e);
        }

        //2、执行BeanPostProcessor的after
        wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
        return wrappedBean;
    }

    private void invokeInitMethods(String beanName,Object bean,BeanDefinition beanDefinition) throws Exception {
        //1、实现了InitializingBean接口
        if (bean instanceof InitializingBean) {
            //执行接口的afterPropertiesSet方法
            ((InitializingBean)bean).afterPropertiesSet();
        }

        //2、注解配置 init-method {判断是为了避免二次执行初始化}
        String initMethodName = beanDefinition.getInitMethodName();
        if (!StringUtil.isEmpty(initMethodName) && !(bean instanceof InitializingBean)) {
            Method initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
            if(initMethod==null){
                throw new BeansException("没有找到类需要执行的init method方法，方法名为：" + initMethodName+" 在类："+beanName);
            }
            initMethod.invoke(bean);
        }
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException{
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBeaan,String beanName) throws BeansException {
        Object res=existingBeaan;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object cur = processor.postProcessAfterInitialization(res, beanName);
            if(cur==null){
                return res;
            }
            res=cur;
        }
        return res;
    }

    //填充Bean属性
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue p : propertyValues.getPropertyValues()) {
            String name = p.getName();
            Object value = p.getValue();
            if (value instanceof BeanReference) {
                BeanReference beanReference = (BeanReference) value;
                value = getBean(beanReference.getBeanName());
                //类型转换
            }else {
                Class<?> sourceType = value.getClass();
                Class<?> targetType = (Class<?>) TypeUtil.getFieldType(bean.getClass(), name);
                ConversionService conversionService = getConversionService();
                if (conversionService != null) {
                    if (conversionService.canConvert(sourceType, targetType)) {
                        value = conversionService.convert(value, targetType);
                    }
                }
            }
            if (value == null) {
                throw new BeansException(name + "的值不存在");
            }
            try {
                //属性填充
                BeanUtil.setFieldValue(bean, name, value);
            } catch (BeanUtilException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给Bean填充属性
     * @param beanDefinition
     * @param beanName
     * @param args
     * @return
     */
    protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) {
        Constructor constructor = null;
        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        //查找构造函数参数与args参数匹配的构造函数
        for (Constructor e : declaredConstructors) {
            if (args != null && e.getParameters().length == args.length) {
                constructor = e;
                break;
            }
        }
        return getInstantiationStrategy().instantiate(beanDefinition, beanName, constructor, args);
    }

    /**
     * 获取实例化策略
     * @return
     */
    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    /**
     * 设置实例化策略
     * @param instantiationStrategy
     */
    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        //非Singleton类型的Bean不执行销毁方法
        if(!beanDefinition.isSingleton()){
            return;
        }

        if (bean instanceof DisposableBean || !StringUtil.isEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }

    /**
     * 执行InstantiationAwareBeanPostProcessor及其子类的postProcessAfterInstantiation方法，
     * 如果其执行结果为false则停止继续执行并返回false。
     * @param beanName
     * @param bean
     * @return
     */
    private boolean applyBeanPostProcessorsAfterInstantiation(String beanName, Object bean) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                InstantiationAwareBeanPostProcessor instantiationAwareBeanPostProcessor = (InstantiationAwareBeanPostProcessor) beanPostProcessor;
                if (!instantiationAwareBeanPostProcessor.postProcessAfterInstantiation(bean, beanName)) {
                    return false;
                }
            }
        }
        return true;
    }
}
