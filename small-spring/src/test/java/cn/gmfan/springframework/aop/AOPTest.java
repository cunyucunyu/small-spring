package cn.gmfan.springframework.aop;

import cn.gmfan.springframework.aop.aspectj.AspectJExpressionPointcut;
import cn.gmfan.springframework.aop.framework.Cglib2AopProxy;
import cn.gmfan.springframework.aop.framework.JdkDynamicAopProxy;
import cn.gmfan.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author gmfan
 */
public class AOPTest {
    @Test
    public void proxyMethod() throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* cn.gmfan.springframework.beans.UserService.*(..))");
        Class<UserService> clazz = UserService.class;
        Method method = clazz.getDeclaredMethod("queryUserInfo");

        System.out.println(pointcut.matches(clazz));
        System.out.println(pointcut.matches(method, clazz));
    }

    @Test
    public void dynamic(){
        UserService userService = new UserService();

        AdvisedSupport advised = new AdvisedSupport();
        //被代理的类
        advised.setTargetSource(new TargetSource(userService));
        //处理被代理的类
        advised.setMethodInterceptor(new UserServiceInterceptor());
        advised.setMethodMatcher(new AspectJExpressionPointcut("execution(* cn.gmfan.springframework.aop.IUserService.*(..))"));

        IUserService proxy_jdk = (IUserService) new JdkDynamicAopProxy(advised).getProxy();
        IUserService proxy_cglib = (IUserService) new Cglib2AopProxy(advised).getProxy();

        System.out.println("测试调用" + proxy_jdk.queryUserInfo());
        System.out.println(proxy_jdk.register("tkg"));
        System.out.println(proxy_jdk.intTest(1,2));
//        System.out.println("测试调用"+proxy_cglib.queryUserInfo());

    }

    @Test
    public void property(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println(userService);
//        System.out.println(userService.queryUserInfo());
    }
}
