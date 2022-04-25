package cn.gmfan.smallspring.boot;

import cn.gmfan.smallspring.boot.autoconfigure.SmallSpringBoot;
import cn.gmfan.smallspring.context.ConfigurableApplicationContext;
import cn.gmfan.smallspring.context.annotation.ClassPathBeanDefinitionScanner;
import cn.gmfan.smallspring.context.support.ClassPathXmlApplicationContext;
import cn.gmfan.smallspring.util.ClassUtil;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *此类用于在Java main 方法中引导 small-spring 启动
 * @author gmfan
 */
public class SmallSpringApplication{

    private static final String CONFIG_PATH1 = "smallSpring.xml";

    private static final String CONFIG_PATH2 = "spring.xml";

    private static final String SMALL_SPRING_PACKAGE = "cn.gmfan.smallspring";


    private Set<Class<?>> primarySources;

    /**
     * main 函数所在类
     */
    private Class<?> mainApplicationClass;

    public SmallSpringApplication(Class<?>... primarySources) {
        this.mainApplicationClass = getMainApplicationClass();
        this.primarySources = new LinkedHashSet<>(Arrays.asList(primarySources));
    }

    public static ConfigurableApplicationContext run(Class<?> primarySource){
        return run(primarySource, null);
    }

    public static ConfigurableApplicationContext run(Class<?> primarySource, String... args) {
        return run(new Class<?>[]{primarySource}, args);
    }

    public static ConfigurableApplicationContext run(Class<?>[] primarySource, String... args) {
        return new SmallSpringApplication(primarySource).run(args);
    }

    public ConfigurableApplicationContext run(String... args) {
        SmallSpringBoot boot=mainApplicationClass.getAnnotation(SmallSpringBoot.class);
        String config=null;
        if (classPathHasFile(CONFIG_PATH2)) {
            config = CONFIG_PATH2;
        }
        if (classPathHasFile(CONFIG_PATH1)) {
            config = CONFIG_PATH1;
        }
        ClassPathXmlApplicationContext context = null;
        if (config != null) {
           context = new ClassPathXmlApplicationContext("classpath:"+config);
        }else{
            context = new ClassPathXmlApplicationContext();
        }

        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(context.getReader().getRegistry());
        scanner.doScan(SMALL_SPRING_PACKAGE);
        if (boot!=null){
            String[] scanBasePackages = boot.scanBasePackages();
            if (scanBasePackages != null && scanBasePackages.length>0) {
                scanner.doScan(scanBasePackages);
            }else{
                String mainName = mainApplicationClass.getName();
                String location=mainName.substring(0,mainName.lastIndexOf("."));
                scanner.doScan(location);
            }
        }
        return context;
    }

    private boolean classPathHasFile(String file) {
        ClassLoader classLoader = ClassUtil.getDefaultClassLoader();
        return classLoader.getResourceAsStream(file) != null;
    }

    /**
     * 寻找 main 函数所在类
     * @return
     */
    private Class<?> getMainApplicationClass(){
        try{
            StackTraceElement[] stackTraceElements = new RuntimeException().getStackTrace();
            for (StackTraceElement element : stackTraceElements) {
                if ("main".equals(element.getMethodName())) {
                    return Class.forName(element.getClassName());
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
