package cn.gmfan.springframework.aop;

import cn.gmfan.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author gmfan
 */
//@Component("beforeAdvice")
public class UserServiceBeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("拦截方法："+method.getName());
    }
}
