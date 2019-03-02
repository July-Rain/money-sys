package com.lawschool.controller.law;

import com.lawschool.beans.law.LawClassifyEntity;
import com.lawschool.beans.law.LawClassifyLibEntity;
import com.lawschool.service.law.LawClassifyLibService;
import com.lawschool.service.law.LawClassifyService;
import com.lawschool.service.law.SynLawService;
import com.lawschool.util.Result;
import com.lawschool.util.SysHttpClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ClassName: SynLawController
 * Description: 同步法律法规库的controller
 * date: 2019-3-1 11:31
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/synlaw")
public class SynLawController {
    @Autowired
    private SynLawService lawService;

    @RequestMapping("synClass")
    public Result synClassify(){
       return  lawService.synClassify();
    }

    @RequestMapping("synClassLib")
    public Result synClassLib(){
        return  lawService.synClassLib();

    }
    @RequestMapping("synClassDesic")
    public Result synClassDesic(@RequestParam Map<String,String> param){
        return  lawService.synClassDesic(param);

    }

    @RequestMapping("lawDetail")
    public Result getDescInfo(String lawid,String rid){

        return  lawService.lawDetail(lawid,rid);
    }




}
