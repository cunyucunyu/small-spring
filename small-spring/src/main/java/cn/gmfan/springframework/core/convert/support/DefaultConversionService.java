package cn.gmfan.springframework.core.convert.support;


import cn.gmfan.springframework.core.convert.converter.ConverterRegistry;

/**
 * @author gmfan
 */
public class DefaultConversionService extends GenericConversionService {

    /**
     * 提前注册转换器
     * @param converterRegistry
     */
    public static void addDefaultConverters(ConverterRegistry converterRegistry) {
    }

    public DefaultConversionService() {
        addDefaultConverters(this);
    }
}
