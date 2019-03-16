package com.lawschool.service.impl.personalCenter;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.lawschool.base.AbstractServiceImpl;
import com.lawschool.beans.User;
import com.lawschool.beans.personalCenter.CollectionEntity;
import com.lawschool.dao.personalCenter.CollectionDao;
import com.lawschool.service.personalCenter.CollectionService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Auther: Moon
 * @Date: 2019/3/8 15:08
 * @Description:
 */
@Service
public class CollectionServiceImpl extends AbstractServiceImpl<CollectionDao, CollectionEntity> implements CollectionService {

    @Override
    public Integer getTotalQuestion(String userId, Integer type){

        return dao.getTotalQuestion(userId, type);
    }

    @Override
    public List<String> getQuestionsForPaper(Integer source, Integer num, String userId){

        return dao.getQuestionsForPaper(source, num, userId);
    }

    /**
     * 收藏功能
     * @param sourceId 资源ID
     * @param source 来源，见CollectionEntity的静态常量
     * @param isCollect 收藏 true/取消收藏 false
     * @return
     */
    @Override
    public boolean doCollect(String sourceId, Integer source, boolean isCollect, String answers){

        User user = (User)SecurityUtils.getSubject().getPrincipal();

        // 判断是否收藏过，已收藏则不做处理
        int num = dao.isCollect(sourceId, user.getId(), source);

        if(isCollect){
            // 收藏

            if(num > 0){ return true; }

            // 封装保存参数
            CollectionEntity entity = new CollectionEntity();
            entity.setId(IdWorker.getIdStr());
            entity.setType(source);
            entity.setCreateUser(user.getId());
            entity.setCreateTime(new Date());
            entity.setAnswers(answers);
            entity.setSourceId(sourceId);

            return dao.mySave(entity);

        } else {
            // 取消收藏
            if(num == 0){ return true; }

            return dao.myDelete(sourceId, user.getId(), source);
        }
    }

    /**
     * 判断是否收藏
     * @param sourceId 资源ID
     * @param userId 用户ID
     * @param source 来源，见CollectionEntity定义的静态常量
     * @return
     */
    @Override
    public boolean isCollect(String sourceId, String userId, Integer source){
        int num = dao.isCollect(sourceId, userId, source);

        if(num > 0){
            return true;

        } else {
            return false;
        }

    }
}
