package cn.gmfan.springframework.aop.bean;

import cn.gmfan.springframework.aop.bean.Husband;
import cn.gmfan.springframework.aop.bean.IMother;
import cn.gmfan.springframework.beans.factory.annotation.Autowired;
import cn.gmfan.springframework.beans.factory.annotation.Qualifier;
import cn.gmfan.springframework.stereotype.Component;

/**
 * @author gmfan
 */
//@Component
public class Wife{
//    @Autowired
    private Husband husband;
//    @Qualifier("husbandMother")
//    @Autowired
    private IMother mother;

    public String queryHusband(){
        return "Wife.husband,Mother.callMother: " + mother.callMother();
    }

    public Husband getHusband() {
        return husband;
    }

    public void setHusband(Husband husband) {
        this.husband = husband;
    }

    public IMother getMother() {
        return mother;
    }

    public void setMother(IMother mother) {
        this.mother = mother;
    }
}
