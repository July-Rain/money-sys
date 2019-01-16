/*
package com.lawschool.config.elastics;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

*/
/**
 * ClassName: ElasticsConfig
 * Description: ElasticsConfig
 * date: 2019-1-11 14:01
 *
 * @author MengyuWu
 * @since JDK 1.8
 *//*


@Configuration
public class ElasticsConfig {


    */
/**
     * 初始化
     *//*

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return getEsClientDecorator().getRestHighLevelClient();
    }

    @Bean
    @Scope("singleton")
    public ESClientDecorator getEsClientDecorator() {
        //可以配置集群 通过逗号隔开
        return new ESClientDecorator(new HttpHost("192.168.0.188",9200));
    }
}
*/
