package cn.gmfan.jdk;

import org.junit.Test;

import javax.sound.sampled.Control;
import javax.swing.table.TableRowSorter;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gmfan
 */
public class MapTest {
    @Test
    public void t1(){
        HashMap hashMap = new HashMap();
        ConcurrentHashMap map = new ConcurrentHashMap();
        map.put(1, 2);
        Thread thread;

    }
    @Test
    public void t2(){
        TKG tkg = new TKG<FileInputStream,String>();
        Field[] fields = tkg.getClass().getDeclaredFields();
        for (Field field : fields) {
            Type type = field.getGenericType();
            System.out.println("==================");
            if (type instanceof TypeVariable) {
                System.out.println("TypeVariable");
                System.out.println(Arrays.toString(((TypeVariable<?>) type).getBounds()));
                System.out.println("+++++++++++++++++++++++++++++");
                System.out.println(((TypeVariable<?>) type).getName());
            } else if (type instanceof ParameterizedType) {
                System.out.println("ParameterizedType");
                System.out.println(type.getTypeName());
                System.out.println("++++++++++++++++");
                Type[] arr = ((ParameterizedType) type).getActualTypeArguments();
                for (Type a : arr) {
                    if (a instanceof WildcardType) {
                        System.out.println("-------------------");
                        System.out.println(a.getTypeName());
                        print(((WildcardType) a).getLowerBounds(),1);
                        print(((WildcardType) a).getUpperBounds(),1);
                    }
                }
            } else if (type instanceof GenericArrayType) {
                System.out.println("GenericArrayType");
                System.out.println(type.getTypeName());
            } else if (type instanceof WildcardType) {
                System.out.println("WildcardType");
                System.out.println(type.getTypeName());
                print(((WildcardType) type).getLowerBounds(),1);
                print(((WildcardType) type).getUpperBounds(),1);
            }
        }
    }

    static void print(Type[] types,int deep) {
        System.out.println(Arrays.toString(types));
        for (Type type : types) {
            System.out.println(type.getTypeName());
        }
    }
}

class TKG<K extends InputStream & Closeable, V> {
//    K k;
//    V v;
//    K[] arr;
//    Map<K, V> map;
    List<? super String> list;
    List<? extends Integer> list2;
//    Class<?> aClass;
}
