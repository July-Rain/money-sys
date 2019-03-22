package com.lawschool.beans.bbs;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lawschool.base.DataEntity;

import java.util.List;

/**
 * @ClassName PostEntity
 * @Author wangtongye
 * @Date 2019/1/3 10:00
 * @Versiom 1.0
 **/
@TableName("law_post")
public class PostEntity extends DataEntity<PostEntity> {

    public static final String TYPE_REALEASE = "realease";  //发表的内容
    public static final String TYPE_COMMENT =  "comment";   //评论
    public static final String TYPE_COLLECTION = "collection";  //收藏的内容

    public static final String STATUS_NORMAL = "0";     //正常
    public static final String STATUS_REPORT = "1";     //被举报
    public static final String STATUS_REPORT_SUCCESS="2";   //举报成功

    private String content;//内容
    private String subordinateColumn;//所属栏目
    private Integer collectionNum;//收藏
    private Integer commentNum;//评论
    private Integer reportNum;//举报
    private String orgCode; //发表评论时所在部门
    private String status;  //状态 0正常，1，被举报，2，举报成功


    @TableField(exist = false)
    private String userName;

    @TableField( exist = false)
    private List<String> ids;   //内容ids

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
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
