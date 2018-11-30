package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.beans.StuMedia;
import com.lawschool.beans.User;
import com.lawschool.dao.StuMediaMapper;
import com.lawschool.dao.UserMapper;
import com.lawschool.service.StuMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class StuMediaServiceImpl implements StuMediaService {

    @Autowired
    private StuMediaMapper mapper;

    @Override
    public List<StuMedia> listMyCollection(Map<String, Object> param) {
        return mapper.listMyCollection(param);
    }

    @Override
    public int deleteStuMedia(StuMedia stuMedia) {
        return mapper.deleteById(stuMedia.getId());
    }

    @Override
    public StuMedia getStuMedia(StuMedia stuMedia) {
        return mapper.selectById(stuMedia.getId());
    }

    @Override
    public int insert(StuMedia stuMedia, User user) {
        stuMedia.setId(IdWorker.get32UUID());
        stuMedia.setStuIsstime(new Date());//发布时间
        stuMedia.setOptuser(user.getFullName());//操作人
        stuMedia.setOpttime(new Date());//操作时间
        return mapper.insert(stuMedia);
    }

    @Override
    public int update(StuMedia stuMedia,User user) {
        stuMedia.setOptuser(user.getFullName());
        stuMedia.setOpttime(new Date());
        stuMedia.setStuOptdepartment(user.getOrgCode());
        return mapper.updateById(stuMedia);
    }



}
