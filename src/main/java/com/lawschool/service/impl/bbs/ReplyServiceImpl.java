package com.lawschool.service.impl.bbs;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.bbs.ReplyEntity;
import com.lawschool.dao.bbs.ReplyDao;
import com.lawschool.service.bbs.ReplyService;
import org.springframework.stereotype.Service;

/**
 * @ClassName ReplyServiceImpl
 * @Author wangtongye
 * @Date 2019/1/3 10:22
 * @Versiom 1.0
 **/
@Service
public class ReplyServiceImpl extends AbstractServiceImpl<ReplyDao, ReplyEntity> implements ReplyService {
}
