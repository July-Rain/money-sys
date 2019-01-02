package com.lawschool.beans;

import com.lawschool.base.DataEntity;

/**
 * @ClassName ManuscriptDao
 * @Author wangtongye
 * @Date 2018/12/26 19:07
 * @Versiom 1.0
 **/
public class ManuscriptEntity extends DataEntity<ManuscriptEntity> {

    private String auditUser;//审核人
    private String auditTime;//审核时间
    private String auditStatus;//审核状态
    private String manuscriptType;//稿件类型（试题、学习内容）

    //*******=================== 试题类型字段 =====================*******
    private String typeId;//试题类型
    private String questionDifficulty;//难度
    private String questionType;//题型
    private String answerChoiceNumber;//答案个数
    private String answerId;//正确答案的id
    private String specialKnowledgeId;//专项知识id
    private String policeClassification;//警种
    private String legalBasis;//法律依据
    private String answerDescrible;//答案描述

    //*******=================== 学习内容类型字段 =====================*******
    private String stuCode;//课件编码
    private String stuTitle;//标题

    //*******=================== 共用字段 =====================*******
    private String comContent;//内容
}
