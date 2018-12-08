package com.lawschool.service;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.Msg;
import com.lawschool.util.PageUtils;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MsgService extends AbstractService {
    /**
     * 新增消息
     * @param msg
     */
    public void add(Msg msg);

    /**
     * 展示所有消息
     * @return
     */
    public PageUtils selectAllMsg();

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
    //public void deleteBatchIds(String[] ids);
}
