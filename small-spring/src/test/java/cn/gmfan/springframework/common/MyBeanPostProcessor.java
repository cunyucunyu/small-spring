package cn.gmfan.springframework.common;

import cn.gmfan.springframework.beans.BeansException;
import cn.gmfan.springframework.beans.UserService;
import cn.gmfan.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author gmfan
 */
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("userService")) {
            UserService userService = (UserService) bean;
            userService.setLocation("改为：北京（修改位置为MyBeanPostProcessor==》before");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName+"执行了MyBeanPostProcessor==》after，虽然什么也没做！");
        return bean;
    }
}
