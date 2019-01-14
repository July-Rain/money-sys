/*
package com.lawschool.service.elastics;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

*/
/**
 * ClassName: EsSearchClient
 * Description: EsSearchClient
 * date: 2019-1-11 14:06
 *
 * @author MengyuWu
 * @since JDK 1.8
 *//*

@Service("searchClient")
public class EsSearchClient implements ISearchClient {
    @Autowired
    private RestHighLevelClient client;

    @Override
    public List<JSONObject> search(SearchRequest request) {
        try {
            SearchResponse response = client.search(request);
            if (response.getHits() == null) {
                return null;
            }
            List<JSONObject> list = new ArrayList<>();
            response.getHits().forEach(item -> list.add(JSON.parseObject(item.getSourceAsString())));
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return null;
    }

    @Override
    public List<String> searchString(SearchRequest request) {
        try {

            SearchResponse response = client.search(request);

            if (response.getHits() == null) {
                return null;
            }
            List<String> list = new ArrayList<>();
            response.getHits().forEach(item -> list.add(item.getSourceAsString()));
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> List<T> search(SearchRequest request, Class<T> tClass) {

        List<JSONObject> searchResponse = this.search(request);
        if (searchResponse == null) {
            return null;
        }
        List<T> list = new ArrayList<>(searchResponse.size());
        searchResponse.forEach(item -> list.add(JSON.parseObject(JSON.toJSONString(item), tClass)));
        return list;
    }

    @Override
    public <T> IndexResponse saveEntity(String index, String type, String id, T t) {
        IndexResponse indexResponse = null;
        try {
            IndexRequest indexRequest = new IndexRequest(index, type , id);
            indexRequest.source(JSON.toJSONString(t) , XContentType.JSON);
            */
/*BulkRequest bulkRequest = new BulkRequest();
            bulkRequest.add(indexRequest);
            Header basicHeader = new BasicHeader("Content-Type:application" , "json");*//*

            //this.client.bulk(bulkRequest , basicHeader);
            indexResponse = this.client.index(indexRequest);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return indexResponse;
    }
}
*/
