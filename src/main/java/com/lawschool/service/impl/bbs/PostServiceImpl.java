package com.lawschool.service.impl.bbs;

import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.base.Page;
import com.lawschool.beans.User;
import com.lawschool.beans.bbs.PostEntity;
import com.lawschool.dao.bbs.PostDao;
import com.lawschool.service.bbs.PostCollectionService;
import com.lawschool.service.bbs.PostService;
import com.lawschool.service.bbs.ReplyService;
import com.lawschool.util.Result;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName PostServiceImpl
 * @Author wangtongye
 * @Date 2019/1/3 10:04
 * @Versiom 1.0
 **/
@Service
public class PostServiceImpl extends AbstractServiceImpl<PostDao, PostEntity> implements PostService {

    @Autowired
    private PostCollectionService postCollectionService;

    @Autowired
    private ReplyService replyService;

    @Override
    public Integer updateNum(PostEntity postEntity){
        return dao.updateNum(postEntity);
    }

    @Override
    public Integer findMyPost(String userId){
        return dao.findMyPost(userId);
    }

    @Override
    public Result mineList(Map<String, Object> params) {
        String mineType = (String) params.get("type");
        String subordinateColumn = (String)params.get("subordinateColumn");
        String status = (String) params.get("status");
        List<String> ids = new ArrayList<>();
        User user =  (User) SecurityUtils.getSubject().getPrincipal();
        if (PostEntity.TYPE_REALEASE.equals(mineType)){
            //获取所有我发表的内容
            ids = dao.findMineList(user.getId());
        }else if (PostEntity.TYPE_COLLECTION.equals(mineType)){
            //获取所有我的收藏
            ids = postCollectionService.findMyCollIds(user.getId());
        }else if (PostEntity.TYPE_COMMENT.equals(mineType)){
            //获取所有我评论的内容
            ids = replyService.findMyReplyIds(user.getId());
        }

        PostEntity postEntity = new PostEntity();
        postEntity.setIds(ids);
        postEntity.setStatus(status);
        if (StringUtils.isNotBlank(subordinateColumn)){
            postEntity.setSubordinateColumn(subordinateColumn);
        }

        String orderBy = " a.create_time desc";
        params.put("orderBy", orderBy);
        Page<PostEntity> page = this.findPage(new Page<PostEntity>(params), postEntity);

        return Result.ok().put("page",page);
    }

    @Override
    public void report(String id) {
        PostEntity postEntity = dao.selectById(id);
        postEntity.setStatus(PostEntity.STATUS_REPORT);
        postEntity.setReportNum(postEntity.getReportNum()==null?1:(postEntity.getReportNum()+1));
        dao.updateById(postEntity);
    }
}
