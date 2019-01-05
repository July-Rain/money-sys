package com.lawschool.controller.bbs;

import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.bbs.PostEntity;
import com.lawschool.beans.bbs.ReplyEntity;
import com.lawschool.service.bbs.PostService;
import com.lawschool.service.bbs.ReplyService;
import com.lawschool.util.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    private PostService postService;

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

        Page<ReplyEntity> page = replyService.findPage(new Page<ReplyEntity>(params), replyEntity);
        return Result.ok().put("page", page);
    }

    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public Result info(@PathVariable("id") String id){
        ReplyEntity replyEntity = replyService.findOne(id);
        return Result.ok().put("data", replyEntity);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody ReplyEntity entity){
        replyService.save(entity);

        PostEntity postEntity = postService.findOne(entity.getPostId());
        postEntity.setCommentNum(postEntity.getCommentNum() == null ? 1 : postEntity.getCommentNum()+ 1);
        return Result.ok();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result delete(@RequestBody List<String> ids){
        replyService.delete(ids);
        return Result.ok();
    }
}
