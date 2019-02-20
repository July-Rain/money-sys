package com.lawschool.controller.system;

import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.system.DictEntity;
import com.lawschool.form.CommonForm;
import com.lawschool.service.system.DictionService;
import com.lawschool.util.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Moon
 * @Date: 2019/2/15 16:39
 * @Description:
 */
@RestController
@RequestMapping("/dict")
public class DictController extends AbstractController{

    @Autowired
    private DictionService dictService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(@RequestParam Map<String, Object> params){

        // 初始化查询参数
        DictEntity entity = new DictEntity();
        if(params.get("parentCode") != null){
            entity.setParentCode(String.valueOf(params.get("parentCode")));
        }

        Page<DictEntity> page = dictService.findPage(
                new Page<DictEntity>(params), entity
        );

        return Result.ok().put("page", page);
    }

    /**
     * 保存
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody DictEntity entity){
        if(StringUtils.isNotBlank(entity.getParentCode())){
            entity.setIsParent(0);
        } else {
            entity.setIsParent(1);
        }

        boolean result = dictService.save(entity);
        if(result){
            return Result.ok();
        } else {
            return Result.error("保存失败...");
        }
    }

    /**
     * 获取字典信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Result info(@RequestParam String id){

        DictEntity entity = dictService.findOne(id);

        return Result.ok().put("info", entity);
    }

    /**
     * 删除
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result delete(@RequestParam("id") String id, @RequestParam("isParent") Integer isParent){

        boolean result = dictService.mydelete(id, isParent);
        if(result){
            return Result.ok();
        } else {

            return Result.error("删除失败...");
        }
    }

    /**
     * 竞赛中心请求
     * @param params
     * @return
     */
    @RequestMapping(value = "/getByTypeAndParentcode")
    public Result getByTypeAndParentcode(@RequestParam Map<String, Object> params){
        String code = (String)params.get("type");
        List<CommonForm> result = dictService.findCodeByType(code);

        return Result.ok().put("dictlist", result);
    }

    @RequestMapping(value = "/getbyType/{type}", method = RequestMethod.GET)
    public Result getbyType(@PathVariable String type){
        List<CommonForm> forms = dictService.findCodeByType(type);

        return Result.ok().put("forms",forms);
    }
}
