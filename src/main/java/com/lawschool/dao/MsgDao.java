package com.lawschool.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.Msg;

public interface MsgDao extends BaseMapper<Msg> {

    void add(Msg msg);

    List<Msg> selectAllMsg();

     Msg selectByMsgId(String id);

     void updateByMsg(Msg msg);

     void deleteByMsgId(String id);
}
