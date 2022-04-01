package cn.gmfan.springframework.beans;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gmfan
 */
public class UserDao {
    private static Map<String, String> map = new HashMap<>();

    public void initDataMethod() {
        System.out.println("UserDao执行初始化方法initDataMethod");
        map.put("1", "gmfan");
        map.put("2", "伊丽莎白");
        map.put("3", "梅普露");
    }

    public String queryUserById(String uId) {
        return map.get(uId);
    }

    public void destroyDataMethod(){
        System.out.println("UserDao执行destroyDataMethod方法销毁数据");
        map.clear();
    }
}
