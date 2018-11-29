package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.beans.Collection;
import com.lawschool.beans.User;
import com.lawschool.dao.CollectionMapper;
import com.lawschool.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    CollectionMapper collectionMapper;


    @Override
    public int delCollection(Collection collection, User user) {
        collection.setOpttime(new Date());
        collection.setDelStatus((short)1);
        EntityWrapper<Collection> ew=new EntityWrapper();
        ew.eq("COM_USERID",user.getUserId()).eq("DEL_STATUS",0).eq("ID",collection.getId());
        return collectionMapper.update(collection,ew);
    }

    @Override
    public int addCollection(Collection collection,User user) {
        collection.setId(IdWorker.get32UUID());
        collection.setComUserid(user.getUserId());
        collection.setOptuser(user.getFullName());
        collection.setOpttime(new Date());
        collection.setDelStatus((short)0);
        return collectionMapper.insert(collection);
    }


}
