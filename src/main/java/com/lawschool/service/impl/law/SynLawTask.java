package com.lawschool.service.impl.law;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.beans.law.LawClassifyEntity;
import com.lawschool.service.law.LawClassifyService;
import com.lawschool.service.law.SynLawService;
import com.lawschool.util.SysHttpClient;
import com.lawschool.util.UtilValidate;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: SynLawTask
 * Description: TODO
 * date: 2019-3-2 14:12
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@Component
@PropertySource("classpath:properties/sys.properties")
public class SynLawTask {
    @Autowired
    private SynLawService synLawService;

    @Autowired
    private LawClassifyService classifyService;

    @Value("${law_url}")
    private String url;
    @Value("${law_pageSize}")
    private String pageSize;
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    //@Scheduled(cron = "${law_cron}")
    public void synLawDesic(){
        System.out.println("=====================================================");
        System.out.println("定时任务");
        System.out.println("=====================================================");

        //调用相关接口
        String httpResult = SysHttpClient.doPostRequest(url+"mapi/advancedSearch.cbs"+"?pageNumber=1&pageSize=1", "");
        int count =0;
        //获取接口返回数据
        JSONObject jsonObject=JSONObject.fromObject(httpResult);
        if("true".equals(jsonObject.get("result"))&&UtilValidate.isNotEmpty(jsonObject.get("count"))){
            count= Integer.parseInt(jsonObject.get("count").toString());
        }
        int ps = Integer.parseInt(pageSize);
        for(int i=1;i<=count/ps;i++){
            Map<String,String> param = new HashMap<>();
            param.put("pageSize",pageSize);
            param.put("pageNumber",String.valueOf(i));
            synLawService.synClassDesic(param);

        }
        /*System.out.printf(httpResult);
        //获取所有的法律法规分类
        List<LawClassifyEntity> entityList = classifyService.selectList(new EntityWrapper<LawClassifyEntity>());

        for(int i=0;i<entityList.size();i++){
            Map<String,String> param = new HashMap<>();

        }*/

    }
}
