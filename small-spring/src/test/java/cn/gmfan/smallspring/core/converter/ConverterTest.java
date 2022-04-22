package cn.gmfan.smallspring.core.converter;

import cn.gmfan.smallspring.context.support.ClassPathXmlApplicationContext;
import cn.gmfan.smallspring.core.converter.bean.Husband;
import cn.gmfan.smallspring.util.ClassUtil;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;

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

    @Test
    public void testFilePath(){
        ClassLoader classLoader = ClassUtil.getDefaultClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("spring.xml");
        System.out.println(inputStream!=null);
    }
}
