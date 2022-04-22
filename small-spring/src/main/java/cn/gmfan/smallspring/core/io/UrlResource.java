package cn.gmfan.smallspring.core.io;

import cn.hutool.core.lang.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author gmfan
 */
public class UrlResource implements Resource {
    private final URL url;

    public UrlResource(URL url) {
        Assert.notNull(url, " URL不能为空");
        this.url = url;
    }
    @Override
    public InputStream getInputStream() throws IOException {
        URLConnection connection = this.url.openConnection();
        return connection.getInputStream();
    }
}
