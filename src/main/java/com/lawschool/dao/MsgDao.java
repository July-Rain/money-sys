package com.lawschool.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lawschool.beans.Msg;
import org.apache.ibatis.annotations.Param;

public interface MsgDao extends BaseMapper<Msg> {

    void add(Msg msg);

    List<Msg> selectAllMsg();

     Msg selectByMsgId(String id);

     void updateByMsg(Msg msg);

     void deleteByMsgId(String id);


    List<Msg> selectAllMsgByUserid(Map<String,Object> param);
    int selectAllMsgCont(Map<String,Object> param);

    List<Msg> findMsgList(Map<String,Object> param);
    int findMsgListCont(Map<String,Object> param);
     List<Msg> showSent(@Param("recievePeople")String recievePeople);

}
