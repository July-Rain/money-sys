package com.lawschool.form;

import java.util.List;

/**
 * @Auther: Moon
 * @Date: 2019/1/18 11:40
 * @Description: TreeForm
 */
public class TreeForm {
    private String id;
    private String name;
    private String parentId;

    private List<TreeForm> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<TreeForm> getChildren() {
        return children;
    }

    public void setChildren(List<TreeForm> children) {
        this.children = children;
    }
}
