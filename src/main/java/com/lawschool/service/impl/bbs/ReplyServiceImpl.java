package com.lawschool.service.impl.bbs;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.bbs.ReplyEntity;
import com.lawschool.dao.bbs.ReplyDao;
import com.lawschool.service.bbs.ReplyService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ReplyServiceImpl
 * @Author wangtongye
 * @Date 2019/1/3 10:22
 * @Versiom 1.0
 **/
@Service
public class ReplyServiceImpl extends AbstractServiceImpl<ReplyDao, ReplyEntity> implements ReplyService {

    @Override
    public Integer findMyReply(String userId){
        return dao.findMyReply(userId);
    }

    @Override
    public List<String> findMyReplyIds(String userId) {
        return dao.findMyReplyIds(userId);
    }
}
