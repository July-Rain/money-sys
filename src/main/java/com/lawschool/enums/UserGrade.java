package com.lawschool.enums;

import com.lawschool.base.IEnum;
import sun.awt.SunHints;

public enum UserGrade implements IEnum {

    STUSER("省厅用户"), SJUSER("市局用户"),XJUSER("分局用户"),
    SDUSER("派出所"),OTHSDUSER("所队其他部门");

    public final String value;

    UserGrade(String value){
        this.value = value;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
