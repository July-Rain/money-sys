package com.lawschool.dao;

import java.util.List;
import com.lawschool.beans.Msg;

public interface MsgDao {

    public void insert(Msg msg);

    public List<Msg> selectAllMsg();

    public Msg selectByMsgId(String id);

    public void updateByMsg(Msg msg);

    public void deleteByMsgId(String id);
}
