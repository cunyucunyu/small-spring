package cn.gmfan.smallspring.core.converter.bean;

import cn.gmfan.smallspring.beans.factory.FactoryBean;
import cn.gmfan.smallspring.core.converter.support.StringToLocalDateConverter;
import cn.gmfan.smallspring.stereotype.Component;

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
