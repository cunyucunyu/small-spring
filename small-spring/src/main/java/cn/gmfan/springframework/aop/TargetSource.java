package cn.gmfan.springframework.aop;

/**
 * @author gmfan
 */
public class TargetSource {
    //被代理的对象
    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    public Class<?>[] getTargetInterfaces(){
        return this.target.getClass().getInterfaces();
    }

    public Object getTarget(){
        return this.target;
    }
}
