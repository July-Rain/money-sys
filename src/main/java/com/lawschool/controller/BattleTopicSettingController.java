package com.lawschool.controller;

import com.lawschool.service.BattlePlatformService;
import com.lawschool.service.BattleTopicSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @Descriptin  对战题目配置
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
@Controller
@RequestMapping("/battleTopicSetting")
public class BattleTopicSettingController {

    @Autowired
    private BattleTopicSettingService battleTopicSettingService;


}
