package com.lawschool.form;

import java.util.List;

/**
 * 结果分析Form
 */
public class AnalysisForm {
    private Integer total;// 总题目数
    private Integer rightNum;// 回答正确数
    private String id;// 主题ID
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
}
