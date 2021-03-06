package com.lawschool.service.impl.law;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lawschool.beans.law.ClassifyDesicEntity;
import com.lawschool.beans.law.LawClassifyEntity;
import com.lawschool.beans.law.LawClassifyLibEntity;
import com.lawschool.service.law.ClassifyDesicService;
import com.lawschool.service.law.LawClassifyLibService;
import com.lawschool.service.law.LawClassifyService;
import com.lawschool.service.law.SynLawService;
import com.lawschool.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ClassName: SynLawServiceImpl
 * Description: 同步法律法规库impl
 * date: 2019-3-1 13:53
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@Service
public class SynLawServiceImpl implements SynLawService {
    @Autowired
    private LawClassifyLibService classifyLibService;

    @Autowired
    private LawClassifyService classifyService;

    @Autowired
    private ClassifyDesicService desicService;

    @Value("${law_url}")
    private String url;
    @Override
    public Result synClassify() {
//        JSONObject postJson = new JSONObject();
//        //封装请求体
//        postJson.put("keyword", "");
//        //调用相关接口
//        String httpResult = SysHttpClient.doPostRequest(url+"mapi/getClass.cbs", postJson.toString());
//
//        //获取接口返回数据
//        JSONObject jsonObject=JSONObject.fromObject(httpResult);
//        JSONArray jsonArray=JSONArray.fromObject(jsonObject.get("list"));
//        List<LawClassifyEntity> entityList= new ArrayList<>();
//        for (int i=0;i<jsonArray.size();i++){
//            //设置数据
//            JSONObject object = JSONObject.fromObject(jsonArray.get(i));
//            LawClassifyEntity entity = new LawClassifyEntity();
//            entity.setId(object.get("id").toString());
//            entity.setClassifyCode(object.get("code").toString());
//            entity.setClassifyId(object.get("id").toString());
//            entity.setParentId(object.get("parentId").toString());
//            entity.setClassifyName(object.get("name").toString());
//            entity.setCreateTime(new Date());
//            entityList.add(entity);
//        }
//        //更新数据库 有则更新  无则新增
//        classifyService.insertOrUpdateBatch(entityList);
//
//        return Result.ok().put("size",entityList.size());
        //获取所有的法律法规分类

            //调用相关接口
            String httpResult = SysHttpClient.sendGet(url+"page/loadTree.cbs", "method=dict-loadDictTree&jsonParam={\"parent\":\"0\",\"rid\":\"classify\"}");
            JSONObject jsonObject=JSONObject.fromObject(httpResult);
            JSONArray jsonArray=JSONArray.fromObject(jsonObject.get("data"));
            List<LawClassifyEntity> entityListNew= new ArrayList<>();
            for (int t=0;t<jsonArray.size();t++){
                //设置数据
                JSONObject object = JSONObject.fromObject(jsonArray.get(t));
                if(UtilValidate.isNotEmpty(object)){
                    LawClassifyEntity entity = new LawClassifyEntity();
                    entity.setId(object.get("key").toString());
                    if(UtilValidate.isNotEmpty(object.get("code"))){
                        entity.setClassifyCode(object.get("code").toString());
                    }
                    entity.setClassifyId(object.get("key").toString());
                    entity.setParentId(object.get("parent").toString());
                    entity.setClassifyName(object.get("text").toString());
                    entity.setCreateTime(new Date());
                    entityListNew.add(entity);
                }

            }
            //更新数据库 有则更新  无则新增
            if(UtilValidate.isNotEmpty(entityListNew)){
                classifyService.insertOrUpdateBatch(entityListNew);
            }

        return Result.ok().put("size",entityListNew.size());


    }

