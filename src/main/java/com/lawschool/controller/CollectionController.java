package com.lawschool.controller;

import com.lawschool.beans.Collection;
import com.lawschool.service.CollectionService;
import com.lawschool.util.Constant;
import com.lawschool.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.lawschool.util.Constant.SUCCESS;


@RestController
@RequestMapping("/coll")

public class CollectionController extends AbstractController{


    @Autowired
    CollectionService collectionService;

    @RequestMapping("/delColl")
    public Result delCollection(Collection collection){
        int rest = collectionService.delCollection(collection, getUser());
        return rest==SUCCESS?Result.ok():Result.error("取消收藏失败");
    }

    @RequestMapping("/addColl")
    public Result addCollection(Collection collection){
        int rest = collectionService.addCollection(collection,getUser());
        return rest== SUCCESS ?Result.ok():Result.error("收藏失败");
    }


}
