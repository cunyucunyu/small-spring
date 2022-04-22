package cn.gmfan.smallspring.context.annotation;

import cn.gmfan.smallspring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import cn.gmfan.smallspring.beans.factory.config.BeanDefinition;
import cn.gmfan.smallspring.beans.factory.support.BeanDefinitionRegistry;
import cn.gmfan.smallspring.stereotype.Component;
import cn.gmfan.smallspring.util.StringUtil;

import java.util.Set;

/**
 * @author gmfan
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {
    private BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    /**
     * 扫描传入路径的类中是否存在@Component注解与@Scope注解，存在的话将他们注册到
     * BeanDefinitionRegistry中。
     * @param basePackages
     */
    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);

            for (BeanDefinition beanDefinition : candidates) {
                //解析Bean作用域
                String beanScope = resolveBeanScope(beanDefinition);
                if (StringUtil.isNotEmpty(beanScope)) {
                    beanDefinition.setScope(beanScope);
                }
                registry.registryBeanDefinition(determineBeanName(beanDefinition), beanDefinition);
            }
        }

        //注册处理Autowired,Value注解的BeanPostProcessor
        registry.registryBeanDefinition("cn.gmfan.springframework.beans.factory.annotation.tkg#AutowiredAnnotationBeanPostProcessor",new BeanDefinition(AutowiredAnnotationBeanPostProcessor.class));
    }

    /**
     * 获取Bean对象作用域
     * @param beanDefinition
     * @return
     */
    private String resolveBeanScope(BeanDefinition beanDefinition) {
        Class<?> clazz = beanDefinition.getBeanClass();
        Scope scope = clazz.getAnnotation(Scope.class);
        if (scope == null) {
            return StringUtil.EMPTY;
        }
        return scope.value();
    }

    /**
     * 获取Bean对象名称
     * @param beanDefinition
     * @return
     */
    private String determineBeanName(BeanDefinition beanDefinition) {
        Class<?> clazz = beanDefinition.getBeanClass();
        Component component = clazz.getAnnotation(Component.class);
        String value = component.value();
        if (StringUtil.isEmpty(value)) {
            value = StringUtil.lowerFirst(clazz.getSimpleName());
        }
        return value;
    }
}
