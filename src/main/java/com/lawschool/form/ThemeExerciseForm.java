package com.lawschool.form;

/**
 * @author xuxiang
 * @date 2018/12/4 14:21
 */
public class ThemeExerciseForm {

    private String id;// 任务ID
    private String themeId;// 主题类型Id
    private String themeName;// 主题名称
    private String total;// 题目总数
    private String answerNum;// 已回答数量
    private String status;// 任务状态

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAnswerNum() {
        return answerNum;
    }

    public void setAnswerNum(String answerNum) {
        this.answerNum = answerNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
