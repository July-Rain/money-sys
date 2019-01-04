package com.lawschool.dao.bbs;

import com.lawschool.base.AbstractDao;
import com.lawschool.beans.bbs.ReplyEntity;

import java.util.List;

public interface ReplyDao extends AbstractDao<ReplyEntity> {
    void deleteByPostId(List<String> ids);
}
