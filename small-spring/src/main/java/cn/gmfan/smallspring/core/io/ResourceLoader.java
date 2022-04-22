package cn.gmfan.smallspring.core.io;

/**
 * 包装资源加载器，使得用户可以以集中的统一方法调用服务。
 * @author gmfan
 */
public interface ResourceLoader {
    String CLASSPATH_URL_PREFIX = "classpath:";

    /**
     * 获取类资源
     * @param location
     * @return
     */
    Resource getResource(String location);
}
