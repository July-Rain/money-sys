package com.lawschool.controller.medal;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.annotation.SysLog;
import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.medal.MedalEntity;
import com.lawschool.beans.medal.UserMedalEntity;
import com.lawschool.service.medal.MedalService;
import com.lawschool.service.medal.UserMedalService;
import com.lawschool.util.GeneralRuntimeException;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/medal")
public class MedalController extends AbstractController {

    @Autowired
    private MedalService medalService;
    @Autowired
    private UserMedalService userMedalService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(@RequestParam Map<String, Object> params) {

        String titleName = (String) params.get("titleName");

        MedalEntity medalEntity = new MedalEntity();
        medalEntity.setTitleName(titleName);
        medalEntity.setIntegral((String) params.get("jifen"));//积分
        medalEntity.setCredit((String) params.get("xuefen"));//学分
        Page<MedalEntity> page = medalService.findPage(new Page<MedalEntity>(params), medalEntity);
        return Result.ok().put("page", page);
    }

    /**
     * 查询单个勋章
     */
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public Result info(@PathVariable("id") String id) {
        MedalEntity medal = medalService.findOne(id);
        return Result.ok().put("data", medal);
    }

    /**
     * 保存勋章
     */
    @SysLog("保存勋章")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody MedalEntity entity) {


//        entity.setCreateTime(new Date());
//        entity.setCreateUser(getUser().getId());
        medalService.save(entity);
        return Result.ok();
    }

    /**
     * 删除勋章
     */
    @SysLog("删除勋章")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result deleteById(@RequestBody List<String> ids) {
        medalService.delete(ids);
        return Result.ok();
    }

    /**
     * 可领取的勋章
     * @return
     */
    @RequestMapping(value = "/medalList", method = RequestMethod.GET)
    public Result medal(){
        //TODO
        return Result.ok();
    }

    /**
     * 新增我的勋章（领取勋章）
     */
    @SysLog("新增我的勋章（领取勋章）")
    @RequestMapping(value = "/saveMedal/{id}", method = RequestMethod.POST)
    public Result saveMedal(@PathVariable("id") String id) {

        UserMedalEntity entity = new UserMedalEntity();
        entity.setUserId(getUser().getId());
        entity.setMedalId(id);
        entity.setIsWear("0");

        if(userMedalService.checkUserMedal(entity)){
            throw new GeneralRuntimeException("勋章已经领取过！");
        }
        userMedalService.save(entity);
        return Result.ok();
    }

    /**
     * 查询我的勋章
     */
    @RequestMapping(value = "/myMedal", method = RequestMethod.GET)
    public Result myMedal() {
        List<String> list = userMedalService.findMedalIdList(getUser().getId());
        return Result.ok().put("list", list);
    }

    /**
     * 佩戴勋章
     */
    @SysLog("佩戴勋章")
    @RequestMapping(value = "/wear/{id}", method = RequestMethod.POST)
    public Result wear(@PathVariable("id") String id) {
        userMedalService.enbleWear(getUser().getId(), id);
        return Result.ok();
    }


   //勋章名称重复问题
    @RequestMapping("/medalName")
    public Result medalName(String medalname,String mytype,String id){
        String type="";

        MedalEntity medalEntity=null;

        if(mytype.equals("1"))
        {
             medalEntity= medalService.selectOne(new EntityWrapper<MedalEntity>().eq("title_name",medalname));

        }
        else if(mytype.equals("2"))
        {
             medalEntity= medalService.selectOne(new EntityWrapper<MedalEntity>().eq("title_name",medalname).ne("ID",id));

        }

        if(medalEntity==null)
        {
            type="0";
        }
        else
        {
            type="1";
        }
        return Result.ok().put("type",type);

    }
}
