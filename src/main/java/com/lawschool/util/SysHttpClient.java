package com.lawschool.util;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * @author MengyuWu
 * @Title:
 * @Package
 * @Description: httpclient接口
 * @date 2018/3/2210:00
 */
public class SysHttpClient {

    public static String doPostRequest(String url,String jsonData) {
        HttpClient httpClient = null;
        PostMethod postMethod = null;
        int statusCode = 0;
        String response = "";
        try {
            httpClient = new HttpClient();
            System.out.println(url);
            postMethod = new PostMethod(url);
            //传递参数
            StringRequestEntity entity=new StringRequestEntity(jsonData,"application/json","utf-8");
            postMethod.setRequestEntity(entity);

            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(200000000);

            httpClient.getHttpConnectionManager().getParams().setSoTimeout(200000000);


            statusCode = httpClient.executeMethod(postMethod);

            Cookie[] cookies = httpClient.getState().getCookies();

            StringBuffer tmpcookies = new StringBuffer();
            for (Cookie c : cookies) {
                tmpcookies.append(c.toString() + ";");
                System.out.println("cookies = "+c.toString());
                System.out.println(c.getName()+c.getValue());
            }

            if (statusCode == HttpStatus.SC_OK) {
                // statusCode=200 返回成功
                InputStream inputStream = null;
                InputStreamReader inputStreamReader = null;
                BufferedReader bufferedReader = null;
                String tmp = null;
                // 读取返回报文
                StringBuffer resp = new StringBuffer();
                try {
                    inputStream = postMethod.getResponseBodyAsStream();
                    inputStreamReader = new InputStreamReader(inputStream,
                            "UTF-8");
                    bufferedReader = new BufferedReader(inputStreamReader);
                    while ((tmp = bufferedReader.readLine()) != null) {
                        resp.append(tmp).append("\n");
                    }
                    response = resp.toString();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    // 关闭client链接 关闭流
                    postMethod.releaseConnection();
                    IOUtils.closeQuietly(inputStream);
                    IOUtils.closeQuietly(inputStreamReader);
                    IOUtils.closeQuietly(bufferedReader);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


    public static Cookie[] doPostRequest(String url, String jsonData, HttpServletRequest request, HttpServletResponse httpServletResponse) throws IOException {
        HttpClient httpClient = null;
        PostMethod postMethod = null;
        int statusCode = 0;

        httpClient = new HttpClient();

        postMethod = new PostMethod(url);
        //传递参数
        StringRequestEntity entity = new StringRequestEntity(jsonData, "application/json", "utf-8");


        httpClient.getHttpConnectionManager().getParams().setSoTimeout(20000);

        httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);


        statusCode = httpClient.executeMethod(postMethod);

        Cookie[] cookies = httpClient.getState().getCookies();

        StringBuffer tmpcookies = new StringBuffer();

        for (Cookie c : cookies) {
            tmpcookies.append(c.toString() + ";");
            //System.out.println("cookies = " + c.toString());
            System.out.println(c.getName() + c.getValue());
             //org.apache.http.cookie.Cookie tmpC=new  org.apache.http.cookie.Cookie("name","value");
//            javax.servlet.http.Cookie tmpC = new javax.servlet.http.Cookie(c.getName(), c.getValue());
//            tmpC.setPath(c.getPath());
//            httpServletResponse.addCookie(tmpC);
        }

        return cookies;

    }
}