    @Override
    public Result synClassifyAll() {
        //获取所有的法律法规分类
        List<LawClassifyEntity> entityList = classifyService.selectList(new EntityWrapper<LawClassifyEntity>().eq("syn_flag","0"));

        for(int i=0;i<entityList.size();i++){
            //调用相关接口
            String httpResult = SysHttpClient.sendGet(url+"page/loadTree.cbs", "method=dict-loadDictTree&jsonParam={\"parent\":\""+entityList.get(i).getId()+"\",\"rid\":\"classify\"}");
            JSONObject jsonObject=JSONObject.fromObject(httpResult);
            JSONArray jsonArray=JSONArray.fromObject(jsonObject.get("data"));
            List<LawClassifyEntity> entityListNew= new ArrayList<>();
            for (int t=0;t<jsonArray.size();t++){
                //设置数据
                JSONObject object = JSONObject.fromObject(jsonArray.get(t));
                if(UtilValidate.isNotEmpty(object)){
                    LawClassifyEntity entity = new LawClassifyEntity();
                    entity.setId(object.get("key").toString());
                    entity.setClassifyCode(object.get("code").toString());
                    entity.setClassifyId(object.get("key").toString());
                    entity.setParentId(object.get("parent").toString());
                    entity.setClassifyName(object.get("text").toString());
                    entity.setCreateTime(new Date());
                    entityListNew.add(entity);
                }

            }
            //更新数据库 有则更新  无则新增
            if(UtilValidate.isNotEmpty(entityListNew)){
                classifyService.insertOrUpdateBatch(entityListNew);
            }
            entityList.get(i).setSynFlag("1");
            classifyService.updateById(entityList.get(i));

        }
        return Result.ok().put("size",entityList.size());
    }

    @Override
    public Result synClassLib() {
        JSONObject postJson = new JSONObject();
        //封装请求体
        postJson.put("keyword", "");
        //调用相关接口
        String httpResult = SysHttpClient.doPostRequest(url+"mapi/getLawdb.cbs", postJson.toString());

        System.out.printf(httpResult);
        //获取接口返回数据
        JSONObject jsonObject=JSONObject.fromObject(httpResult);
        JSONArray jsonArray=JSONArray.fromObject(jsonObject.get("list"));
        List<LawClassifyLibEntity> entityList= new ArrayList<>();
        for (int i=0;i<jsonArray.size();i++){
            //设置数据
            JSONObject object = JSONObject.fromObject(jsonArray.get(i));
            LawClassifyLibEntity entity = new LawClassifyLibEntity();
            entity.setId(GetUUID.getUUIDs("SC"));
            entity.setLibCode(object.get("code").toString());
            entity.setLibId(entity.getId());
            entity.setParentId("0");
            entity.setLibName(object.get("name").toString());
            entity.setCreateTime(new Date());
            entityList.add(entity);
        }
        //更新数据库 有则更新  无则新增
        classifyLibService.insertOrUpdateBatch(entityList);

        return Result.ok().put("size",entityList.size());

    }

