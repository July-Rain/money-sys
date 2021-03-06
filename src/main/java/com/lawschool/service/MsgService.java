package com.lawschool.service;

import com.baomidou.mybatisplus.service.IService;
import com.lawschool.base.AbstractService;
import com.lawschool.beans.Msg;
import com.lawschool.util.PageUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface MsgService extends IService<Msg> {
    /**
     * 新增消息
     * @param msg
     */
    public void add(Msg msg);

    /**
     * 展示所有消息
     * @return
     */
    public PageUtils selectAllMsg(Map<String,Object> param);



    public PageUtils findMsgList(Map<String,Object> param);

    /**
     * 根据id查询单条消息
     * @param id
     * @return
     */
    public Msg selectByMsgId(String id);

    /**
     * 根据id修改消息
     * @param msg
     */
    public void updateByMsg(Msg msg);

    /**
     * 删除单条数据
     * @param id
     */
    public void deleteByMsgId(String id);

    /**
     * 删除多条数据
     * @param ids
     */
    public void deleteBatchIds(String[] ids);

    /**
     * 展示已发送消息(传入当前用户id，和分页参数)
     */
    PageUtils showSent(String recievePeople, Map<String,Object> param);
}
