package cn.gmfan.jdk;

import cn.gmfan.springframework.beans.event.CustomEventListener;
import net.sf.cglib.proxy.Enhancer;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * @author gmfan
 */
public class Reflect {
    @Test
    public void t1(){
        CustomEventListener listener = new CustomEventListener();
        Type[] types=listener.getClass().getGenericInterfaces();
        System.out.println(Arrays.toString(types));
        if (types[0] instanceof ParameterizedType) {
            System.out.println(((ParameterizedType) types[0]).getActualTypeArguments()[0].getTypeName());
        }
    }

    @Test
    public void t2(){
        Enhancer enhancer = new Enhancer();
    }
}
