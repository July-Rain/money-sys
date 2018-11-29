package com.lawschool.controller;

import com.lawschool.service.CompetitionOnlineService;
import com.lawschool.service.TeamUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @Descriptin  战队人员表
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
@Controller
@RequestMapping("/teamUser")
public class TeamUserController {

    @Autowired
    private TeamUserService teamUserService;


}
