package com.lawschool.base;

import com.baomidou.mybatisplus.toolkit.IdWorker;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * 公共继承类
 * @param <T>
 */
public class DataEntity<T> extends BaseEntity<T> {

    private static final long serialVersionUID = 1L;

    protected String createUser;    //创建人

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    protected Date createTime;      //创建时间
    protected String optUser;       //操作人
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    protected Date optTime;         //操作时间

    // 新增前调用
    public void preInsert(String userId){
        String id = IdWorker.getIdStr();
        setId(id);
        if(StringUtils.isNotBlank(userId)){
            this.createUser = userId;
            this.optUser = this.createUser;
        }
        this.createTime = new Date();
        this.optTime = this.createTime;
    }

    // 更新前调用
    public void preUpdate(String userId){
        if(StringUtils.isNotBlank(userId)){
            this.optUser = userId;
        }
        this.optTime = new Date();
    }

    public DataEntity(){
        super();
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
}
