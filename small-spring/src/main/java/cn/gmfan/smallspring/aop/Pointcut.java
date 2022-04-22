package cn.gmfan.smallspring.aop;

/**
 * 切入点
 * @author gmfan
 */
public interface Pointcut {

    /**
     * 返回此切入点的类过滤器
     * @return
     */
    ClassFilter getClassFilter();

    /**
     * 返回此切入点的方法过滤器
     * @return
     */
    MethodMatcher getMethodMatcher();
}
