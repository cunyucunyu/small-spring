package cn.gmfan.smallspring.core.convert.support;

import cn.gmfan.smallspring.core.convert.ConversionService;
import cn.gmfan.smallspring.core.convert.converter.Converter;
import cn.gmfan.smallspring.core.convert.converter.ConverterFactory;
import cn.gmfan.smallspring.core.convert.converter.ConverterRegistry;
import cn.gmfan.smallspring.core.convert.converter.GenericConverter;
import cn.gmfan.smallspring.util.ClassUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 通用类型转换器默认实现
 *
 * @author gmfan
 */
public class GenericConversionService implements
        ConversionService, ConverterRegistry {

    private Map<GenericConverter.ConvertiblePair, GenericConverter> converters = new HashMap<>();

    @Override
    public boolean canConvert(Class<?> sourceType, Class<?> targetType) {
        GenericConverter converter = getConverter(sourceType, targetType);
        return converter!=null;
    }

    /**
     * 获取可以转换 sourceType 为 targetType 的转换器
     * @param sourceType
     * @param targetType
     * @return
     */
    protected GenericConverter getConverter(Class<?> sourceType, Class<?> targetType) {
        List<Class<?>> sourceCandidates = getClassHierarchy(sourceType);
        List<Class<?>> targetCandidates = getClassHierarchy(targetType);
        for (Class<?> sc : sourceCandidates) {
            for (Class<?> tc : targetCandidates) {
                GenericConverter.ConvertiblePair convertiblePair = new GenericConverter.ConvertiblePair(sc, tc);
                GenericConverter converter = converters.get(convertiblePair);
                if (converter != null) {
                    return converter;
                }
            }
        }
        return null;
    }

    private List<Class<?>> getClassHierarchy(Class<?> clazz) {
        List<Class<?>> hierarchy = new ArrayList<>();
        while (clazz != null) {
            hierarchy.add(clazz);
            clazz = clazz.getSuperclass();
        }
        return hierarchy;
    }

    @Override
    public <T> T convert(Object source, Class<T> targetType) {
        Class<?> sourceType = source.getClass();
        sourceType = ClassUtil.isCglibProxyClass(sourceType) ? sourceType.getSuperclass() : sourceType;
        GenericConverter converter = getConverter(sourceType, targetType);
        return (T)converter.convert(source,sourceType,targetType);
    }

    @Override
    public void addConverter(Converter<?, ?> converter) {
        GenericConverter.ConvertiblePair typeInfo = getRequiredTypeInfo(converter);
        ConverterAdapter converterAdapter = new ConverterAdapter(typeInfo, converter);
        for (GenericConverter.ConvertiblePair convertiblePair : converterAdapter.getConvertibleTypes()) {
            converters.put(convertiblePair, converterAdapter);
        }
    }

    private GenericConverter.ConvertiblePair getRequiredTypeInfo(Object object) {
        Class<?> clazz = ClassUtil.isCglibProxyClass(object.getClass()) ? object.getClass().getSuperclass() : object.getClass();
        Type[] types = clazz.getGenericInterfaces();
        ParameterizedType parameterized=(ParameterizedType) types[0];
        Type[] actualTypeArguments = parameterized.getActualTypeArguments();
        Class<?> sourceType=(Class<?>) actualTypeArguments[0];
        Class<?> targetType=(Class<?>) actualTypeArguments[1];
        return new GenericConverter.ConvertiblePair(sourceType, targetType);
    }

    @Override
    public void addConverter(GenericConverter converter) {
        for (GenericConverter.ConvertiblePair convertiblePair : converter.getConvertibleTypes()) {
            converters.put(convertiblePair, converter);
        }
    }

    @Override
    public void addConverterFactory(ConverterFactory<?, ?> converterFactory) {
        GenericConverter.ConvertiblePair typeInf = getRequiredTypeInfo(converterFactory);
        ConverterFactoryAdapter converterFactoryAdapter = new ConverterFactoryAdapter(typeInf, converterFactory);
        for (GenericConverter.ConvertiblePair convertiblePair : converterFactoryAdapter.getConvertibleTypes()) {
            converters.put(convertiblePair, converterFactoryAdapter);
        }
    }

    private static final class ConverterAdapter implements GenericConverter {

        private final ConvertiblePair typeInfo;

        private final Converter<Object, Object> converter;

        private ConverterAdapter(ConvertiblePair typeInfo, Converter<?, ?> converter) {
            this.typeInfo = typeInfo;
            this.converter = (Converter<Object, Object>) converter;
        }

        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Collections.singleton(typeInfo);
        }

        @Override
        public Object convert(Object source, Class sourceType, Class targetType) {
            return converter.convert(source);
        }
    }


    private static final class ConverterFactoryAdapter implements GenericConverter {

        private final ConvertiblePair typeInfo;

        private final ConverterFactory<Object, Object> converterFactory;

        private ConverterFactoryAdapter(ConvertiblePair typeInfo, ConverterFactory<?, ?> converterFactory) {
            this.typeInfo = typeInfo;
            this.converterFactory = (ConverterFactory<Object, Object>) converterFactory;
        }


        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Collections.singleton(typeInfo);
        }

        @Override
        public Object convert(Object source, Class sourceType, Class targetType) {
            return converterFactory.getConverter(targetType).convert(source);
        }
    }

}
