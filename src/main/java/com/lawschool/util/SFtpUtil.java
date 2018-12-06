package com.lawschool.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.omg.CORBA.SystemException;
import org.springframework.stereotype.Component;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

@Component
public class SFtpUtil {

	private static Logger log = Logger.getLogger(SFtpUtil.class);

	/**
	 * Description: 向FTP服务器上传文件
	 * 
	 * @param basePath FTP服务器基础目录
	 * @param filePath FTP服务器文件存放路径。例如分日期存放：/2015/01/01。文件的路径为basePath+filePath
	 * @param filename 上传到FTP服务器上的文件名
	 * @param input    输入流
	 * @return 成功返回true，否则返回false
	 */
	public static boolean uploadFile(String basePath, String filePath, String filename, InputStream input) {
		boolean result = false;

		Map<String, String> sftpDetails = new HashMap<String, String>();
		// 设置主机ip，端口，用户名，密码
//		sftpDetails.put(SFTPConstants.SFTP_REQ_HOST, "10.254.250.193");
//		sftpDetails.put(SFTPConstants.SFTP_REQ_USERNAME, "root");
//		sftpDetails.put(SFTPConstants.SFTP_REQ_PASSWORD, "qwe123!@#");
//		sftpDetails.put(SFTPConstants.SFTP_REQ_PORT, "22");

		sftpDetails.put(SFTPConstants.SFTP_REQ_HOST, "10.254.250.174");
		sftpDetails.put(SFTPConstants.SFTP_REQ_USERNAME, "root");
		sftpDetails.put(SFTPConstants.SFTP_REQ_PASSWORD, "123456");
		sftpDetails.put(SFTPConstants.SFTP_REQ_PORT, "22");
		
		// 判断目标目录是否存在如若不存在则创建
		String dst = basePath + filePath; // 目标文件名

		SFTPChannel channel = SFtpUtil.getSFTPChannel();
		try {
			ChannelSftp chSftp = channel.getChannel(sftpDetails, 60000);
			//创建目录
			createDir(dst,chSftp);
			dst = dst + "/" + filename;
			chSftp.put(input, dst, ChannelSftp.OVERWRITE); // 代码段2
			chSftp.quit();
			channel.closeChannel();
			log.info("上传成功");
			result = true;
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static boolean createDir(String fullPath, ChannelSftp sftp) throws SystemException {
		
		boolean result = false;
		
		if (!isDirExist(fullPath,sftp)) {
			// 如果目录不存在创建目录
			String[] dirs = fullPath.split("/");
			String tempPath = "";
			for (String dir : dirs) {
				if (null == dir || "".equals(dir))
					continue;
				tempPath += "/" + dir;
				if (!isDirExist(tempPath,sftp)) {
					
					try {
						sftp.mkdir(tempPath);
					} catch (SftpException e) {
						log.info("创建失败");
					}
				}
			}
		}
		
		return result;
	}
	
	
	/**
	  * 判断目录是否存在
	 * 
	 * @param directory
	 * @param sftp
	 * @return
	 */
	public static boolean isDirExist(String directory, ChannelSftp sftp) {
		boolean isDirExistFlag = false;

		try {
			SftpATTRS sftpATTRS = sftp.lstat(directory);
			isDirExistFlag = true;
			return sftpATTRS.isDir();
		} catch (SftpException e) {
			if (e.getMessage().toLowerCase().equals("no such file")) {
				isDirExistFlag = false;
			}
		}

		return isDirExistFlag;
	}

	
	
	public static SFTPChannel getSFTPChannel() {
		return new SFTPChannel();
	}
}