    @Override
    public Result synClassDesic(Map<String,String> param)  {
        //封装请求体
        StringBuffer sb = new StringBuffer();
        sb.append("?title=").append(UtilValidate.isNotEmpty(param.get("title"))?param.get("title"):"");
        sb.append("&content=").append(UtilValidate.isNotEmpty(param.get("content"))?param.get("content"):"");
        sb.append("&issueOrganization=").append(UtilValidate.isNotEmpty(param.get("issueOrganization"))?param.get("issueOrganization"):"");
        sb.append("&issueDate1=").append(UtilValidate.isNotEmpty(param.get("issueDate1"))?param.get("issueDate1"):"");
        sb.append("&issueDate2=").append(UtilValidate.isNotEmpty(param.get("issueDate2"))?param.get("issueDate2"):"");
        sb.append("&classNumber=").append(UtilValidate.isNotEmpty(param.get("classNumber"))?param.get("classNumber"):"");
        sb.append("&rid=").append(UtilValidate.isNotEmpty(param.get("rid"))?param.get("rid"):"");
        sb.append("&pageNumber=").append(UtilValidate.isNotEmpty(param.get("pageNumber"))?param.get("pageNumber"):"");
        sb.append("&pageSize=").append(UtilValidate.isNotEmpty(param.get("pageSize"))?param.get("pageSize"):"");
        sb.append("&status=").append(UtilValidate.isNotEmpty(param.get("status"))?param.get("status"):"");
        //调用相关接口
        String httpResult = SysHttpClient.doPostRequest(url+"mapi/advancedSearch.cbs"+sb.toString(), "");

       System.out.printf(httpResult);
        //获取接口返回数据
        JSONObject jsonObject=JSONObject.fromObject(httpResult);
        int count =0;
        if(UtilValidate.isNotEmpty(jsonObject.get("count"))){
            count= Integer.parseInt(jsonObject.get("count").toString());
        }
        JSONArray jsonArray=JSONArray.fromObject(jsonObject.get("list"));
        List<ClassifyDesicEntity> entityList= new ArrayList<>();
        for (int i=0;i<jsonArray.size();i++){
            //设置数据
            JSONObject object = JSONObject.fromObject(jsonArray.get(i));
            if(UtilValidate.isNotEmpty(object.get("id")))
            {
                ClassifyDesicEntity entity = new ClassifyDesicEntity();
                entity.setId(object.get("id").toString());
                entity.setOrderNum(object.get("id").toString());
                entity.setLawTitle(object.get("name").toString());
                entity.setLibId(object.get("rid").toString());
                entity.setIssueTime(UtilString.String2Date(object.get("issuedate").toString()));
                entity.setStatus(UtilValidate.isNotEmpty(object.get("status"))?object.get("status").toString():"其他");
                entity.setIssueOrg(UtilValidate.isNotEmpty(object.get("issueddept"))?object.get("issueddept").toString():"");
                entity.setCreateTime(new Date());
                entity.setClassifyId(UtilValidate.isNotEmpty(object.get("subjectId"))?object.get("subjectId").toString():"");
                entityList.add(entity);
            }

        }
        //更新数据库 有则更新  无则新增
        desicService.insertOrUpdateBatch(entityList);
        //获取接口返回数据
        return Result.ok().put("size",entityList.size()).put("allCount",count);
    }

    @Override
    public Result lawDetail(String lawid, String rid) {
        JSONObject postJson = new JSONObject();
        //封装请求体
        //调用相关接口
        StringBuffer sb = new StringBuffer();

        sb.append("?lawid="+lawid);
        if(UtilValidate.isNotEmpty(rid)){
            sb.append("&rid="+rid);
        }

        //调用相关接口
        String httpResult = SysHttpClient.doPostRequest(url+"mapi/lawDetail.cbs"+sb, "");
        JSONObject jsonObject=JSONObject.fromObject(httpResult);
        return Result.ok().put("info",jsonObject);
    }

    @Override
    public Result synClassDesicList(Map<String, String> param) {
        //封装请求体
        StringBuffer sb = new StringBuffer();
        sb.append("?title=").append(UtilValidate.isNotEmpty(param.get("title"))?param.get("title"):"");
        sb.append("&content=").append(UtilValidate.isNotEmpty(param.get("content"))?param.get("content"):"");
        sb.append("&issueOrganization=").append(UtilValidate.isNotEmpty(param.get("issueOrganization"))?param.get("issueOrganization"):"");
        sb.append("&issueDate1=").append(UtilValidate.isNotEmpty(param.get("issueDate1"))?param.get("issueDate1"):"");
        sb.append("&issueDate2=").append(UtilValidate.isNotEmpty(param.get("issueDate2"))?param.get("issueDate2"):"");
        sb.append("&classNumber=").append(UtilValidate.isNotEmpty(param.get("classNumber"))?param.get("classNumber"):"");
        sb.append("&rid=").append(UtilValidate.isNotEmpty(param.get("rid"))?param.get("rid"):"");
        sb.append("&pageNumber=").append(UtilValidate.isNotEmpty(param.get("pageNumber"))?param.get("pageNumber"):"");
        sb.append("&pageSize=").append(UtilValidate.isNotEmpty(param.get("pageSize"))?param.get("pageSize"):"");
        sb.append("&status=").append(UtilValidate.isNotEmpty(param.get("status"))?param.get("status"):"");
        //调用相关接口
        String httpResult = SysHttpClient.doPostRequest(url+"mapi/advancedSearch.cbs"+sb.toString(), "");

        System.out.printf(httpResult);
        //获取接口返回数据
        JSONObject jsonObject=JSONObject.fromObject(httpResult);
        //获取接口返回数据
        return Result.ok().put("allCount",jsonObject);
    }
}
