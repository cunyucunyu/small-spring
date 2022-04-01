package cn.gmfan.springframework.aop.framework.autoproxy;

import cn.gmfan.springframework.aop.*;
import cn.gmfan.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import cn.gmfan.springframework.aop.framework.ProxyFactory;
import cn.gmfan.springframework.beans.BeansException;
import cn.gmfan.springframework.beans.factory.BeanFactory;
import cn.gmfan.springframework.beans.factory.BeanFactoryAware;
import cn.gmfan.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import cn.gmfan.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * @author gmfan
 */
public class DefaultAdvisorAutoProxyCreator implements
        InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

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
        return bean;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {

        //如果是Advice、Pointcut、Advisor则不代理
        if (isInfrastructureClass(beanClass)) {
            return null;
        }

        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory
                .getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();

        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            ClassFilter classFilter = advisor.getPointcut().getClassFilter();
            if (!classFilter.matches(beanClass)) {
                continue;
            }

            AdvisedSupport support = new AdvisedSupport();

            TargetSource targetSource = null;

            try {
                targetSource=new TargetSource(beanClass
                        .getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
            support.setTargetSource(targetSource);
            support.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            support.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            support.setProxyTargetClass(false);

            return new ProxyFactory(support).getProxy();
        }

        return null;
    }

    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass) ||
                Pointcut.class.isAssignableFrom(beanClass) ||
                Advisor.class.isAssignableFrom(beanClass);
    }
}
