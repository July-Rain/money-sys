package com.lawschool.controller;


import com.lawschool.service.CompetitionTeamService;
import com.lawschool.service.MatchSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @Descriptin 比武战队
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
@Controller
@RequestMapping("/competitionTeam")
public class CompetitionTeamController {

    @Autowired
    private CompetitionTeamService competitionTeamService;


}
