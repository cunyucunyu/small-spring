package cn.gmfan.cglib.support;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

/**
 * @author gmfan
 */
public class TargetMethodCallbackFilter implements CallbackFilter {
    @Override
    public int accept(Method method) {
        if (method.getName().equals("toString")) {
            System.out.println(method.getName() + "1");
            return 1;
        }
        return 0;
    }
}
