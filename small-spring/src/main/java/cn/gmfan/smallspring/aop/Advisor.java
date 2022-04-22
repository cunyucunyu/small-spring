package cn.gmfan.smallspring.aop;

import org.aopalliance.aop.Advice;

/**
 * @author gmfan
 */
public interface Advisor {

    /**
     * 返回Advice，Advice表示的是在特定接入点的执行动作
     * @return
     */
    Advice getAdvice();
}
