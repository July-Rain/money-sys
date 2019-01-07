package com.lawschool.beans.bbs;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

/**
 * @ClassName ReplyEntity
 * @Author wangtongye
 * @Date 2019/1/3 10:07
 * @Versiom 1.0
 **/
@TableName("law_reply")
public class ReplyEntity extends DataEntity<ReplyEntity> {

    private String postId;//回复评论
    private String content;//回复内容
    private String replyObject;//回复对象

    @TableField
    private String userName;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReplyObject() {
        return replyObject;
    }

    public void setReplyObject(String replyObject) {
        this.replyObject = replyObject;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
