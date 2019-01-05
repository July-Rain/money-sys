package com.lawschool.beans.bbs;

import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

/**
 * @ClassName PostCollectionEntity
 * @Author wangtongye
 * @Date 2019/1/3 10:33
 * @Versiom 1.0
 **/
@TableName("law_post_conllection")
public class PostCollectionEntity extends DataEntity<PostCollectionEntity> {
    private String userId;
    private String postId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
