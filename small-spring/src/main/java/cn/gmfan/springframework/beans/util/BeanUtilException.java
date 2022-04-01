package cn.gmfan.springframework.beans.util;

/**
 * @author gmfan
 */
public class BeanUtilException extends RuntimeException {
    public BeanUtilException(String msg) {
        super(msg);
    }

    public BeanUtilException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
