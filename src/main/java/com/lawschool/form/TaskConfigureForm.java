package com.lawschool.form;

import java.util.Date;
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
    private String deptNames;
    private String userNames;

    private Date kssj;
    private Date jssj;

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

    public Date getKssj() {
        return kssj;
    }

    public void setKssj(Date kssj) {
        this.kssj = kssj;
    }

    public Date getJssj() {
        return jssj;
    }

    public void setJssj(Date jssj) {
        this.jssj = jssj;
    }
}
