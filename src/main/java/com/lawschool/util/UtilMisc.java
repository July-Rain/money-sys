package com.lawschool.util;

import com.alibaba.fastjson.JSONReader;
import com.lawschool.base.AbstractController;
import org.apache.commons.io.IOUtils;
import org.springframework.util.ResourceUtils;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 常用工具
 *
 * @author LiCheng
 * @data 2018.3.12
 */
@SuppressWarnings("all")
public class UtilMisc extends AbstractController {

    public static List<Map<String, String>> jsonToList(String json){
    JSONReader reader = new JSONReader(new StringReader(json));//已流的方式处理，这里很快
        reader.startArray();
        List<Map<String, String>> rsList = new ArrayList<Map<String, String>>();
        Map<String, String> map = null;
        int i = 0;
        while (reader.hasNext()) {
            i++;
            reader.startObject();//这边反序列化也是极速
            map = new HashMap<String, String>();
            while (reader.hasNext()) {
                String arrayListItemKey = reader.readString();
                String arrayListItemValue = reader.readObject().toString();
                map.put(arrayListItemKey, arrayListItemValue);
            }
            rsList.add(map);
            reader.endObject();
        }
        reader.endArray();
        return rsList;
    }
    /**
     * 获取现在时间
     *
     * @return返回字符串格式yyyyMMddHHmmss
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }



    //list去重
    public   static   List  removeDuplicate(List list)  {
        for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )  {
            for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )  {
                if  (list.get(j).equals(list.get(i)))  {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    public static Date getDate() {
        Date day = new Date();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            day = df.parse(df.format(day));
        } catch (ParseException e) {

        }

        return day;
    }
    public static Date getDateByHHMMSS() {
        Date day = new Date();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            day = df.parse(df.format(day));
        } catch (ParseException e) {

        }

        return day;
    }
    public static int getMonth() {
        Calendar cale = null;
        cale = Calendar.getInstance();
        int month = cale.get(Calendar.MONTH) + 1;
        return month;
    }

    public static String getYear() {
        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        return String.valueOf(year);
    }

    /**
     * 抽取元数据 查询字典数据获取value
     *
     * @param object
     * @param codeJavaFiledName code所在的字段名 java的Set方法名 首字母小写
     */


    /**
     * 　　* @Description: 获取时间戳--暂时用于生成编号
     * 　　* @param
     * 　　* @return
     * 　　* @throws
     * 　　* @author MengyuWu
     * 　　* @date 2018/3/26 20:32
     */
    public static String getTimestamp() {
        Random jjj = new Random();
        Date currentTime = new Date();

        String randomNum = "";

        // 获取当前时间
        SimpleDateFormat formatter = new SimpleDateFormat("YYYYMMddHHmmss");
        String dateString = formatter.format(currentTime);

        // 取6位随机数
        for (int k = 0; k < 6; k++) {
            randomNum = randomNum + jjj.nextInt(9);
        }

        return dateString + randomNum;
    }


    /**
     * 　　* @Description: 上传的PDF转成图片
     * 　　* @param
     * 　　* @return
     * 　　* @throws
     * 　　* @author MengyuWu
     * 　　* @date 2018/4/13 16:26
     */

    /**
     * 　　* @Description: 把byte数组转成相应的文件
     * 　　* @param
     * 　　* @return
     * 　　* @throws
     * 　　* @author MengyuWu
     * 　　* @date 2018/4/13 16:32
     */
    public File getFileFromBytes(byte[] b) {
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:demo-images.pdf");
            IOUtils.toByteArray(new FileInputStream(file));
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * service中获取当前登录用户信息的方法
     *
     * @return
     */


//往前推2天
    public static String getLastDayOfMonthTwo(int year,int month)
    {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //当前月份最后一天减两天
        cal.set(Calendar.DAY_OF_MONTH, lastDay-2);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());

        return lastDayOfMonth;
    }

    //
    public static String getCurrDayTwo()
    {
        Calendar cal = Calendar.getInstance();
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置当前时间+2
        cal.set(Calendar.DAY_OF_MONTH+2, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currDayTwo = sdf.format(cal.getTime());

        return currDayTwo;
    }

    public static String getCurrDayTwoMonthOne ()
    {


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);// 先退 月份减一  然后在 减去 日期 2天
        calendar.add(Calendar.DATE, - 2);  //天数退2天
        Date m = calendar.getTime();
        String riqi = format.format(m);
        return riqi;
    }

//当前时间往后2天
    public static String getnowdatetwoday ()
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,  2);  //天数前2天
        Date m = calendar.getTime();
        String riqi = format.format(m);
        return riqi;
    }

    //当前时间往后2天
    public static String getnowdatetwoday2 ()
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,  2);  //天数前2天
        Date m = calendar.getTime();
        String riqi = format.format(m);
        return riqi;
    }




    public static String createData(int length) {
        StringBuilder sb=new StringBuilder();
        Random rand=new Random();
        for(int i=0;i<length;i++)
        {
            sb.append(rand.nextInt(10));
        }
        String data=sb.toString();

        return data;
    }
}
