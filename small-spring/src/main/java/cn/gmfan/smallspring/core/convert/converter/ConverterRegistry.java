package cn.gmfan.smallspring.core.convert.converter;

/**
 * 类型转换器注册接口
 *
 * @author gmfan
 */
public interface ConverterRegistry {
    /**
     * 添加一个普通类型的转换器
     * @param converter
     */
    void addConverter(Converter<?, ?> converter);

    /**
     * 添加通用类型的转换器
     *
     * @param converter
     */
    void addConverter(GenericConverter converter);

    /**
     * 添加转换器工厂
     * @param converterFactory
     */
    void addConverterFactory(ConverterFactory<?, ?> converterFactory);
}
