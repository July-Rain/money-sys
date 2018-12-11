package com.lawschool.beans.system;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.base.DataEntity;

import java.util.Date;

/**
 * @version V1.0
 * @Description: 主题类型Entity
 * @author: 中石电子科技 徐祥
 * @date: 2018-12-11 11:00
 */
@TableName("law_sys_topic")
public class TopicTypeEntity extends DataEntity<TopicTypeEntity> {

    private static final Integer DEL_FLAG_NORMAL = 0;
    private static final Integer DEL_FLAG_DELETE = 1;

    private String name;// 主题名称
    private Integer sort;// 排序
    private Integer enable;// 是否使用
    private Integer delFlag;// 删除标志

    public void preInsert(){
        this.createTime = new Date();
        this.optTime = this.createTime;
        this.id = IdWorker.getIdStr();
        this.optUser = this.createUser;
        this.delFlag = DEL_FLAG_NORMAL;
    }

    public void preUpdate(){
        this.optTime = new Date();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
