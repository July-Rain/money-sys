package com.lawschool.beans.diagnosis;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * ClassName: DiagnosisEntity
 * Description: diagnosis
 * date: 2019-1-7 14:16
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
public class DiagnosisEntity implements Serializable {
    //学习时长
    private BigDecimal value;
    //学习次数
    private int countStu;
    //名称
    private String name;
    //类型
    private String type;

    public int getCountStu() {
        return countStu;
    }

    public void setCountStu(int countStu) {
        this.countStu = countStu;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
