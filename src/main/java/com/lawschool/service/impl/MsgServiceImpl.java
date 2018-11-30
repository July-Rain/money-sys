package com.lawschool.service.impl;

import com.lawschool.beans.Msg;
import com.lawschool.dao.MsgDao;
import com.lawschool.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MsgServiceImpl implements MsgService {

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
    public List<Msg> selectAllMsg() {
        return msgDao.selectAllMsg();
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
}
