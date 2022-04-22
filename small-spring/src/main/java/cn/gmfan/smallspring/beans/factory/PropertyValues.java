package cn.gmfan.smallspring.beans.factory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gmfan
 */
public class PropertyValues {
    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    public void addPropertyValue(PropertyValue propertyValue) {
        this.propertyValueList.add(propertyValue);
    }

    public PropertyValue[] getPropertyValues(){
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue p : propertyValueList) {
            if (p.getName().equals(propertyName)) {
                return p;
            }
        }
        return null;
    }
}
