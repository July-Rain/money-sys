package com.lawschool.controller;

import com.lawschool.util.SysHttpClient;
import net.sf.json.JSONObject;

/**
 * ClassName: TestController
 * Description: TODO
 * date: 2019-3-5 14:17
 *
 * @author MengyuWu
 * @since JDK 1.8
 */
public class TestController {
    public static void main(String[] args) {

       // String httpResult = SysHttpClient.doPostRequest("http://e.tbs.com.cn:8191/mapi/getClass.cbs", "");
        String httpResult = SysHttpClient.doPostRequest("http://e.tbs.com.cn:8191/mapi/advancedSearch.cbs"+"?pageNumber=1&pageSize=1&classNumber="+"5100901", "");

        System.out.printf(httpResult);
    }
}
