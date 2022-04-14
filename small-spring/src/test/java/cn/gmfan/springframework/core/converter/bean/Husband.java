package cn.gmfan.springframework.core.converter.bean;

import cn.gmfan.springframework.beans.factory.annotation.Value;
import cn.gmfan.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author gmfan
 */
@Component
public class Husband {

    @Value("${wifeName:你猜}")
    private String wifeName;

    @Value("${marriageDate:2021-08-08}")
    private Date marriageDate;

    public String getWifeName() {
        return wifeName;
    }

    public void setWifeName(String wifeName) {
        this.wifeName = wifeName;
    }

    public Date getMarriageDate() {
        return marriageDate;
    }

    public void setMarriageDate(Date marriageDate) {
        this.marriageDate = marriageDate;
    }
}
