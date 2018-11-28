package com.lawschool.util;


import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import static com.oracle.jrockit.jfr.Transition.To;

public class UtilString {
    static DecimalFormat decimalFormat = new DecimalFormat("0.0000");
    /**
     * 根据参数返回字符串。如参数为："1-3", 则返回"1,2,3"
     * @param monthRangeRule 如："1-3", "2-6"等等; 月份取值区间
     * @return
     */
    public static String getMonthRange(String monthRangeRule) {
        String[] arr = monthRangeRule.split("-");
        if(arr != null && arr.length == 2) {
            int start = Integer.parseInt(arr[0]);
            int end = Integer.parseInt(arr[1]);
            StringBuffer buffer = new StringBuffer();
            for(int i=start; i < end+1; i++) {
                buffer.append(",").append(i);
            }
            return buffer.toString().substring(1);
        }

        return null;
    }

    /**
     * 根据参数返回字符串。如参数为："1-3", 则返回"1,2,3"
     * @param monthRangeRule 如："1-3", "2-6"等等; 月份取值区间
     * @return
     */
    public static List<Integer> getMonthRangeList(String monthRangeRule) {
        List<Integer> intList = new LinkedList<>();
        String[] arr = monthRangeRule.split("-");
        if(arr != null && arr.length == 2) {
            int start = Integer.parseInt(arr[0]);
            int end = Integer.parseInt(arr[1]);
            for(int i=start; i < end+1; i++) {
                intList.add(i);
            }
        }

        return intList;
    }

    public static BigDecimal calcRate(BigDecimal source, BigDecimal target) {
        if(target == null || target.compareTo(BigDecimal.ZERO) < 0 ||  new BigDecimal("0.00").equals(target)) {
            return BigDecimal.ZERO;
        } else if(source  == null) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(decimalFormat.format(source.divide(target,2, BigDecimal.ROUND_HALF_UP)));
    }
    public static BigDecimal calcRate(Integer source, Integer target) {
        if(target == null || source == null) {
            return BigDecimal.ZERO;
        } else if(Integer.MIN_VALUE == source || Integer.MIN_VALUE == target) {
            return BigDecimal.ZERO;
        } else if(target == 0) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(decimalFormat.format(source/target));
    }
    public static BigDecimal calcRateDouble(Integer source, Integer target) {
        if(target == null || source == null) {
            return BigDecimal.ZERO;
        } else if(Integer.MIN_VALUE == source || Integer.MIN_VALUE == target) {
            return BigDecimal.ZERO;
        } else if(target == 0) {
            return BigDecimal.ZERO;
        }
        double range = source*1.0/target;
        return new BigDecimal(range).setScale(2,BigDecimal.ROUND_HALF_UP);
    }

    public static boolean isBooleanTrue(Object bObj) {
        String bStr = String.valueOf(bObj);
        if(bStr != null && bStr.equalsIgnoreCase("true")) {
            return true;
        }
        return false;
    }

    public static String getCommaSplitedCharacters(List<String> strList) {
        StringBuffer buf = new StringBuffer();
        if(strList != null) {
            for(String item: strList) {
                buf.append(",").append(item);
            }
        }
        String result = buf.toString();
        if(!(StringUtils.isEmpty(result)) && result.length()>1) {
            return result.substring(1);
        } else {
            return null;
        }
    }
}
