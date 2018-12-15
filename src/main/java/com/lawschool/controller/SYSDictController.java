package com.lawschool.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.base.AbstractController;
import com.lawschool.beans.Dict;
import com.lawschool.beans.competition.RecruitConfiguration;
import com.lawschool.form.CommonForm;
import com.lawschool.service.DictService;
import com.lawschool.util.RedisUtil;
import com.lawschool.util.Result;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dict")
public class SYSDictController {

    @Autowired
    DictService dictService;

    @Autowired
    RedisUtil redisUtil;


    //根据type和父类code找  字典表数据  按ordernum排序
    @RequestMapping("/getByTypeAndParentcode")
    public Result getByTypeAndParentcode(@RequestParam Map<String, Object> params){
		String type = (String)params.get("type");
		String Parentcode = (String)params.get("Parentcode");
        EntityWrapper<Dict> ew = new EntityWrapper<>();
        if(UtilValidate.isNotEmpty(type)){
			ew.eq("TYPE",type);
		}
            if(UtilValidate.isNotEmpty(Parentcode)){
			ew.eq("PARENT_CODE",Parentcode);
		}
        ew.orderBy("ORDER_NUM",true);
          List<Dict> list= dictService.selectList(ew);

        return Result.ok().put("dictlist", list);
    }

    /**
     * @Author zjw
     * @Description 获取某一类型的字典值
     * @Date 10:00 2018-12-14
     * @Param [type]
     * @return com.lawschool.util.Result
    **/
    @RequestMapping("/getbyType/{type}")
    public Result getbyType(@PathVariable String type){
        List<CommonForm> forms = dictService.findByType(type);
        return Result.ok().put("forms",forms);
    }
}
