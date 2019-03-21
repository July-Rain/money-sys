package com.lawschool.controller.bbs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lawschool.annotation.SysLog;
import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.bbs.PostEntity;
import com.lawschool.beans.bbs.ReplyEntity;
import com.lawschool.controller.bbs.Sensitivefilter.SensitivewordFilter;
import com.lawschool.service.bbs.PostService;
import com.lawschool.service.bbs.ReplyService;
import com.lawschool.util.RedisUtil;
import com.lawschool.util.Result;
import com.lawschool.util.SpringContextUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @ClassName ReplyController
 * @Author wangtongye
 * @Date 2019/1/3 10:52
 * @Versiom 1.0
 **/
@RestController
@RequestMapping("/reply")
public class ReplyController extends AbstractController {

    @Autowired
    private ReplyService replyService;

    @Autowired
    private SensitivewordFilter sensitivewordFilter;

    @Autowired
    private PostService postService;
    @Autowired
    private RedisUtil redisUtil;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(@RequestParam Map<String, Object> params){

        String postId = (String)params.get("postId");
        String createUser = (String)params.get("createUser");

        ReplyEntity replyEntity = new ReplyEntity();
        replyEntity.setPostId(postId);
        if(StringUtils.isNotEmpty(createUser)){
            createUser = getUser().getId();
            replyEntity.setCreateUser(createUser);
        }
        String orderBy = " a.create_time desc";
        params.put("orderBy",orderBy);
        Page<ReplyEntity> page = replyService.findPage(new Page<ReplyEntity>(params), replyEntity);
        return Result.ok().put("page", page);
    }

    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public Result info(@PathVariable("id") String id){
        ReplyEntity replyEntity = replyService.findOne(id);
        return Result.ok().put("data", replyEntity);
    }

    @SysLog("添加回复")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody ReplyEntity entity){

        String string =  entity.getContent();

        Set<String> keyWordSet=new HashSet<>();
        keyWordSet= JSON.parseObject(redisUtil.get("sensitive"),new TypeReference<Set<String>>(){});

        Map map= sensitivewordFilter.doSensitiveFifter(string,keyWordSet);

        String size=map.get("size").toString();
        Set content= (Set) map.get("content");
        if(size.equals("0")){
            replyService.save(entity);
            PostEntity postEntity = postService.findOne(entity.getPostId());
            PostEntity newPostEntity = new PostEntity();
            newPostEntity.setId(postEntity.getId());
            newPostEntity.setCommentNum(postEntity.getCommentNum() == null ? 1 : postEntity.getCommentNum()+ 1);

            postService.updateNum(newPostEntity);
            return Result.ok();
        }
        else {
            String word = "";
            Iterator<String> iterator = content.iterator();
            while (iterator.hasNext()) {
                word = word+"  "+iterator.next();
            }
            return Result.ok().put("code", 1).put("word",word);
        }
    }

    @SysLog("删除回复")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result delete(@RequestBody List<String> ids){
        replyService.delete(ids);
        return Result.ok();
    }
}
