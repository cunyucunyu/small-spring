package cn.gmfan.springframework.aop.bean;

import cn.gmfan.springframework.beans.factory.FactoryBean;
import cn.gmfan.springframework.stereotype.Component;

import java.lang.reflect.Proxy;

/**
 * @author gmfan
 */
@Component
public class HusbandMother implements FactoryBean<IMother> {

    @Override
    public IMother getObject() throws Exception {
        System.out.println("--------------------------");
        return (IMother) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{IMother.class}, (proxy, method, args) -> "婚后媳妇妈妈的职责被婆婆代理了！" + method.getName());
    }

    @Override
    public Class<?> getObjectType() {
        return IMother.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
