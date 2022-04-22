package cn.gmfan.smallspring.beans.factory.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Value {

    /**
     * 表达式如："#{properties.myProp}"
     * @return
     */
    String value();
}
