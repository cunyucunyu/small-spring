package cn.gmfan.smallspring.boot.autoconfigure;

import java.lang.annotation.*;

/**
 * @author gmfan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface SmallSpringBoot {

    /**
     * 需要扫描的包路径，没有添加的话，默认会扫描注解所在
     * 包路径。
     * @return
     */
    String[] scanBasePackages() default {};
}
