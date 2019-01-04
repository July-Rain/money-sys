package com.lawschool.beans.bbs;

import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

/**
 * @ClassName PostEntity
 * @Author wangtongye
 * @Date 2019/1/3 10:00
 * @Versiom 1.0
 **/
@TableName("law_post")
public class PostEntity extends DataEntity<PostEntity> {

    private String titleName;//标题
    private String content;//内容
    private String subordinateColumn;//所属栏目
    private Integer collectionNum;//收藏
    private Integer commentNum;//评论
    private Integer reportNum;//举报

    private String userName;

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
