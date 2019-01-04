package com.lawschool.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lawschool.beans.Msg;
import com.lawschool.dao.MsgDao;
import com.lawschool.service.MsgService;
import com.lawschool.util.PageUtils;
import com.lawschool.util.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Integer.parseInt;

@Service
public class MsgServiceImpl extends ServiceImpl<MsgDao,Msg> implements MsgService {

    @Autowired
    MsgDao msgDao;

    /**
     * 新增站内消息
     * @param msg
     */
    @Override
    public void add(Msg msg) {
        msgDao.insert(msg);
    }

    /**
     * 显示所有站内信息
     * @return
     */
    @Override
    public PageUtils selectAllMsg() {
        int pageNo = 1;
        long pageSize = 10l;
        int count = msgDao.selectCount(new EntityWrapper<Msg>());
        List<Msg> msgList = msgDao.selectAllMsg();
        PageUtils page = new PageUtils(msgList,count,pageSize,pageNo);
        return page;
    }

    /**
     * 根据选中ID显示数据
     * @param id
     * @return
     */
    @Override
    public Msg selectByMsgId(String id) {
        return msgDao.selectByMsgId(id);
    }

    /**
     * 修改单条数据
     * @param msg
     */
    @Override
    public void updateByMsg(Msg msg) {
        msgDao.updateByMsg(msg);
    }

    /**
     * 删除单条数据
     * @param id
     */
    @Override
    public void deleteByMsgId(String id) {
        msgDao.deleteByMsgId(id);
    }

    @Override
    public void deleteBatchIds(String[] ids) {
        msgDao.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public PageUtils showSent(String recievePeople, Map<String, Object> param){
        int currPage= parseInt(Optional.ofNullable(param.get("currPage")).orElse("1").toString());
        long pageSize= parseInt(Optional.ofNullable(param.get("pageSize")).orElse("10").toString());
        //Page page = this.selectPage(new Query(param).getPage(), new EntityWrapper<Msg>().eq("RECIEVE_PEOPLE", userId));
        List<Msg> msgList = msgDao.showSent(recievePeople);
        for(Msg msg:msgList){
            System.out.println(msg.getRecieveDate());
            if(msg.getRecieveDate()==null){
                msg.setRecieveDate(new Date());
                msgDao.updateById(msg);
            }
        }

        int totalCount = msgList.size();
        PageUtils page = new PageUtils(msgList,totalCount,pageSize,currPage);

        return page;
    }


    //删除多条数据
    /*public void deleteBatchIds(String[] ids){
        //msgDao.deleteBatchIds(Arrays.asList(ids));
        for(String id:ids){
            msgDao.deleteByMsgId(id);
        }
    }*/
}

