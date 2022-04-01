package cn.gmfan.springframework.aop;

import java.lang.reflect.Method;

/**
 * @author gmfan
 */
public interface MethodMatcher {
    /**
     * 匹配方法
     * @param method
     * @param targetClass
     * @return
     */
    boolean matches(Method method, Class<?> targetClass);
}
