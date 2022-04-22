package cn.gmfan.smallspring.core.converter.bean;

import cn.gmfan.smallspring.beans.factory.annotation.Value;
import cn.gmfan.smallspring.stereotype.Component;

import java.time.LocalDate;

/**
 * @author gmfan
 */
@Component
public class Husband {

    @Value("${wifeName:你猜}")
    private String wifeName;

    @Value("${marriageDate:2021-08-08}")
    private LocalDate marriageDate;

    public String getWifeName() {
        return wifeName;
    }

    public void setWifeName(String wifeName) {
        this.wifeName = wifeName;
    }

    public LocalDate getMarriageDate() {
        return marriageDate;
    }

    public void setMarriageDate(LocalDate marriageDate) {
        this.marriageDate = marriageDate;
    }

    @Override
    public String toString() {
        return "Husband{" +
                "wifeName='" + wifeName + '\'' +
                ", marriageDate=" + marriageDate +
                '}';
    }
}
