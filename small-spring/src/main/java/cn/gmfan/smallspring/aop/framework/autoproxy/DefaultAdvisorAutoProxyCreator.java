package cn.gmfan.smallspring.aop.framework.autoproxy;

import cn.gmfan.smallspring.aop.*;
import cn.gmfan.smallspring.aop.aspectj.AspectJExpressionPointcutAdvisor;
import cn.gmfan.smallspring.aop.framework.ProxyFactory;
import cn.gmfan.smallspring.beans.BeansException;
import cn.gmfan.smallspring.beans.factory.BeanFactory;
import cn.gmfan.smallspring.beans.factory.BeanFactoryAware;
import cn.gmfan.smallspring.beans.factory.PropertyValues;
import cn.gmfan.smallspring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import cn.gmfan.smallspring.beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 此类实现了对象实例化之前感知，Bean Factory感知
 * @author gmfan
 */
public class DefaultAdvisorAutoProxyCreator implements
        InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    private final Set<Object> earlyProxyReferences = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //判断是否已经创建过代理对象了
        if (!earlyProxyReferences.contains(beanName)) {
            return wrapIfNecessary(bean, beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues propertyValues, Object bean, String beanName) throws BeansException {
        return propertyValues;
    }

    @Override
    public Object getEarlyBeanReference(Object bean,String beanName){
        earlyProxyReferences.add(beanName);
        return wrapIfNecessary(bean, beanName);
    }

    /**
     * 创建代理对象
     * @param bean
     * @param beanName
     * @return
     */
    protected Object wrapIfNecessary(Object bean, String beanName) {
        //如果是Advice、Pointcut、Advisor的子类则不需要代理
        if (isInfrastructureClass(bean.getClass())) {
            return bean;
        }

        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory
                .getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();

        //下面的方法遍历匹配表达式的类，并且只有一个Advice会被执行
        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            //获取类过滤器
            ClassFilter classFilter = advisor.getPointcut().getClassFilter();
            if (!classFilter.matches(bean.getClass())) {
                continue;
            }
            AdvisedSupport advisedSupport = new AdvisedSupport();

            TargetSource targetSource = new TargetSource(bean);
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            //ture 使用 Cglib 代理，false 使用 JDK 代理，存在父类接口则使用 JDK 代理，不存在使用 JDK 代理
            if(advisedSupport.getTargetSource().getTargetInterfaces().length>0){
                advisedSupport.setProxyTargetClass(false);
            }else{
                advisedSupport.setProxyTargetClass(true);
            }

            return new ProxyFactory(advisedSupport).getProxy();
        }
        return bean;
    }

    /**
     * 如果是Advice、Pointcut、Advisor的子类则返回true
     * @param beanClass
     * @return
     */
    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass) ||
                Pointcut.class.isAssignableFrom(beanClass) ||
                Advisor.class.isAssignableFrom(beanClass);
    }
}
