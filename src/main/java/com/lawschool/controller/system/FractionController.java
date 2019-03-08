package com.lawschool.controller.system;

import com.lawschool.base.AbstractController;
import com.lawschool.beans.system.Fraction;
import com.lawschool.enums.Source;
import com.lawschool.service.system.FractionService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public Result testSave(){
        Fraction fraction = new Fraction();
        fraction.setSource(Source.AUDIOSTUDY);
        fraction.setMinDemand(10.0);
        fraction.setScore(5.0);
        fraction.setDailyLimit(5.0);
        fraction.setFractionType("1");
        List<Fraction> list = new ArrayList<>();
        list.add(fraction);
        try {
            fractionService.saveFraction(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Result.ok();
    }

    @RequestMapping(value = "/getFractionByType", method = RequestMethod.GET)
    public Result getFractionByType(){

        Result result = fractionService.getFractionByType("1",Source.AUDIOSTUDY);

        return Result.ok().put("result",result);
    }
}
