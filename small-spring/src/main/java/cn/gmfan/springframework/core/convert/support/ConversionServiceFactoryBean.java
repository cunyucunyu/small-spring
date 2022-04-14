package cn.gmfan.springframework.core.convert.support;

import cn.gmfan.springframework.beans.factory.FactoryBean;
import cn.gmfan.springframework.beans.factory.InitializingBean;
import cn.gmfan.springframework.core.convert.ConversionService;
import cn.gmfan.springframework.core.convert.converter.Converter;
import cn.gmfan.springframework.core.convert.converter.ConverterFactory;
import cn.gmfan.springframework.core.convert.converter.ConverterRegistry;
import cn.gmfan.springframework.core.convert.converter.GenericConverter;
import com.sun.istack.internal.Nullable;

import java.util.Set;

/**
 * @author gmfan
 */
public class ConversionServiceFactoryBean implements FactoryBean<ConversionService>, InitializingBean {

    @Nullable
    private Set<?> converters;

    @Nullable
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
