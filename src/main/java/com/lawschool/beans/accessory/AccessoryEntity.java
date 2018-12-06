package com.lawschool.beans.accessory;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: AccessoryEntity
 * Description: 附件实体
 * date: 2018-12-6 14:29
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@TableName("law_sys_accessory")
public class AccessoryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 附件id
     */
    private String id;
    /**
     * 附件类型
     */
    private String accessoryType;
    /**
     * 描述信息
     */
    private String remark;
    /**
     * 附件名称
     */
    private String accessoryName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String createName;

    private int delFlag;

    /**
     * 文件路径
     */
    private String filePath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccessoryType() {
        return accessoryType;
    }

    public void setAccessoryType(String accessoryType) {
        this.accessoryType = accessoryType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAccessoryName() {
        return accessoryName;
    }

    public void setAccessoryName(String accessoryName) {
        this.accessoryName = accessoryName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
