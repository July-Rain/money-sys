package com.lawschool.form;

import java.util.List;

/**
 * 结果分析Form
 */
public class AnalysisForm {
    private Integer total;// 总题目数
    private Integer rightNum;// 回答正确数
    private String id;// 主题ID

    // 以下字段为总统计分析需要使用
    private String typeName;// 练习类型名称，主题练习、随机练习、自定义练习
    private Integer typeNum;// 各练习类型次数

    private List<ThemeAnswerForm> list;// 各类型题目的统计分析

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getRightNum() {
        return rightNum;
    }

    public void setRightNum(Integer rightNum) {
        this.rightNum = rightNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ThemeAnswerForm> getList() {
        return list;
    }

    public void setList(List<ThemeAnswerForm> list) {
        this.list = list;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getTypeNum() {
        return typeNum;
    }

    public void setTypeNum(Integer typeNum) {
        this.typeNum = typeNum;
    }
}
