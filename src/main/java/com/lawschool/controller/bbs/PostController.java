package com.lawschool.controller.bbs;

import com.lawschool.base.AbstractController;
import com.lawschool.base.Page;
import com.lawschool.beans.bbs.PostCollectionEntity;
import com.lawschool.beans.bbs.PostEntity;
import com.lawschool.form.CollectionPostForm;
import com.lawschool.form.PostFrom;
import com.lawschool.service.bbs.PostCollectionService;
import com.lawschool.service.bbs.PostService;
import com.lawschool.service.bbs.ReplyService;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName PostController
 * @Author wangtongye
 * @Date 2019/1/3 10:41
 * @Versiom 1.0
 **/
@RestController
@RequestMapping("/post")
public class PostController extends AbstractController {

    @Autowired
    private PostService postService;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private PostCollectionService postCollectionService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(@RequestParam Map<String, Object> params){

        String subordinateColumn = (String)params.get("subordinateColumn");

        PostEntity postEntity = new PostEntity();
        postEntity.setSubordinateColumn(subordinateColumn);

        Page<PostEntity> page = postService.findPage(new Page<PostEntity>(params), postEntity);
        return Result.ok().put("page", page);
    }

    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public Result info(@PathVariable("id") String id){
        PostEntity postEntity = postService.findOne(id);
        return Result.ok().put("data", postEntity);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody PostEntity entity){
        postService.save(entity);
        return Result.ok();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result delete(@RequestBody List<String> ids){
        postService.delete(ids);
        return Result.ok();
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public Result count(){
        PostFrom post = new PostFrom();
        post.setUserName(getUser().getUserName());
        post.setRelease(postService.findMyPost(getUser().getId()));
        post.setComment(replyService.findMyReply(getUser().getId()));
        post.setCollection(postCollectionService.findMyCollection(getUser().getId()));
        return Result.ok().put("data", post);
    }

    @RequestMapping(value = "/collection/{id}", method = RequestMethod.POST)
    public Result collection(@PathVariable("id") String id){
        PostCollectionEntity entity = new PostCollectionEntity();
        entity.setUserId(getUser().getId());
        entity.setPostId(id);
        postCollectionService.save(entity);

        PostEntity postEntity = postService.findOne(id);

        PostEntity newPostEntity = new PostEntity();
        newPostEntity.setId(postEntity.getId());
        newPostEntity.setCollectionNum(postEntity.getCollectionNum() == null ? 1 : postEntity.getCollectionNum()+ 1);

        postService.updateNum(newPostEntity);
        return Result.ok();
    }

    @RequestMapping(value = "/myCollection", method = RequestMethod.GET)
    public Result myCollection(@RequestParam Map<String, Object> params){
        Page<CollectionPostForm> page = postCollectionService.findPage(new Page<CollectionPostForm>(params), new CollectionPostForm());
        return Result.ok().put("page", page);
    }
}
