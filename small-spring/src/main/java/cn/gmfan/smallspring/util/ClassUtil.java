package cn.gmfan.smallspring.util;

/**
 * Class工具
 *
 * @author gmfan
 */
public class ClassUtil {
    /**
     * 获取默认类加载器
     * @return
     */
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader loader = null;
        //线程的上下文加载器
        loader = Thread.currentThread().getContextClassLoader();

        if (loader == null) {
            //获取当前类的类加载器
            loader = ClassUtil.class.getClassLoader();
        }
        return loader;
    }

    /**
     * 判断Class是否是cglib代理的类
     * @param clazz
     * @return
     */
    public static boolean isCglibProxyClass(Class<?> clazz) {
        return clazz != null && isCglibProxyClassName(clazz.getName());
    }

    /**
     * 判断类名是否是cglib代理的类
     * @param className
     * @return
     */
    public static boolean isCglibProxyClassName(String className) {
        return className != null && className.contains("$$");
    }
}
