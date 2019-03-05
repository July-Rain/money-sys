package com.lawschool.service.impl.law;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.beans.law.LawClassifyEntity;
import com.lawschool.service.law.LawClassifyService;
import com.lawschool.service.law.SynLawService;
import com.lawschool.util.SysHttpClient;
import com.lawschool.util.UtilValidate;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

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

        /*//调用相关接口
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
        System.out.printf(httpResult);*/
        //获取所有的法律法规分类
        List<LawClassifyEntity> entityList = classifyService.selectList(new EntityWrapper<LawClassifyEntity>());

        for(int i=0;i<entityList.size();i++){
            //调用相关接口
            String httpResult = SysHttpClient.doPostRequest(url+"mapi/advancedSearch.cbs"+"?pageNumber=1&pageSize=1&classNumber="+entityList.get(i).getClassifyCode(), "");
            int count =0;
            //获取接口返回数据
            JSONObject jsonObject=JSONObject.fromObject(httpResult);
            if("true".equals(jsonObject.get("result"))&&UtilValidate.isNotEmpty(jsonObject.get("count"))){
                count= Integer.parseInt(jsonObject.get("count").toString());
            }
            int ps = Integer.parseInt(pageSize);
            for(int t=1;t<=count/ps;t++){
                Map<String,String> param = new HashMap<>();
                param.put("pageSize",pageSize);
                param.put("pageNumber",String.valueOf(t));
                param.put("classNumber",entityList.get(i).getId());
                synLawService.synClassDesic(param);

            }

        }

    }
    //@Scheduled(cron = "0 0/10 * * * ? ")
    public void synLawClassify(){
        //获取所有的法律法规分类
        List<LawClassifyEntity> entityList = classifyService.selectList(new EntityWrapper<LawClassifyEntity>().eq("syn_flag","0"));

        for(int i=0;i<entityList.size();i++){
            //调用相关接口
            String httpResult = SysHttpClient.sendGet(url+"page/loadTree.cbs", "method=dict-loadDictTree&jsonParam={\"parent\":\""+entityList.get(i).getId()+"\",\"rid\":\"classify\"}");
            JSONObject jsonObject=JSONObject.fromObject(httpResult);
            JSONArray jsonArray=JSONArray.fromObject(jsonObject.get("list"));
            List<LawClassifyEntity> entityListNew= new ArrayList<>();
            for (int t=0;t<jsonArray.size();t++){
                //设置数据
                JSONObject object = JSONObject.fromObject(jsonArray.get(t));
                LawClassifyEntity entity = new LawClassifyEntity();
                entity.setId(object.get("id").toString());
                entity.setClassifyCode(object.get("code").toString());
                entity.setClassifyId(object.get("id").toString());
                entity.setParentId(object.get("parentId").toString());
                entity.setClassifyName(object.get("name").toString());
                entity.setCreateTime(new Date());
                entityListNew.add(entity);
            }
            //更新数据库 有则更新  无则新增
            if(UtilValidate.isNotEmpty(entityListNew)){
                classifyService.insertOrUpdateBatch(entityListNew);
            }else{
                entityList.get(i).setSynFlag("1");
                classifyService.updateById(entityList.get(i));
            }

        }
    }
}
