package cn.gmfan.springframework.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;


/**
 * @author gmfan
 */
public class UserServiceInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        long t1 = System.currentTimeMillis();
        try {
            return methodInvocation.proceed();
        }finally {
            System.out.println("监控-----aop");
            System.out.println("方法名称：" + methodInvocation.getMethod());
            System.out.println("消耗时间：" + (System.currentTimeMillis() - t1));
            System.out.println("=================end===================");
        }
    }
}
