package cn.gmfan.cglib;

import cn.gmfan.cglib.bean.TargetObject;
import cn.gmfan.cglib.support.TargetInterceptor;
import cn.gmfan.cglib.support.TargetMethodCallbackFilter;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InterfaceMaker;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Method;

/**
 * @author gmfan
 */
public class Main {
    public static void main(String[] args) {
        //创建代理类
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TargetObject.class);
        enhancer.setCallback(new TargetInterceptor());
        TargetObject to = (TargetObject) enhancer.create();

        System.out.println("-------------------------------");
        to.setName("你好");
        System.out.println("-------------------------------");
        System.out.println(to.getName());
        System.out.println("-------------------------------");
        System.out.println(to);
    }
}
