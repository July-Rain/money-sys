package com.lawschool.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.annotation.SysLog;
import com.lawschool.base.AbstractController;
import com.lawschool.beans.Dict;
import com.lawschool.beans.competition.RecruitConfiguration;
import com.lawschool.service.DictService;
import com.lawschool.util.RedisUtil;
import com.lawschool.util.Result;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
		ew.eq("DEL_FLAG",1);
        ew.orderBy("ORDER_NUM",true);
          List<Dict> list= dictService.selectList(ew);

        return Result.ok().put("dictlist", list);
    }

    /**
     * 查询所有字典数据
     */
    @RequestMapping("/dictList")
    public Object selectDictList(){
        List<Map<String,Object>> dictList = dictService.queryForZtree();
        return dictList;
    }

    /**
     * 实现实体类里面list包 下级  下级list在包下级的 树结构数据
     */
    @RequestMapping("/listAllDictTree")
    public Result listAllDictTree(){
        List<Dict> sysDictList = new ArrayList<Dict>();
        Dict dict = new Dict();
        dict.setId("0");
        dict.setParentCode("0");
        dict.setName("一级菜单");
        sysDictList.add(dict);
        sysDictList.addAll(dictService.selectAllDict());
        return Result.ok().put("listAllDictTree",sysDictList);
    }

    @RequestMapping("/info")
    public Result info(String id){
        Dict dict = dictService.selectByDictId(id);
        if(dict.getParentCode().equals("0")){
            dict.setName(dict.getValue());
        }else{
            Dict dictFu = dictService.selectByCode(dict.getParentCode());
            dict.setParentCode(dictFu.getCode());
        }
        return Result.ok().put("data",dict);
    }

        @SysLog("删除参数配置")
        @RequestMapping("/delete")
        public Result delete(String id){
        dictService.deleteByDictId(id);
        /*Dict dict = dictService.selectByDictId(id);
        String str = "";
        List<Dict> list = dictService.queryListParentCode(dict.getCode());
            if(list.size()>0)
            {
//              return Result.ok().put("str","有子节点");
                str="有子节点";
            }

            if(str.equals("有子节点"))
            {
                return Result.ok().put("str",str);
            }
            else {
                dictService.deleteByCode(dict.getCode());
                str="删除成功！";
            }*/
            return Result.ok().put("str","删除成功！");
    }

    @SysLog("更新目录")
    @RequestMapping("/update")
    public Result update(@RequestBody Dict dict){
        dictService.updateById(dict);
        return Result.ok().put("id",dict.getId());
    }

    @SysLog("添加目录")
    @RequestMapping("/insert")
    public Result insert(@RequestBody Dict dict){
        dict.setId(IdWorker.getIdStr());
        dictService.insert(dict);
        return Result.ok().put("id",dict.getId());
    }

}
