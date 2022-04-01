package cn.gmfan.springframework.beans;

import cn.gmfan.springframework.beans.factory.*;
import cn.gmfan.springframework.context.ApplicationContext;
import cn.gmfan.springframework.context.ApplicationContextAware;

/**
 * @author gmfan
 */
public class UserService{
    private String uId;
    private String company;
    private String location;
    private IUserDao userDao;

    public String queryUserInfo(){
        return "查询用户" + userDao.queryUserName(uId)
                +"的详细信息："+toString();
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public IUserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "UserService{" +
                "uId='" + uId + '\'' +
                ", company='" + company + '\'' +
                ", location='" + location + '\'' +
                ", userDao=" + userDao +
                '}';
    }
}
