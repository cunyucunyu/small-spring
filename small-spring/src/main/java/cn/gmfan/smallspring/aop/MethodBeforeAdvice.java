package cn.gmfan.smallspring.aop;

import java.lang.reflect.Method;

/**
 * @author gmfan
 */
public interface MethodBeforeAdvice extends BeforeAdvice {
    /**
     * 在方法执行前执行
     * @param method
     * @param args
     * @param target
     * @throws Throwable
     */
    void before(Method method, Object[] args, Object target) throws Throwable;
}
