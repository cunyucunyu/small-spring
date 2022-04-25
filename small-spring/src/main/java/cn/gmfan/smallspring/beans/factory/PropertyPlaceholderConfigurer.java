package cn.gmfan.smallspring.beans.factory;

import cn.gmfan.smallspring.beans.BeansException;
import cn.gmfan.smallspring.beans.factory.config.BeanDefinition;
import cn.gmfan.smallspring.beans.factory.config.BeanFactoryPostProcessor;
import cn.gmfan.smallspring.core.io.DefaultResourceLoader;
import cn.gmfan.smallspring.core.io.Resource;
import cn.gmfan.smallspring.util.StringValueResolver;

import java.io.IOException;
import java.util.Properties;

/**
 * 处理占位符${}
 * @author gmfan
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {
    /**
     * 默认占位符前缀
     */
    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

    /**
     * 默认占位符后缀
     */
    public static final String DEFAULT_PLACEHOLDER_SUFFIX="}";

    /**
     * 使用默认值是的分割符
     */
    public static final String DEFAULT_PLACEHOLDER_DEFAULT = ":";

    private String location;

    public PropertyPlaceholderConfigurer(){}

    public PropertyPlaceholderConfigurer(String configLocation) {
        this.location = configLocation;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        try{
            //获取配置文件中的属性值
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(location);
            Properties properties = new Properties();
            properties.load(resource.getInputStream());

            String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();

            for (String beanName : beanDefinitionNames) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);

                PropertyValues propertyValues = beanDefinition.getPropertyValues();
                for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                    Object value = propertyValue.getValue();
                    //属性不是字符串则跳过
                    if (!(value instanceof String)) {
                        continue;
                    }

                    String propVal = resolvePlaceholder((String) value, properties);
                    if (propVal != null) {
                        propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(),propVal));
                    }
                }

            }
            //向容器中添加字符串解析器，用于支持@Value注解
            StringValueResolver valueResolver = new PlaceholderResolvingStringValueResolver(properties);
            beanFactory.addEmbeddedValueResolver(valueResolver);
        } catch (IOException e) {
            throw new BeansException("没有找到资源文件：" + location, e);
        }
    }

    /**
     * 根据${}内包含的key从传入属性中获取对应的值，不存在的话返回null
     * @param value
     * @param properties
     * @return
     */
    private String resolvePlaceholder(String value, Properties properties) {
        String strVal = (String) value;
        int startIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
        int stopIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_DEFAULT)==-1?strVal.indexOf(DEFAULT_PLACEHOLDER_SUFFIX):strVal.indexOf(DEFAULT_PLACEHOLDER_DEFAULT);
        if (startIdx != -1 && stopIdx != -1 && startIdx < stopIdx) {
            String propKey = strVal.substring(startIdx + 2, stopIdx);
            String propVal = properties.getProperty(propKey);
            //使用默认值
            if (propVal == null) {
                int defaultIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_DEFAULT);
                if (defaultIdx != -1) {
                    propVal = strVal.substring(defaultIdx + 1, strVal.length() - 1);
                }
            }
            return propVal;
        }
        return null;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private class PlaceholderResolvingStringValueResolver implements StringValueResolver {
        private final Properties properties;

        private PlaceholderResolvingStringValueResolver(Properties properties) {
            this.properties = properties;
        }

        @Override
        public String resolveStringValue(String strVal) {
            return PropertyPlaceholderConfigurer.this.resolvePlaceholder(strVal,properties);
        }
    }
}
