package com.lawschool.beans.practicecenter;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.base.DataEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 主题练习
 *
 * @author xuxiang
 * @date 2018/12/4 9:38
 */
@TableName("law_theme_exercise")
public class ThemeExerciseEntity extends DataEntity<ThemeExerciseEntity> {

    private static final long serialVersionUID = 1L;

    public static final Integer STATUS_WAIT = 0;// 待开始
    public static final Integer STATUS_ON = 1;// 进行中
    public static final Integer STATUS_OFF = 2;// 已提交
    public static final Integer STATUS_FILE = 3;// 已归档

    /** 主题类型ID */
    private String typeId;

    /** 主题类型名称 */
    private String typeName;

    /** 总题数 */
    private Integer total;

    /** 答题数 */
    private Integer answerNum;

    /** 练习状态  0待开始、1进行中、2已提交、3已结束*/
    private Integer status;

    /** 正确题数 */
    private Integer rightNum;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getAnswerNum() {
        return answerNum;
    }

    public void setAnswerNum(Integer answerNum) {
        this.answerNum = answerNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setRightNum(Integer rightNum) {
        this.rightNum = rightNum;
    }

    public Integer getRightNum() {
        return rightNum;
    }

    @Override
    public String toString() {
        return "ThemeExerciseEntity{" +
                "typeId='" + typeId + '\'' +
                ", typeName='" + typeName + '\'' +
                ", total=" + total +
                ", answerNum=" + answerNum +
                ", status=" + status +
                ", rightNum=" + rightNum +
                ", id='" + id + '\'' +
                '}';
    }
}
