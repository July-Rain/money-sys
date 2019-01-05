package com.lawschool.form;

/**
 * @ClassName PostFrom
 * @Author wangtongye
 * @Date 2019/1/4 16:27
 * @Versiom 1.0
 **/
public class PostFrom {

    private String userName;
    private Integer release;
    private Integer comment;
    private Integer collection;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getRelease() {
        return release;
    }

    public void setRelease(Integer release) {
        this.release = release;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public Integer getCollection() {
        return collection;
    }

    public void setCollection(Integer collection) {
        this.collection = collection;
    }
}
