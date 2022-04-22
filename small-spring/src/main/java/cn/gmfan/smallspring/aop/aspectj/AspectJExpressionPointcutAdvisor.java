package cn.gmfan.smallspring.aop.aspectj;

import cn.gmfan.smallspring.aop.Pointcut;
import cn.gmfan.smallspring.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

/**
 * @author gmfan
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {
    /**
     * 切面
     */
    private AspectJExpressionPointcut pointcut;
    /**
     * 具体拦截方法
     */
    private Advice advice;
    /**
     * 表达式
     */
    private String expression;

    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public Pointcut getPointcut() {
        if (pointcut == null) {
            pointcut = new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }


    public void setExpression(String expression) {
        this.expression = expression;
    }
}
