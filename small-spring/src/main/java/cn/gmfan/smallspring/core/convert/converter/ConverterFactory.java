package cn.gmfan.smallspring.core.convert.converter;

/**
 * 范围转换器工厂可以将 S 转换为 R 的子类型
 * @author gmfan
 */
public interface ConverterFactory<S, R> {
    /**
     * 获取可以将 S 转换为 T，其中 T 也是 R 的子类型实例的转换器
     * @param targetType
     * @param <T>
     * @return
     */
    <T extends R> Converter<S, T> getConverter(Class<T> targetType);
}
