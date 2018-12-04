package com.lawschool.controller;

import com.lawschool.beans.SysConfig;
import com.lawschool.service.BattlePlatformService;
import com.lawschool.service.CompetitionOnlineService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 *
 * @Descriptin  对战平台
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
@RestController
@RequestMapping("/battlePlatform")
public class BattlePlatformController {

    @Autowired
    private BattlePlatformService battlePlatformService;



}
