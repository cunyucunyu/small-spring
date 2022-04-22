package cn.gmfan.smallspring.aop;

import cn.gmfan.smallspring.util.ClassUtil;

/**
 * @author gmfan
 */
public class TargetSource {
    //被代理的对象
    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    /**
     * 返回被代理对象的接口信息
     * @return
     */
    public Class<?>[] getTargetInterfaces(){
        return getTargetClass().getInterfaces();
    }

    public Object getTarget(){
        return this.target;
    }

    /**
     * 获取被代理类的 Class 对象
     * @return
     */
    public Class<?> getTargetClass(){
        //由于 target 有可能是 Cglib 创建的，所以需要判断是否为 Cglib 创建
        return ClassUtil.isCglibProxyClass(target.getClass()) ?
                target.getClass().getSuperclass() : target.getClass();
    }
}
