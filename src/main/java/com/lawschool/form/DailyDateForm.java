package com.lawschool.form;

/**
 * @Auther: Moon
 * @Date: 2019/3/1 17:48
 * @Description: 日期Form
 */
public class DailyDateForm {

    private String day;// 名称
    private String date;// 格式yyyy-MM-dd
    private Boolean active;// true为当前日期，false不是

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
