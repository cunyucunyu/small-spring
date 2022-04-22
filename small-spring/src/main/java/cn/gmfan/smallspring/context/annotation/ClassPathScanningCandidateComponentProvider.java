package cn.gmfan.smallspring.context.annotation;

import cn.gmfan.smallspring.beans.factory.config.BeanDefinition;
import cn.gmfan.smallspring.stereotype.Component;
import cn.hutool.core.util.ClassUtil;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 提供扫描带@Component注解得相关方法
 * @author gmfan
 */
public class ClassPathScanningCandidateComponentProvider {

    /**
     * 扫描类路径basePackage中的组件
     * @param basePackage
     * @return
     */
    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> clazz : classes) {
            candidates.add(new BeanDefinition(clazz));
        }
        return candidates;
    }
}
