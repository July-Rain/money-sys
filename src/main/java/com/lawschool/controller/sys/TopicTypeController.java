package com.lawschool.controller.sys;

import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.User;
import com.lawschool.beans.system.TopicTypeEntity;
import com.lawschool.service.system.TopicTypeService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @version V1.0
 * @Description: 主题类型Controller
 * @author: 中石电子科技 徐祥
 * @date: 2018-12-11 11:36
 */
@RestController
@RequestMapping("/topic")
public class TopicTypeController extends AbstractController {

    @Autowired
    private TopicTypeService topicTypeService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result findPage(@RequestParam Map<String, Object> params){
        TopicTypeEntity entity = new TopicTypeEntity();
        if(params.get("name") != null){
            entity.setName(String.valueOf(params.get("name")));
        }

        Page<TopicTypeEntity> page = topicTypeService.findPage(new Page<TopicTypeEntity>(params), entity);

        return Result.ok().put("page", page);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody TopicTypeEntity entity){
        User user = getUser();
        entity.setCreateUser(user.getId());
        entity.preInsert();

        topicTypeService.mysave(entity);

        return Result.ok();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result delete(@RequestBody String id){
        topicTypeService.updateDelFlag(id);
        return Result.ok();
    }

}
