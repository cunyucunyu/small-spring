package cn.gmfan.springframework.util;

/**
 * @author gmfan
 */
public interface StringValueResolver {
    /**
     * 解析字符串
     * @param strVal
     * @return
     */
    String resolveStringValue(String strVal);
}
