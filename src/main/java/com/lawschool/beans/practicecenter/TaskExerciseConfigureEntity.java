package com.lawschool.beans.practicecenter;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Moon
 * @Date: 2018/12/29 13:41
 * @Description: 练习中心--练习任务配置
 */
@TableName("LAW_EXERCISE_TASK_CONFIGURE")
public class TaskExerciseConfigureEntity extends DataEntity<TaskExerciseConfigureEntity> {

    private static final long serialVersionUID = -4958097964395621083L;

    public static final Integer DEL_NORMAL = 0;// 未删除
    public static final Integer DEL_DELETE = 1;// 已删除

    private String name;// 任务名称
    private Integer source;// 来源，0个人、1部门
    private String themeId;// 主题（多选）
    private String difficulty;// 题目难度
    private String classify;// 题目分类

    private String type;// 题目类型
    private Integer total;// 总题量
    private String users;// 使用人
    private String depts;// 使用部门
    private Integer delFlag;// 删除标志，0未删除、1删除

    private String themeName;// 主题信息
    private String createName;// 创建人名称
    private String deptNames;// 使用部门
    private String userNames;// 使用人员

    @TableField(exist = false)
    private Integer start;
    @TableField(exist = false)
    private Integer end;
    @TableField(exist = false)
    private List<String> difficultys = new ArrayList<String>();
    @TableField(exist = false)
    private List<String> classifys = new ArrayList<String>();
    @TableField(exist = false)
    private List<String> types = new ArrayList<String>();
    @TableField(exist = false)
    private List<String> topics = new ArrayList<String>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getDepts() {
        return depts;
    }

    public void setDepts(String depts) {
        this.depts = depts;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public List<String> getDifficultys() {
        return difficultys;
    }

    public void setDifficultys(List<String> difficultys) {
        this.difficultys = difficultys;
    }

    public List<String> getClassifys() {
        return classifys;
    }

    public void setClassifys(List<String> classifys) {
        this.classifys = classifys;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public String getDeptNames() {
        return deptNames;
    }

    public void setDeptNames(String deptNames) {
        this.deptNames = deptNames;
    }

    public String getUserNames() {
        return userNames;
    }

    public void setUserNames(String userNames) {
        this.userNames = userNames;
    }
}
