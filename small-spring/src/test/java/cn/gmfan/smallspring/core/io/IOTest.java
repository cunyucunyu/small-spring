package cn.gmfan.smallspring.core.io;

import cn.hutool.core.io.IoUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @author gmfan
 */
public class IOTest {
    private DefaultResourceLoader resourceLoader;

    @Before
    public void init(){
        resourceLoader = new DefaultResourceLoader();
    }

    @Test
    public void classpath() throws IOException{
        testIOByPath("classpath:important.properties");
    }

    void testIOByPath(String path) throws IOException {
        Resource resource = resourceLoader.getResource(path);
        String content = IoUtil.readUtf8(resource.getInputStream());
        System.out.println(content);
    }
}
