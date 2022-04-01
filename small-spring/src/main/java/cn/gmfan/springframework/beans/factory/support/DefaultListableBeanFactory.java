package cn.gmfan.springframework.beans.factory.support;

import cn.gmfan.springframework.beans.BeansException;
import cn.gmfan.springframework.beans.factory.ConfigurableListableBeanFactory;
import cn.gmfan.springframework.beans.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * BeanDefinition相关操作
 * @author gmfan
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory
        implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {
    /**
     * 存储BeanDefinition
     */
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new BeansException("类名为：" + beanName + " 的类未被定义或未找到");
        }
        return beanDefinition;
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        Map<String, T> res = new HashMap<>(1 << 8);

        beanDefinitionMap.forEach((name,definition)->{
            Class clazz = definition.getBeanClass();
            if (type.isAssignableFrom(clazz)) {
                res.put(name, (T) getBean(name));
            }
        });

        return res;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }

    @Override
    public void registryBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public void preInstantiateSingletons() throws BeansException{
        beanDefinitionMap.keySet().forEach(this::getBean);
    }


}
