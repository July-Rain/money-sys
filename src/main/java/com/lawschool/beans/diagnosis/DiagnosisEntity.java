package com.lawschool.beans.diagnosis;

import java.io.Serializable;

/**
 * ClassName: DiagnosisEntity
 * Description: diagnosis
 * date: 2019-1-7 14:16
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
public class DiagnosisEntity implements Serializable {
    private int value;
    private String name;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
