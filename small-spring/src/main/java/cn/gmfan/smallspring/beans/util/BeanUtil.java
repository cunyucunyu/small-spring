package cn.gmfan.smallspring.beans.util;

import cn.gmfan.smallspring.util.ClassUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author gmfan
 */
public class BeanUtil {
    /**
     * 填充对象属性
     * @param obj
     * @param name
     * @param value
     * @throws BeanUtilException
     */
    public static void setFieldValue(Object obj, String name, Object value) throws BeanUtilException {
        //如果是被Cglib代理的则获取被代理类
        Class<?> clazz = ClassUtil.isCglibProxyClass(obj.getClass()) ? obj.getClass().getSuperclass() : obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        Optional<Field> field = Arrays.stream(fields)
                .filter((a) -> a.getName().equals(name)).findFirst();
        if (field == null) {
            throw new BeanUtilException("对象" + clazz.getClass() + "没有字段" + name);
        }
        setFieldValue(obj, field.get(), value);
    }

    public static void setFieldValue(Object obj, Field field, Object value) throws BeanUtilException {
        Class fieldType = field.getType();
        if (value != null) {
            //判断field类型是否可以从value转换而来
            if (fieldType.isAssignableFrom(value.getClass()) == false) {
                throw new BeanUtilException("传入参数对象" + value.getClass().getName() + "不可以转换为" + fieldType.getName());
            }
        } else {
            //获取默认值
            value = getDefaultValue(value.getClass());
        }
        field.setAccessible(true);
        try {
            field.set(obj,value);
        } catch (IllegalAccessException e) {
            throw new BeanUtilException("对象" + obj.getClass().getName() + "的字段" + field.getName() + "设置值" + value.getClass() + "失败", e);
        }
    }

    public static Object getDefaultValue(Class<?> clazz) {
        if (clazz.isPrimitive()) {
            if (clazz.isPrimitive()) {
                if (long.class == clazz) {
                    return 0L;
                } else if (int.class == clazz) {
                    return 0;
                } else if (short.class == clazz) {
                    return (short) 0;
                } else if (char.class == clazz) {
                    return (char) 0;
                } else if (byte.class == clazz) {
                    return (byte) 0;
                } else if (double.class == clazz) {
                    return 0D;
                } else if (float.class == clazz) {
                    return 0f;
                } else if (boolean.class == clazz) {
                    return false;
                }
            }
        }
        return null;
    }
}