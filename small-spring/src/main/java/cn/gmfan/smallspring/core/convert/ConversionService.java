package cn.gmfan.smallspring.core.convert;

/**
 * @author gmfan
 */
public interface ConversionService {

    /**
     * 判断 sourceType 是否可以转换为 targetType 类型
     *
     * @param sourceType
     * @param targetType
     * @return
     */
    boolean canConvert(Class<?> sourceType, Class<?> targetType);

    /**
     * 将 source 转换为 targetType 类型
     * @param source
     * @param targetType
     * @param <T>
     * @return
     */
    <T> T convert(Object source, Class<T> targetType);
}
