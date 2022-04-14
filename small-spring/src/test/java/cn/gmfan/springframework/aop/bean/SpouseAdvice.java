package cn.gmfan.springframework.aop.bean;

import cn.gmfan.springframework.aop.MethodBeforeAdvice;
import cn.gmfan.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author gmfan
 */
//@Component("beforeAdvice")
public class SpouseAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("spouse advice："+method.getName());
    }
}
