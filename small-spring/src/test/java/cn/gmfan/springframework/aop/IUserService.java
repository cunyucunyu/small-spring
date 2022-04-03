package cn.gmfan.springframework.aop;

/**
 * @author gmfan
 */
public interface IUserService {

    String queryUserInfo();

    String register(String userName);

    String intTest(int a,int b);
}
