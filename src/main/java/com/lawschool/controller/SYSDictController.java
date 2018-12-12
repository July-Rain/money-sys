package com.lawschool.controller;

import com.lawschool.base.AbstractController;
import com.lawschool.service.DictService;
import com.lawschool.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dict")
public class SYSDictController {

    @Autowired
    DictService dictService;

    @Autowired
    RedisUtil redisUtil;



}
