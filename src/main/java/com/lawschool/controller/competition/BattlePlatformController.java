package com.lawschool.controller.competition;

import com.lawschool.service.competition.BattlePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
