package com.lawschool.service.law;

import com.lawschool.util.Result;

import java.util.Map;

/**
 * ClassName: SynLawService
 * Description: 同步法律法规接口数据
 * date: 2019-3-1 13:48
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
public interface SynLawService {

    /**
     * @Author MengyuWu
     * @Description 同步法律法规主题分类
     * @Date 13:50 2019-3-1
     * @Param []
     * @return com.lawschool.util.Result
     **/
    
    Result synClassify();

    /**
     * @Author MengyuWu
     * @Description 同步法律法规库
     * @Date 13:50 2019-3-1
     * @Param []
     * @return com.lawschool.util.Result
     **/
    
    Result synClassLib();

    /**
     * @Author MengyuWu
     * @Description 同步法律法规决定
     * @Date 13:52 2019-3-1
     * @Param []
     * @return com.lawschool.util.Result
     **/
    
    Result synClassDesic(Map<String,String> param);

    /**
     * @Author MengyuWu
     * @Description 根据法规的唯一标识获取法规的详细内容
     * @Date 10:52 2019-3-2
     * @Param [lawid]
     * @return com.lawschool.util.Result
     **/
    
    Result lawDetail(String lawid,String rid);
}
