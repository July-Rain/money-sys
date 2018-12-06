package com.lawschool.base;

import java.util.Date;

/**
 * 公共继承类
 * @param <T>
 */
public class DataEntity<T> extends BaseEntity<T> {

    private static final long serialVersionUID = 1L;

    protected String createBy;      //创建人
    protected Date createDate;      //创建时间
    protected String optBy;         //操作人
    protected Date optDate;         //操作时间
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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getOptBy() {
        return optBy;
    }

    public void setOptBy(String optBy) {
        this.optBy = optBy;
    }

    public Date getOptDate() {
        return optDate;
    }

    public void setOptDate(Date optDate) {
        this.optDate = optDate;
    }

    public String getIsEnble() {
        return isEnble;
    }

    public void setIsEnble(String isEnble) {
        this.isEnble = isEnble;
    }
}
