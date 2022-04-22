package cn.gmfan.smallspring.core.convert.support;


import cn.gmfan.smallspring.core.convert.converter.ConverterRegistry;

/**
 * @author gmfan
 */
public class DefaultConversionService extends GenericConversionService {

    /**
     * 提前注册转换器
     * @param converterRegistry
     */
    public static void addDefaultConverters(ConverterRegistry converterRegistry) {
        converterRegistry.addConverterFactory(new StringToNumberConverterFactory());
    }

    public DefaultConversionService() {
        addDefaultConverters(this);
    }
}
