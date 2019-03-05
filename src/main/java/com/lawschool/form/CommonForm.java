package com.lawschool.form;

/**
 * @version V1.0
 * @Description: 通用Form
 * @author: 中石电子科技 徐祥
 * @date: 2018-12-10 15:12
 */
public class CommonForm {
    private String key;
    private String value;
    private String opinion;
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
