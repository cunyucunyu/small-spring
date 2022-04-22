package cn.gmfan.smallspring.core.io;

import cn.gmfan.smallspring.util.ClassUtil;
import cn.hutool.core.lang.Assert;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author gmfan
 */
public class ClassPathResource implements Resource {
    private final String path;
    private ClassLoader classLoader;

    public ClassPathResource(String path) {
        this(path, null);
    }

    public ClassPathResource(String path, ClassLoader classLoader) {
        Assert.notNull(path, "类资源路径不能为空");
        this.path=path;
        this.classLoader = (classLoader != null) ? classLoader : ClassUtil.getDefaultClassLoader();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream in = classLoader.getResourceAsStream(path);
        if (in == null) {
            throw new FileNotFoundException(this.path + " 此路径不存在文件");
        }
        return in;
    }
}
