package cn.gmfan.springframework.beans.factory.xml;

import cn.gmfan.springframework.beans.BeansException;
import cn.gmfan.springframework.beans.factory.PropertyValue;
import cn.gmfan.springframework.beans.factory.config.BeanDefinition;
import cn.gmfan.springframework.beans.factory.config.BeanReference;
import cn.gmfan.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import cn.gmfan.springframework.beans.factory.support.BeanDefinitionRegistry;
import cn.gmfan.springframework.core.io.Resource;
import cn.gmfan.springframework.core.io.ResourceLoader;
import cn.gmfan.springframework.util.StringUtil;
import cn.hutool.core.util.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

/**
 * 解析xml并处理Bean的注册
 *
 * @author gmfan
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try(InputStream inputStream=resource.getInputStream()) {
            doLoadBeanDefinitions(inputStream);
        } catch (IOException | ClassNotFoundException e) {
            throw new BeansException("解析IO流中的XML文件失败" + resource, e);
        }
    }

    @Override
    public void loadBeanDefinitions(Resource... resources) throws BeansException {
        for (Resource resource : resources) {
            loadBeanDefinitions(resource);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        loadBeanDefinitions(getResourceLoader().getResource(location));
    }

    @Override
    public void loadBeanDefinitions(String... locations) {
        for (String location : locations) {
            loadBeanDefinitions(location);
        }
    }

    protected void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException {
        Document doc = XmlUtil.readXML(inputStream);
        Element root = doc.getDocumentElement();
        NodeList childNodes = root.getChildNodes();

        //遍历节点
        for (int i = 0; i < childNodes.getLength(); i++) {
            Element bean = null;
            Node node =null;
            //判断不是bean标签的跳过
            if(!((node=childNodes.item(i)) instanceof Element)
                    || (bean=(Element)node)==null
                    || !bean.getNodeName().equals("bean")) {
                continue;
            }

            //解析标签
            String id = bean.getAttribute("id");
            String name = bean.getAttribute("name");
            String className = bean.getAttribute("class");
            String initMethod = bean.getAttribute("init-method");
            String destroyMethodName = bean.getAttribute("destroy-method");
            String beanScope = bean.getAttribute("scope");

            //获取Class
            Class<?> clazz = Class.forName(className);
            //bean的名字优先使用id定义的，没有的话使用name
            String beanName = !StringUtil.isEmpty(id) ? id : name;
            if (StringUtil.isEmpty(beanName)) {
                beanName = StringUtil.lowerFirst(clazz.getSimpleName());
            }

            //定义BeanDefinition
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            beanDefinition.setInitMethodName(initMethod);
            beanDefinition.setDestroyMethodName(destroyMethodName);
            if (!StringUtil.isEmpty(beanScope)) {
                beanDefinition.setScope(beanScope);
            }

            //填充对象的属性
            for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
                Element property=null;
                //判断是否是对象的属性
                if(!((node=bean.getChildNodes().item(j)) instanceof Element)
                        || (property=(Element)node)==null
                        || !property.getNodeName().equals("property")){
                    continue;
                }

                //解析属性
                String attrName = property.getAttribute("name");
                String attrValue = property.getAttribute("value");
                String attrRef = property.getAttribute("ref");
                //获取属性的值
                Object value = !StringUtil.isEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;

                PropertyValue propertyValue = new PropertyValue(attrName, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }

            if(getRegistry().containsBeanDefinition(beanName)){
                throw new BeansException("重复定义的类[" + beanName+"]是被禁止的");
            }
            //注册
            getRegistry().registryBeanDefinition(beanName,beanDefinition);
        }
    }
}
