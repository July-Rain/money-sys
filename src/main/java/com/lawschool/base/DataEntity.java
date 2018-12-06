package com.lawschool.base;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;

import java.util.Date;

/**
 * 公共继承类
 * @param <T>
 */
public class DataEntity<T> extends BaseEntity<T> {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    protected String createBy;      //创建人
    @TableField(exist = false)
    protected Date createDate;      //创建时间
    @TableField(exist = false)
    protected String updateBy;      //更新人
    @TableField(exist = false)
    protected Date updateDate;      //更新时间

    public DataEntity(){
        super();
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

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
