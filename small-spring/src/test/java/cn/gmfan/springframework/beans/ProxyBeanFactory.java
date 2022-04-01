package cn.gmfan.springframework.beans;

import cn.gmfan.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gmfan
 */
public class ProxyBeanFactory implements FactoryBean<IUserDao> {

    @Override
    public IUserDao getObject() throws Exception {
        InvocationHandler handler=(proxy, method, args) -> {
            if ("toString".equals(method.getName())) {
                return this.toString();
            }
            Map<String, String> map = new HashMap<>();
            map.put("1", "gmfan_getObject()");
            map.put("2", "伊丽莎白_getObject()");
            map.put("3", "梅普露_getObject()");
            return "【你被代理了】： " + method.getName() + ": " + map.get(args[0].toString());
        };
        return (IUserDao) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader()
                ,new Class[]{IUserDao.class},handler);
    }

    @Override
    public Class<?> getObjectType() {
        return IUserDao.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
