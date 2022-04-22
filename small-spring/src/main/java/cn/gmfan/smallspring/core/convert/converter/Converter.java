package cn.gmfan.smallspring.core.convert.converter;

/**
 * 转换器将 S 类型的对象转换成 T 类型
 * @author gmfan
 */
public interface Converter<S, T> {
    /**
     * 将 source 类型转换为目标类型
     * @param source
     * @return
     */
    T convert(S source);
}
