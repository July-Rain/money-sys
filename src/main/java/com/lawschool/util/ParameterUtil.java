package com.lawschool.util;

public class ParameterUtil {
	
	
	public static boolean isNull(String s){
		if("".equals(s) || s == null){
			return true;
		}
		return false;
	}
	
	public static String stringEscape(String s){
		if(!ParameterUtil.isNull(s)){
			char[] c = s.toCharArray();
			StringBuffer str = new StringBuffer();
			for(int i = 0; i < c.length; i++){
				str.append("/"+c[i]);
			}
			return str.toString();
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(stringEscape("admin"));
	}

}
