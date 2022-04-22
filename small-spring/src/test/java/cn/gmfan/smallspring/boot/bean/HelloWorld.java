package cn.gmfan.smallspring.boot.bean;

import cn.gmfan.smallspring.beans.factory.annotation.Autowired;
import cn.gmfan.smallspring.stereotype.Component;

/**
 * @author gmfan
 */
@Component
public class HelloWorld {
    public void helloWorld() {
        System.out.println("Hello World");
    }
}
