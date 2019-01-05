package com.lawschool.form;

import com.lawschool.base.Page;

import java.util.Date;

/**
 * @ClassName CollectionPostForm
 * @Author wangtongye
 * @Date 2019/1/5 11:51
 * @Versiom 1.0
 **/
public class CollectionPostForm {

    private String id;
    private String userId;
    private String userName;
    private Date createTime;
    private String content;//内容
    private String subordinateColumn;//所属栏目
    private Integer collectionNum;//收藏
    private Integer commentNum;//评论
    private Integer reportNum;//举报
    private Page<CollectionPostForm> page;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubordinateColumn() {
        return subordinateColumn;
    }

    public void setSubordinateColumn(String subordinateColumn) {
        this.subordinateColumn = subordinateColumn;
    }

    public Integer getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(Integer collectionNum) {
        this.collectionNum = collectionNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getReportNum() {
        return reportNum;
    }

    public void setReportNum(Integer reportNum) {
        this.reportNum = reportNum;
    }

    public Page<CollectionPostForm> getPage() {
        return page;
    }

    public void setPage(Page<CollectionPostForm> page) {
        this.page = page;
    }
}
