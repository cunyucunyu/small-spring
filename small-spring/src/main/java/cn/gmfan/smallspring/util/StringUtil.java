package cn.gmfan.smallspring.util;

/**
 * @author gmfan
 */
public class StringUtil {

    public static final String EMPTY = "";

    /**
     * 判断字符串是否为空
     *
     * @param object
     * @return
     */
    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof CharSequence) {
            return ((CharSequence) object).length() == 0;
        } else {
            return true;
        }
    }

    /**
     * 判断字符串是否为空
     * @param object
     * @return
     */
    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }

    /**
     * 将字符串第一个字符转换为小写字符
     *
     * @param str
     * @return
     */
    public static String lowerFirst(String str) {
        if (str == null) {
            return null;
        } else {
            if (str.length() > 0) {
                char firstChar = str.charAt(0);
                if (Character.isUpperCase(firstChar)) {
                    return Character.toLowerCase(firstChar)
                            + (str.length() > 1 ? str.substring(1) : "");
                }
            }

            return str.toString();
        }
    }
}
