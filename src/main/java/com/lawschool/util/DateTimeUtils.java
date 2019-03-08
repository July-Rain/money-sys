package com.lawschool.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateTimeUtils {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 根据提供的年月日获取该月份的第一天, 如：2018-02-01 00:00:00
     * @param year
     * @param monthOfYear
     * @return
     */
    public static String getSupportBeginDayofMonth(int year, int monthOfYear) {
        Calendar cal = Calendar.getInstance();
        // 不加下面2行，就是取当前时间前一个月的第一天及最后一天
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDate = cal.getTime();
        return dateFormat.format(firstDate);
    }

    /**
     * 根据提供的年月获取该月份的最后一天，如：2018-02-28 23:59:59
     * @param year
     * @param monthOfYear
     * @return
     */
    public static String getSupportEndDayofMonth(int year, int monthOfYear) {
        Calendar cal = Calendar.getInstance();
        // 不加下面2行，就是取当前时间前一个月的第一天及最后一天
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);

        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = cal.getTime();

        return dateFormat.format(lastDate);
    }

    /**
     * 获取本周的第一天日期
     * 设置周一为一周第一天
     * @return 格式为yyyy-MM-dd
     */
    public static String getFirstDayOfWeek(){
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        return sdf.format(cal.getTime());
    }

    /**
     * 获取一周的日期
     * @return
     */
    public static List<String> getWeekDays(){
        List<String> result = new ArrayList<String>(7);

        // 周一日期
        String firstDayOfWeek = getFirstDayOfWeek();
        result.add(firstDayOfWeek);

        try{
            Date first = sdf.parse(firstDayOfWeek);
            Calendar cal = Calendar.getInstance();
            cal.setTime(first);

            int i=1;
            while(i < 7){

                cal.add(Calendar.DAY_OF_WEEK, 1);
                i++;
                result.add(sdf.format(cal.getTime()));
            }
        } catch (Exception e){

            result = new ArrayList<String>(7);
        }

        return result;
    }

    /**
     * 获取当天为一周第几天
     * @return
     */
    public static Integer getDayOfWeek(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.setFirstDayOfWeek(Calendar.MONDAY);

        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取今日日期，格式yyyy-MM-dd
     * @return
     */
    public static String getToday(){
        Date today = new Date();
        String current = sdf.format(today);

        return current;
    }

    /**
     * 比较日期大小
     * @param param1 格式yyyy-MM-dd
     * @param param2 格式yyyy-MM-dd
     * @return 负数param1<param2, 0 param1=param2， 正数param1>param2
     */
    public static int compare(String param1, String param2){
        int result = 0;

        try{
            Date day1 = sdf.parse(param1);
            Date day2 = sdf.parse(param2);
            result = day1.compareTo(day2);
        } catch (Exception e) {
            result = 1;
        }

        return result;
    }

    /**
     * 当前季度的开始时间
     *
     * @return
     */
    public static Date getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3){
                c.set(Calendar.MONTH, 0);
            }

            else if (currentMonth >= 4 && currentMonth <= 6){
                c.set(Calendar.MONTH, 3);
            }
            else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 4);
            }
            else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 9);
            }
            c.set(Calendar.DATE, 1);
            now = dateFormat.parse(sdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前季度的结束时间
     *
     * @return
     */
    public static Date getCurrentQuarterEndTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 2);
                c.set(Calendar.DATE, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 8);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            now = dateFormat.parse(sdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获得本月的开始时间
     *
     * @return
     */
    public static Date getCurrentMonthStartTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.DATE, 1);
            now = dateFormat.parse(sdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 本月的结束时间
     *
     * @return
     */
    public static Date getCurrentMonthEndTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.DATE, 1);
            c.add(Calendar.MONTH, 1);
            c.add(Calendar.DATE, -1);
            now = dateFormat.parse(sdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

}
