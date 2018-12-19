package com.lawschool.controller;

import com.lawschool.beans.PracticeConfiguration02;
import com.lawschool.service.PracticeConfiguration02Service;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 从表
 */
@RestController
@RequestMapping("/practiceConfiguration02")
public class PracticeConfigurationController02 {
    @Autowired
    PracticeConfiguration02Service practiceConfiguration02Service;

    @RequestMapping("/save")
    public Result insertConfig(PracticeConfiguration02 practiceConfiguration02){
        practiceConfiguration02Service.insertConfig(practiceConfiguration02);
        return Result.ok().put("id",practiceConfiguration02.getId());
    }
}
