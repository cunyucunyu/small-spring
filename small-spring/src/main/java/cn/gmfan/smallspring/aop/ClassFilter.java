package cn.gmfan.smallspring.aop;

/**
 * @author gmfan
 */
public interface ClassFilter {
    /**
     * 匹配类
     * @param clazz
     * @return
     */
    boolean matches(Class<?> clazz);
}
