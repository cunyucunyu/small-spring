package cn.gmfan.smallspring.context.event;

import cn.gmfan.smallspring.beans.BeansException;
import cn.gmfan.smallspring.beans.factory.BeanFactory;
import cn.gmfan.smallspring.beans.factory.BeanFactoryAware;
import cn.gmfan.smallspring.context.ApplicationEvent;
import cn.gmfan.smallspring.context.ApplicationListener;
import cn.gmfan.smallspring.util.ClassUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author gmfan
 */
public abstract class AbstractApplicationEventMulticaster
        implements ApplicationEventMulticaster, BeanFactoryAware {
    public final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();

    private BeanFactory beanFactory;

    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.remove(listener);
    }

    @Override
    public final void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 返回给定事件匹配的监听器，不匹配的会被排除
     * @param event
     * @return
     */
    protected Collection<ApplicationListener> getApplicationListeners(ApplicationEvent event) {
        List<ApplicationListener> allListeners = new ArrayList<>();
        for (ApplicationListener<ApplicationEvent> listener : applicationListeners) {
            if(supportsEvent(listener,event)){
                allListeners.add(listener);
            }
        }
        return allListeners;
    }

    /**
     * 判断监听器是否对该事件感兴趣
     * @param listener
     * @param event
     * @return
     */
    protected boolean supportsEvent(ApplicationListener<ApplicationEvent> listener, ApplicationEvent event) {
        Class<? extends ApplicationListener> listenerClass = listener.getClass();

        //判断类是否是cglib代理的类以此来获取Class
        Class<?> targetClass = ClassUtil.isCglibProxyClass(listenerClass) ? listenerClass.getSuperclass() : listenerClass;
        //获取listener实现的接口的第一个接口
        Type genericInterface = targetClass.getGenericInterfaces()[0];

        //获取接口的参数的第一个参数
        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
        String className = actualTypeArgument.getTypeName();
        Class<?> evenClassName=null;
        try {
            evenClassName = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("错误的event类：" + className+"原因在于无法找到此类",e);
        }

        return evenClassName.isAssignableFrom(event.getClass());
    }
}
