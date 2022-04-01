package cn.gmfan.springframework.aop;

/**
 * @author gmfan
 */
public interface PointcutAdvisor extends Advisor {
    /**
     * 获取Pointcut
     * @return
     */
    Pointcut getPointcut();
}
