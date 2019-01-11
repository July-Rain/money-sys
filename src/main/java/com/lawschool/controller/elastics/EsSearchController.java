package com.lawschool.controller.elastics;

import com.lawschool.beans.User;
import com.lawschool.service.elastics.ISearchClient;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * ClassName: EsSearchController
 * Description: EsSearchController
 * date: 2019-1-11 14:13
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
@Controller
@RequestMapping("essearch")
public class EsSearchController {

    @Autowired
    private ISearchClient iSearchClient;
    @RequestMapping("/testEs")
    @ResponseBody
    public void testEs() throws Exception {

        //插入数据
        for(int i=11;i<20;i++){
            User user = new User();
            user.setUserName("es测试数据名称1");
            user.setFullName("测试企业2");
            user.setId(String.valueOf(i));
            IndexResponse indexResponse = iSearchClient.saveEntity("mytest", "testBean", i+"",user );
            System.out.println(indexResponse.toString());
        }

        //es查询
        SearchRequest request  =new SearchRequest();
        //数据库名称
        request.indices("mytest");
        //表名
        request.types("testBean");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchQuery("userName", "es测试数据名称1"))
                .query(QueryBuilders.matchQuery("fullName", "测试企业2"));
        //设置排序
        //sourceBuilder.sort("id",SortOrder.DESC);
        //设置分页
        sourceBuilder.from(0);
        sourceBuilder.size(5);
        request.source(sourceBuilder);

        List<User> users	= iSearchClient.search(request, User.class);
        System.out.println("user :"+users.toString());
    }

    @RequestMapping("/testMoreEs")
    @ResponseBody
    public void testMoreEs() throws Exception {
        //多条件设置
        SearchRequest request  =new SearchRequest();
        request.indices("mytest");
        request.types("testBean");


        MatchPhraseQueryBuilder mpq1 = QueryBuilders.matchPhraseQuery("name","数据");
        MatchPhraseQueryBuilder mpq2 = QueryBuilders.matchPhraseQuery("companyname","企业");
        QueryBuilder qb2 = QueryBuilders.boolQuery().must(mpq1).must(mpq2);
        SearchSourceBuilder sourceBuilderm = new SearchSourceBuilder();
        sourceBuilderm.query(qb2);
        sourceBuilderm.sort("id",SortOrder.DESC);
        sourceBuilderm.from(0);
        sourceBuilderm.size(5);
        request.source(sourceBuilderm);
        List<User>  users	= iSearchClient.search(request, User.class);
        System.out.println("user :"+users.toString());
    }
}
