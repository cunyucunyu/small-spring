package cn.gmfan.smallspring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author gmfan
 */
public interface Resource {
    /**
     * 获取资源输入流
     * @return
     * @throws IOException
     */
    InputStream getInputStream() throws IOException;
}
