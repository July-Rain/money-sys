package com.lawschool.form;

import java.util.List;

public class TopicForm extends CommonForm{

    private String name;
    private List<String> nums;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getNums() {
        return nums;
    }

    public void setNums(List<String> nums) {
        this.nums = nums;
    }
}
