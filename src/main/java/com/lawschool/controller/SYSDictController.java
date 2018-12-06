package com.lawschool.controller;

import com.lawschool.beans.Dict;
import com.lawschool.service.DictService;
import com.lawschool.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/dict")
public class SYSDictController extends AbstractController{

    @Autowired
    DictService dictService;

    @Autowired
    RedisUtil redisUtil;



}
