package com.lawschool.base;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;

import java.io.Serializable;

/**
 * 公共继承类
 * @param <T>
 */
public class BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    protected String id;        //主键ID
    @TableField(exist = false)
    protected Page<T> page;     //分页

    public BaseEntity(){

    }

    public BaseEntity(String id){
        this();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Page<T> getPage() {
        return page;
    }

    public void setPage(Page<T> page) {
        this.page = page;
    }
}
