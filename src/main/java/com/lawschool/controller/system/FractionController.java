package com.lawschool.controller.system;

import com.alibaba.fastjson.JSON;
import com.lawschool.base.AbstractController;
import com.lawschool.beans.system.Fraction;
import com.lawschool.enums.Source;
import com.lawschool.service.system.FractionService;
import com.lawschool.util.Result;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * ClassName: FractionController
 * Description: TODO
 * date: 2019/3/89:49
 *
 * @author 王帅奇
 */

@RestController
@RequestMapping("/fraction")
public class FractionController  extends AbstractController {

    @Autowired
    private FractionService fractionService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody Map<String,Object> fractionList){

        List<Fraction> list = new LinkedList<>();

        for (Object obj : fractionList.values()) {
            list.add(JSON.parseObject(JSON.toJSONString(obj),Fraction.class));
        }

        fractionService.saveFraction(list);


        return Result.ok();
    }

    @RequestMapping(value = "/getFractionByType", method = RequestMethod.GET)
    public Result getFractionByType(){

        Result result = fractionService.getFractionByType("1",Source.AUDIOSTUDY);

        return Result.ok().put("result",result);
    }

    @RequestMapping( value = "/list", method = RequestMethod.GET)
    public Result list(){

        Result result = fractionService.getFraction();

        return result;
    }
}
