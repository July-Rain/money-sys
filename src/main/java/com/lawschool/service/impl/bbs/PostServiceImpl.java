package com.lawschool.service.impl.bbs;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.bbs.PostEntity;
import com.lawschool.dao.bbs.PostDao;
import com.lawschool.service.bbs.PostService;
import org.springframework.stereotype.Service;

/**
 * @ClassName PostServiceImpl
 * @Author wangtongye
 * @Date 2019/1/3 10:04
 * @Versiom 1.0
 **/
@Service
public class PostServiceImpl extends AbstractServiceImpl<PostDao, PostEntity> implements PostService {

    public Integer findMyPost(String userId){
        return dao.findMyPost(userId);
    }
}
