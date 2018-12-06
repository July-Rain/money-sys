package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.beans.Collection;
import com.lawschool.beans.User;
import com.lawschool.dao.CollectionMapper;
import com.lawschool.service.CollectionService;
import com.lawschool.util.Constant;
import com.lawschool.util.GetUUID;
import com.lawschool.util.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.lawschool.util.Constant.*;

@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    CollectionMapper collectionMapper;

    @Override
    //1 删  0 留  取消收藏
    public int delCollection(Collection collection, User user) {
        return this.changeStatus(collection,user,true);
    }

    @Override
    //添加收藏
    public int addCollection(Collection collection,User user) {
        String[] existStatus = this.isExist(collection,user);//收藏状态
        if(existStatus[0].equals(IS_NOT_EXIST+"")){//新增
            collection.setId(GetUUID.getUUIDs("CO"));
            collection.setComUserid(user.getId());
            collection.setOptuser(user.getFullName());
            collection.setOpttime(new Date());
            collection.setDelStatus((short)0);
            return collectionMapper.insert(collection)==1?SUCCESS:ERROR;//1 添加成功  0 添加失败
        }
        if(existStatus[0].equals(ONCE_EXIST+"")){//重新收藏
            collection.setOpttime(new Date());
            collection.setId(existStatus[1]);
            return this.changeStatus(collection,user,false)==1?SUCCESS:ERROR;//1 成功/ 0 失败
        }
        if(existStatus[0].equals(IS_EXITS+"")){
            return IS_EXITS;
        }
        return ERROR;
    }

    //判断是否被收藏过：
    private  String[]  isExist(Collection collection,User user){
        EntityWrapper<Collection> ew=new EntityWrapper();
        ew.eq("COM_USERID",user.getId()).eq("COM_STUCODE",collection.getComStucode()).eq("TYPE",collection.getType());
        List<Collection> collections = collectionMapper.selectList(ew);
        if(UtilValidate.isNotEmpty(collections) && collections.size()==1){
            if(collections.get(0).getDelStatus()==0) return new String[]{IS_EXITS+""};//2 目前被收藏中
            return  new String[]{ONCE_EXIST+"",collections.get(0).getId()};//1 之前被收藏过然后取消收藏了
        }
       return  new String[]{IS_NOT_EXIST+""};
    }

    //改变状态 true 取消收藏  false 重新收藏
    private int changeStatus(Collection collection,User user,boolean flag){
        collection.setOpttime(new Date());
        EntityWrapper<Collection> ew=new EntityWrapper();
        ew.eq("COM_USERID",user.getId()).eq("ID",collection.getId());
        if(flag){//0-》1  取消收藏
            collection.setDelStatus((short)1);
            ew.eq("DEL_STATUS",0);
        }else {//1-》0 重新收藏
            collection.setDelStatus((short)0);
            ew.eq("DEL_STATUS",1);
        }
        return collectionMapper.update(collection,ew)==1?SUCCESS:ERROR;//1 改变成功 0 改变失败
    }

}
