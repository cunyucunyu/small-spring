package cn.gmfan.springframework.beans.factory.annotation;

import cn.gmfan.springframework.beans.BeansException;
import cn.gmfan.springframework.beans.factory.BeanFactory;
import cn.gmfan.springframework.beans.factory.BeanFactoryAware;
import cn.gmfan.springframework.beans.factory.ConfigurableListableBeanFactory;
import cn.gmfan.springframework.beans.factory.PropertyValues;
import cn.gmfan.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import cn.gmfan.springframework.beans.util.BeanUtil;
import cn.gmfan.springframework.util.ClassUtil;

import java.lang.reflect.Field;

/**
 * @author gmfan
 */
public class AutowiredAnnotationBeanPostProcessor implements
        InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    private ConfigurableListableBeanFactory beanFactory;
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues propertyValues, Object bean, String beanName) throws BeansException {

        //1.处理注解@Value
        Class<?> clazz = bean.getClass();
        clazz = ClassUtil.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;

        Field[] declaredFields = clazz.getDeclaredFields();

        for (Field field : declaredFields) {
            Value valueAnnotation = field.getAnnotation(Value.class);
            if (valueAnnotation != null) {
                String value = valueAnnotation.value();
                value = beanFactory.resolveEmbeddedValue(value);
                BeanUtil.setFieldValue(bean, field.getName(), value);
            }
        }

        //2.处理注解@Autowired
        for (Field field : declaredFields) {
            Autowired autowired = field.getAnnotation(Autowired.class);
            if (autowired != null) {
                Class<?> fieldType = field.getType();
                String dependentBeanName = null;
                Qualifier qualifier = field.getAnnotation(Qualifier.class);
                Object dependentBean = null;
                if (qualifier != null) {
                    dependentBeanName = qualifier.value();
                    dependentBean = beanFactory.getBean(dependentBeanName, fieldType);
                } else {
                    dependentBean = beanFactory.getBean(fieldType);
                }
                BeanUtil.setFieldValue(bean, field.getName(), dependentBean);
            }
        }
        return propertyValues;
    }
}
