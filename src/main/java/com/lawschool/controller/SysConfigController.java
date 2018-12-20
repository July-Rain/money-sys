package com.lawschool.controller;

import com.lawschool.annotation.SysLog;
import com.lawschool.beans.SysConfig;
import com.lawschool.service.SysConfigService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

/**
 * ClassName: SysConfigController
 * Description: 系统参数配置
 * date: 2018-11-28 18:12
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/sysconfig")
public class SysConfigController {
    @Autowired
    private SysConfigService configService;

    /**
     * @Author MengyuWu
     * @Description 查询列表
     * @Date 17:48 2018-12-7
     * @Param [params]
     * @return com.lawschool.util.Result
     **/
    
    @RequestMapping("/list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = configService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * @Author MengyuWu
     * @Description 查看参数
     * @Date 22:38 2018-12-7
     * @Param [id]
     * @return com.lawschool.util.Result
     **/
    
    @RequestMapping("/info")
    public Result info(String id){
        SysConfig sysConfig = configService.selectById(id);
        return Result.ok().put("data", sysConfig);
    }
    /**
     * @Author MengyuWu
     * @Description 添加参数配置
     * @Date 17:45 2018-12-7
     * @Param [config]
     * @return com.lawschool.util.Result
     **/
    @SysLog("添加参数配置")
    @RequestMapping("/insert")
    public Result insert(@RequestBody SysConfig config){
//        config.setId(GetUUID.getUUIDs("SC"));
        configService.insert(config);
        return Result.ok().put("id",config.getId());
    }

    /**
     * @Author MengyuWu
     * @Description 更新参数配置
     * @Date 17:45 2018-12-7
     * @Param [config]
     * @return com.lawschool.util.Result
     **/
    @SysLog("更新参数配置")
    @RequestMapping("/update")
    public Result update(@RequestBody SysConfig config){
        configService.updateById(config);
        return Result.ok().put("id",config.getId());
    }

    /**
     * @Author MengyuWu
     * @Description 删除参数配置
     * @Date 17:46 2018-12-7
     * @Param [config]
     * @return com.lawschool.util.Result
     **/
    
    @SysLog("删除参数配置")
    @RequestMapping("/delete")
    public Result delete(@RequestBody String[] ids){
        configService.deleteBatchIds(Arrays.asList(ids));
        return Result.ok();
    }
}
