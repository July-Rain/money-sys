/*
package com.lawschool.service.elastics;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;

import java.util.List;

*/
/**
 * ClassName: ISearchClient
 * Description: TODO
 * date: 2019-1-11 14:05
 *
 * @author MengyuWu
 * @since JDK 1.8
 *//*

public interface ISearchClient {
    */
/**
     * 搜索结果
     *//*

    List<JSONObject> search(SearchRequest request);

    List<String> searchString(SearchRequest request);
    */
/**
     * 搜索
     *//*

    <T> List<T> search(SearchRequest request, Class<T> tClass);

    */
/**
     *
     * @param index 数据库名称
     * @param type 数据库表名
     * @param id 数据库主键ID
     * @param t 实体class
     * @param <T>
     * @return
     *//*

    <T> IndexResponse saveEntity(String index, String type, String id, T t);
}
*/
