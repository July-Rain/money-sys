package com.lawschool.controller.competition;


import com.lawschool.service.competition.MatchSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @Descriptin 擂台配置
 * @author      孙小康
 * @version     v1.0
 * @Time        2018/11/29
 *
 */
@Controller
@RequestMapping("/matchSetting")
public class MatchSettingController {

    @Autowired
    private MatchSettingService matchSettingService;


}
