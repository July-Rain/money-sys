package com.lawschool.base;

import java.util.Date;

/**
 * 公共继承类
 * @param <T>
 */
public class DataEntity<T> extends BaseEntity<T> {

    private static final long serialVersionUID = 1L;

    protected String createUser;    //创建人
    protected Date createTime;      //创建时间
    protected String optUser;       //操作人
    protected Date optTime;         //操作时间
    protected String isEnble;       //是否启用（0启用；1禁用）


    //======================== 静态常量 ======================
    //删除标记（0：正常；1：删除；）
    public static final String DEL_FLAG_NORMAL = "0";
    public static final String DEL_FLAG_DELETE = "1";


    public DataEntity(){
        super();
        this.isEnble = DEL_FLAG_NORMAL;
    }

    public DataEntity(String id){
        super(id);
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOptUser() {
        return optUser;
    }

    public void setOptUser(String optUser) {
        this.optUser = optUser;
    }

    public Date getOptTime() {
        return optTime;
    }

    public void setOptTime(Date optTime) {
        this.optTime = optTime;
    }

    public String getIsEnble() {
        return isEnble;
    }

    public void setIsEnble(String isEnble) {
        this.isEnble = isEnble;
    }
}
