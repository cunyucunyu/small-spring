package cn.gmfan.cglib.support;


import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author gmfan
 */
public class TargetInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("========================================================================");
        System.out.println("o=" + o.getClass());
        System.out.println("methodProxy=" + methodProxy.getClass());
        System.out.println("========================================================================");
        System.out.println("开始执行【" + method.getName() + "】方法");
        Object res = methodProxy.invokeSuper(o, objects);
        System.out.println("结束调用");
        return res;
    }
}
