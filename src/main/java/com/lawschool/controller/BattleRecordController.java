package com.lawschool.controller;

import com.lawschool.service.BattlePlatformService;
import com.lawschool.service.BattleRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @Descriptin  对战记录
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
@Controller
@RequestMapping("/battleRecord")
public class BattleRecordController {

    @Autowired
    private BattleRecordService battleRecordService;


}
