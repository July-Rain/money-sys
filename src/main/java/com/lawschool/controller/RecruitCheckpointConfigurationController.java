package com.lawschool.controller;

import com.lawschool.service.RecruitCheckpointConfigurationService;
import com.lawschool.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @Descriptin  闯关关卡
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
@Controller
@RequestMapping("/recruitCheckpointConfiguration")
public class RecruitCheckpointConfigurationController {

    @Autowired
    private RecruitCheckpointConfigurationService recruitCheckpointConfigurationService;


}
