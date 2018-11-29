/**
 * Copyright (C) 2015 FZJT-IOT
 *
 *
 * @className:com.fzjt.xiao6.manager.util.GetUUID
 * @description:
 * 
 * @version:v1.0.0 
 * @author:hanxiaotao
 * 
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2015-11-12     hanxiaotao       v1.0.0        create
 *
 *
 */
package com.lawschool.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class GetUUID
{
	public static String[] chars = new String[]
	{ "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
			"w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G",
			"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };



	/**
	 * @Author MengyuWu
	 * @Description 获取随机数
	 * @Date 15:12 2018-11-29
	 * @Param [count]
	 * @return java.lang.String
	 **/
	
	public static String getRom(int count)
	{
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < count; i++)
		{
			String str = uuid.substring(i * 2, i * 2 + 2);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();
	}


	/**
	 * @Author MengyuWu
	 * @Description 生成唯一标识
	 * @Date 15:12 2018-11-29
	 * @Param [type]
	 * @return java.lang.String
	 **/
	public static String getUUIDs(String type)
	{
		return type + getTimestamp();
	}


	/**
	 * @Author MengyuWu
	 * @Description 获取有效的时间戳  生成规则：年月日时分秒+6位随机数
	 * @Date 15:14 2018-11-29
	 * @Param []
	 * @return java.lang.String
	 **/
	
	public static String getTimestamp()
	{
		Random jjj = new Random();
		Date currentTime = new Date();

		String randomNum = "";

		// 获取当前时间
		SimpleDateFormat formatter = new SimpleDateFormat("YYYYMMddHHmmss");
		String dateString = formatter.format(currentTime);

		// 取6位随机数
		for (int k = 0; k < 6; k++)
		{
			randomNum = randomNum + jjj.nextInt(9);
		}

		return dateString + randomNum;
	}
}
