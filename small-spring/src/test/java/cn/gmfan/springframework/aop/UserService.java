package cn.gmfan.springframework.aop;

import java.util.Random;

/**
 * @author gmfan
 */
public class UserService implements IUserService{
    public String queryUserInfo() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "gmfan";
    }

    public String register(String userName) {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "注册用户：" + userName + " success！";
    }
}
