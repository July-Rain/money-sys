package com.lawschool.controller;

import com.lawschool.service.CompetitionOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @Descriptin  在线比武配置
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
@Controller
@RequestMapping("/competitionOnline")
public class CompetitionOnlineController {

    @Autowired
    private CompetitionOnlineService competitionOnlineService;


}