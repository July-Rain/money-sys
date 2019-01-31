package com.lawschool.form;

import java.util.List;

/**
 * @Auther: Moon
 * @Date: 2019/1/2 11:49
 * @Description: 练习任务配置Form
 */
public class TaskConfigureForm {

    private String id;
    private String name;
    private List<String> difficultys;
    private List<String> classifys;
    private List<String> types;
    private List<String> topics;

    private List<String> users;
    private List<String> depts;
    private String themeName;
    private List<String> deptNames;
    private List<String> userNames;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public List<String> getDepts() {
        return depts;
    }

    public void setDepts(List<String> depts) {
        this.depts = depts;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getDeptNames() {
        return deptNames;
    }

    public void setDeptNames(List<String> deptNames) {
        this.deptNames = deptNames;
    }

    public List<String> getUserNames() {
        return userNames;
    }

    public void setUserNames(List<String> userNames) {
        this.userNames = userNames;
    }
}
