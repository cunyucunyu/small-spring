package cn.gmfan.smallspring.core.convert.support;

import cn.gmfan.smallspring.beans.factory.FactoryBean;
import cn.gmfan.smallspring.beans.factory.InitializingBean;
import cn.gmfan.smallspring.core.convert.ConversionService;
import cn.gmfan.smallspring.core.convert.converter.Converter;
import cn.gmfan.smallspring.core.convert.converter.ConverterFactory;
import cn.gmfan.smallspring.core.convert.converter.ConverterRegistry;
import cn.gmfan.smallspring.core.convert.converter.GenericConverter;

import java.util.Set;

/**
 * @author gmfan
 */
public class ConversionServiceFactoryBean implements FactoryBean<ConversionService>, InitializingBean {

    private Set<?> converters;

    private GenericConversionService conversionService;

    @Override
    public ConversionService getObject() throws Exception {
        return conversionService;
    }

    @Override
    public Class<?> getObjectType() {
        return conversionService.getClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        conversionService = new DefaultConversionService();
        registerConverters(converters,conversionService);
    }

    /**
     * 将 converter 注册到 ConverterRegistry
     * @param converters
     * @param registry
     */
    private void registerConverters(Set<?> converters, ConverterRegistry registry) throws IllegalAccessException {
        if (converters != null) {
            for (Object converter : converters) {
                if (converter instanceof GenericConverter) {
                    registry.addConverter((GenericConverter) converter);
                } else if (converter instanceof Converter<?, ?>) {
                    registry.addConverter((Converter<?, ?>) converter);
                } else if (converter instanceof ConverterFactory<?, ?>) {
                    registry.addConverterFactory((ConverterFactory<?, ?>) converter);
                }else {
                    throw new IllegalAccessException("类型转换器Converter必须实现以下接口中的一个："
                            +"Converter,ConverterFactory,or GenericConverter.");
                }
            }
        }
    }

    public void setConverters(Set<?> converters) {
        this.converters = converters;
    }
}
