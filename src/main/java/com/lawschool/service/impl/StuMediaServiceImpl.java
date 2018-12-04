package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.beans.StuMedia;
import com.lawschool.beans.User;
import com.lawschool.dao.StuMediaMapper;
import com.lawschool.dao.UserMapper;
import com.lawschool.service.StuMediaService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class StuMediaServiceImpl implements StuMediaService {

    @Autowired
    private StuMediaMapper mapper;

    //我的收藏-重点课程 -zjw
    @Override
    public PageUtils listMyCollection(Map<String, Object> param) {
        int pageNo=1;
        long pageSize=10l;
        if(UtilValidate.isNotEmpty(param.get("pageNo"))){
            pageNo=Integer.parseInt((String) param.get("pageNo"));
        }
        if(UtilValidate.isNotEmpty(param.get("pageSize"))){
            pageSize=Long.parseLong((String) param.get("pageSize"));
        }

        int count=mapper.cntMyCollection(param);

        param.put("startPage",(pageNo-1)*pageSize);
        param.put("endPage",pageNo*pageSize);

        List<StuMedia> stuMedias = mapper.listMyCollection(param);

        PageUtils page=new PageUtils(stuMedias,count,pageSize,pageNo);

        return page;
    }


    @Override
    public StuMedia getStuMedia(StuMedia stuMedia) {
        return mapper.selectById(stuMedia.getId());
    }

}
