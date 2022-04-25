package cn.gmfan.smallspring.boot;

import cn.gmfan.smallspring.beans.factory.annotation.Autowired;
import cn.gmfan.smallspring.boot.autoconfigure.SmallSpringBoot;
import cn.gmfan.smallspring.boot.bean.HelloWorld;
import cn.gmfan.smallspring.context.ConfigurableApplicationContext;

import java.util.List;

/**
 * @author gmfan
 */
@SmallSpringBoot
public class SmallSpringApplicationTest {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SmallSpringApplication.run(SmallSpringApplicationTest.class, args);
        HelloWorld helloWorld = context.getBean("helloWorld", HelloWorld.class);
        helloWorld.helloWorld();
    }
}
