package com.lawschool.service;

import java.util.List;

import com.lawschool.beans.ExamConfig;

/***
 * 
 * @Title:ExamConfigService.java
 * @Description: 考试配置接口
 * @author: 王帅奇
 * @date 2018年11月29日
 */
public interface ExamConfigService {

	/**
	 * 
	 * @Description:新增考试配置
	 * @Pwarms:@param config
	 * @return:int
	 */
    public int insertConfig(ExamConfig config);

    /**
     * 
     * @Description:更新考试配置信息
     * @Pwarms:@param config
     * @Pwarms:@return
     * @return:int
     */
    public int updateConfig(ExamConfig config);

    /**
     * 
     * @Description:查询考试配置信息
     * @Pwarms:@param config
     * @Pwarms:@return
     * @return:List<ExamConfig>
     */
    public List<ExamConfig> listConfig(ExamConfig config);

    /**
     * 
     * @Description:删除考试配置
     * @Pwarms:@param sysCofig
     * @Pwarms:@return
     * @return:int
     */
    public int deleteConfig(ExamConfig examConfig);
    
    /**
     * 
     * @Description:查询考试配置详情
     * @Pwarms:@param ExamConfig
     * @Pwarms:@return
     * @return:SysConfig
     */
    public ExamConfig queryConfig(ExamConfig examConfig);
}
