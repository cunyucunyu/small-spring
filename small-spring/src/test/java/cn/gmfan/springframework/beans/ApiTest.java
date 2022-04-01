package cn.gmfan.springframework.beans;

import cn.gmfan.springframework.beans.event.CustomEvent;
import cn.gmfan.springframework.beans.factory.PropertyValue;
import cn.gmfan.springframework.beans.factory.PropertyValues;
import cn.gmfan.springframework.beans.factory.config.BeanDefinition;
import cn.gmfan.springframework.beans.factory.config.BeanReference;
import cn.gmfan.springframework.beans.factory.support.DefaultListableBeanFactory;
import cn.gmfan.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import cn.gmfan.springframework.common.MyBeanFactoryPostProcessor;
import cn.gmfan.springframework.common.MyBeanPostProcessor;
import cn.gmfan.springframework.context.support.ClassPathXmlApplicationContext;
import cn.gmfan.springframework.core.io.DefaultResourceLoader;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.EventListener;

/**
 * @author gmfan
 */
public class ApiTest {
    @Test
    public void test_event() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.publishEvent(new CustomEvent(applicationContext, 1L, "成功了！"));

        applicationContext.registerShutdownHook();

        UserService service = applicationContext.getBean("userService", UserService.class);
        User user = applicationContext.getBean("user", User.class);
        System.out.println(service.queryUserInfo());
    }
    @Test
    public void test_BeanFactory(){
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registryBeanDefinition("userDao", new BeanDefinition(UserDao.class));

        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("uId", "2"));
        propertyValues.addPropertyValue(new PropertyValue("userDao", new BeanReference("userDao")));

        beanFactory.registryBeanDefinition("userService", new BeanDefinition(UserService.class, propertyValues));

        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();
    }

    @Test
    public void xmlProperties() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        //加载配置文件
        reader.loadBeanDefinitions("classpath:spring.xml");

        UserService userService = beanFactory.getBean("userService", UserService.class);
        userService.queryUserInfo();
    }

    @Test
    public void test_BeanFactoryPostProcessorAndBeanPostProcessor(){
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");

        //修改BeanDefinition
        MyBeanFactoryPostProcessor beanFactoryPostProcessor = new MyBeanFactoryPostProcessor();
        beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);

        //修改Bean对象
        MyBeanPostProcessor beanPostProcessor = new MyBeanPostProcessor();
        beanFactory.addBeanPostProcessor(beanPostProcessor);

        UserService userService = beanFactory.getBean("userService", UserService.class);
        String res = userService.queryUserInfo();
        System.out.println(res);
    }

    @Test
    public void xmlContextText(){
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:spring.xml");

        applicationContext.registerShutdownHook();

        UserService userService = applicationContext.getBean("userService", UserService.class);
        String res = userService.queryUserInfo();
        System.out.println(res);
    }

    @Test
    public void prototype(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();

        UserService u1 = applicationContext.getBean("userService", UserService.class);
        UserService u2 = applicationContext.getBean("userService", UserService.class);

        System.out.println(u1);
        System.out.println(u2);

        System.out.println(u1.queryUserInfo());

        System.out.println("====================");
        System.out.println(Integer.toHexString(u1.hashCode()));
        System.out.println(Integer.toHexString(u2.hashCode()));

    }
}

