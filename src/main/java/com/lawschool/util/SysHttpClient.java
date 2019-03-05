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
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;


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

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
}
