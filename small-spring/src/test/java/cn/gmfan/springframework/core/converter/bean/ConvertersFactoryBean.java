package cn.gmfan.springframework.core.converter.bean;

import cn.gmfan.springframework.beans.factory.FactoryBean;
import cn.gmfan.springframework.core.converter.support.StringToLocalDateConverter;
import cn.gmfan.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author gmfan
 */
@Component("converters")
public class ConvertersFactoryBean implements FactoryBean<Set<?>> {
    @Override
    public Set<?> getObject() throws Exception {
        HashSet<Object> converters = new HashSet<>();
        StringToLocalDateConverter stringToLocalDateConverter = new StringToLocalDateConverter("yyyy-MM-dd");
        converters.add(stringToLocalDateConverter);
        return converters;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
