package cn.gmfan.springframework.aop.bean;

import cn.gmfan.springframework.beans.factory.annotation.Autowired;
import cn.gmfan.springframework.stereotype.Component;

/**
 * @author gmfan
 */
@Component
public class Husband {
    @Autowired
    private Wife wife;

    public String queryWife(){
        return "Husband.wife";
    }

    public Wife getWife() {
        return wife;
    }

    public void setWife(Wife wife) {
        this.wife = wife;
    }
}
