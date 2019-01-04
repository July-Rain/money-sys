package com.lawschool.service.bbs;

import com.lawschool.base.AbstractService;
import com.lawschool.beans.bbs.PostEntity;

public interface PostService extends AbstractService<PostEntity> {

    Integer findMyPost(String userId);
}
