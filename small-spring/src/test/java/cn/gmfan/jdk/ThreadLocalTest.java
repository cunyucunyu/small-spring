package cn.gmfan.jdk;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author gmfan
 */
public class ThreadLocalTest {
    @Test
    public void t1() throws ParseException {
        ThreadLocal threadLocal = ThreadLocal.withInitial(() -> {
            return new SimpleDateFormat();
        });

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss SSS");

        long t1 = Date.parse("2022-21-31 09:21:26".replaceAll("-","/"));
        long t2 = Date.parse("2022-21-30 09:21:26".replaceAll("-","/"));
        System.out.println(sdf.format(t1-t2));
        System.out.println(sdf.format(0L));

        threadLocal.set(1);
        threadLocal.get();
        threadLocal.remove();

//        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor();
        Executors.newFixedThreadPool(1);
        Executors.newScheduledThreadPool(1);
        Executors.newCachedThreadPool();
    }
}
