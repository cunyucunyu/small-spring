package cn.gmfan.smallspring.beans.factory.support;

import cn.gmfan.smallspring.beans.BeansException;
import cn.gmfan.smallspring.beans.factory.DisposableBean;
import cn.gmfan.smallspring.beans.factory.config.BeanDefinition;
import cn.gmfan.smallspring.util.StringUtil;

import java.lang.reflect.Method;

/**
 * @author gmfan
 */
public class DisposableBeanAdapter implements DisposableBean {
    private final Object bean;
    private final String beanName;
    private String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }
    @Override
    public void destroy() throws Exception {
        //实现了DisposableBean接口
        if (bean instanceof DisposableBean) {
            ((DisposableBean)bean).destroy();
        }

        //使用了注解配置destroy-method
        if (!StringUtil.isEmpty(destroyMethodName)) {
            //防止二次执行销毁方法
            if (!(bean instanceof DisposableBean && destroyMethodName.equals("destroy"))) {
                Method destroy = bean.getClass().getMethod(destroyMethodName);
                if (destroy == null) {
                    throw new BeansException("在bean对象："+beanName+"中没有找到需要执行的销毁方法：" + destroyMethodName);
                }
                destroy.invoke(bean);
            }
        }
    }
}
