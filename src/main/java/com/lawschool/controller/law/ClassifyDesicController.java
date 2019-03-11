package com.lawschool.controller.law;

import com.lawschool.base.AbstractController;
import com.lawschool.service.law.ClassifyDesicService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * ClassName: ClassifyDesicController
 * Description: TODO
 * date: 2018-12-26 19:00
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/classdesic")
public class ClassifyDesicController extends AbstractController {
    @Autowired
    private ClassifyDesicService desicService;
    @RequestMapping("/list")
    public Result list(@RequestParam Map<String, String> params){
        PageUtils page = desicService.queryPage(params);
        return Result.ok().put("page", page);
    }

}
