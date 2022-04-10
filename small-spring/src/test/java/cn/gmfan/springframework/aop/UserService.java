package cn.gmfan.springframework.aop;

import cn.gmfan.springframework.beans.factory.annotation.Value;
import cn.gmfan.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author gmfan
 */
@Component
public class UserService implements IUserService {

    @Value("${token}")
    private String token;

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

    @Override
    public String intTest(int a, int b) {
        return String.valueOf(a) + b;
    }

    public String toString(){
        return "UserService#token = { " + token + " } ";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
