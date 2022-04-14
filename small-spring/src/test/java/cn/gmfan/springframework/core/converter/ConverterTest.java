package cn.gmfan.springframework.core.converter;

import cn.gmfan.springframework.context.support.ClassPathXmlApplicationContext;
import cn.gmfan.springframework.core.converter.bean.Husband;
import org.junit.Test;

/**
 * @author gmfan
 */
public class ConverterTest {
    @Test
    public void convert() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        Husband husband = applicationContext.getBean("husband", Husband.class);
        System.out.println("测试结果：" + husband);
    }
}
