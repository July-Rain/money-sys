package com.lawschool.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.Msg;

public interface MsgDao extends BaseMapper<Msg> {

    public void add(Msg msg);

    public List<Msg> selectAllMsg();

    public Msg selectByMsgId(String id);

    public void updateByMsg(Msg msg);

    public void deleteByMsgId(String id);
}
