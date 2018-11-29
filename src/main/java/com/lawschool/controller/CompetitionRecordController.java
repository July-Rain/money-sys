package com.lawschool.controller;

import com.lawschool.service.CompetitionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @Descriptin  竞赛记录
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
@Controller
@RequestMapping("/competitionRecord")
public class CompetitionRecordController {

    @Autowired
    private CompetitionRecordService competitionRecordService;


}
