package cn.gmfan.springframework.aop;

import cn.gmfan.springframework.aop.aspectj.AspectJExpressionPointcut;
import cn.gmfan.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import cn.gmfan.springframework.aop.bean.Husband;
import cn.gmfan.springframework.aop.bean.Wife;
import cn.gmfan.springframework.aop.bean.WifeInterface;
import cn.gmfan.springframework.aop.framework.Cglib2AopProxy;
import cn.gmfan.springframework.aop.framework.JdkDynamicAopProxy;
import cn.gmfan.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

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
        Husband husband = applicationContext.getBean("husband", Husband.class);
        WifeInterface wife = applicationContext.getBean("wife", Wife.class);
        System.out.println("husband's wife:" + husband.queryWife());
        System.out.println(wife.getClass());
        String str = wife.queryHusband();
        System.out.println("wife's husband:"+str);
    }

    @Test
    public void jdk_proxy(){
        A a=(A)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), B.class.getInterfaces(), ((proxy, method, args) -> {
            System.out.println(proxy.getClass());
            System.out.println("=============");
            System.out.println(method.getName());
            return null;
        }));
        System.out.println(a.a());
        System.out.println("---------------------");
        for (Class<?> e : C.class.getInterfaces()) {
            System.out.println(e);
        }
        System.out.println(C.class.getInterfaces().length);
    }

    static interface A{
        String a();
    }

    static class B implements A{
        public String a(){
            return "I am A";
        }
    }

    static class C{

    }
}
