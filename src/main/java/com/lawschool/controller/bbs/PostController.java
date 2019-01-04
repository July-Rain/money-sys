package com.lawschool.controller.bbs;

import com.lawschool.base.Page;
import com.lawschool.beans.bbs.PostEntity;
import com.lawschool.service.bbs.PostService;
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
public class PostController {

    @Autowired
    private PostService postService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(@RequestParam Map<String, Object> params){
        Page<PostEntity> page = postService.findPage(new Page<PostEntity>(params), new PostEntity());
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
}
