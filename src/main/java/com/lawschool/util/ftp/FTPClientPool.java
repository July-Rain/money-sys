package com.lawschool.util.ftp;

import java.util.Properties;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.core.io.Resource;

public class FTPClientPool {
	private GenericObjectPool<FTPClient> ftpClientPool;

	private Resource configLocation;

	public Resource getConfigLocation() {
		return configLocation;
	}

	public void setConfigLocation(Resource configLocation) {

		this.configLocation = configLocation;
		Properties pro = new Properties();
		try {
			pro.load(configLocation.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

		// 初始化对象池配置
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setBlockWhenExhausted(Boolean.parseBoolean(pro.getProperty("ftpClient_blockWhenExhausted")));
		poolConfig.setMaxWaitMillis(Long.parseLong(pro.getProperty("ftpClient_maxWait")));
		poolConfig.setMinIdle(Integer.parseInt(pro.getProperty("ftpClient_minIdle")));
		poolConfig.setMaxIdle(Integer.parseInt(pro.getProperty("ftpClient_maxIdle")));
		poolConfig.setMaxTotal(Integer.parseInt(pro.getProperty("ftpClient_maxTotal")));
		poolConfig.setTestOnBorrow(Boolean.parseBoolean(pro.getProperty("ftpClient_testOnBorrow")));
		poolConfig.setTestOnReturn(Boolean.parseBoolean(pro.getProperty("ftpClient_testOnReturn")));
		poolConfig.setTestOnCreate(Boolean.parseBoolean(pro.getProperty("ftpClient_testOnCreate")));
		poolConfig.setTestWhileIdle(Boolean.parseBoolean(pro.getProperty("ftpClient_testWhileIdle")));
		poolConfig.setLifo(Boolean.parseBoolean(pro.getProperty("ftpClient_lifo")));

		FTPConfig ftpConfig = new FTPConfig();
		ftpConfig.setHost(pro.getProperty("ftpClient_host"));
		ftpConfig.setPort(Integer.parseInt(pro.getProperty("ftpClient_port")));
		ftpConfig.setUsername(pro.getProperty("ftpClient_username"));
		ftpConfig.setPassword(pro.getProperty("ftpClient_pasword"));
		ftpConfig.setClientTimeout(Integer.parseInt(pro.getProperty("ftpClient_clientTimeout")));
		ftpConfig.setEncoding(pro.getProperty("ftpClient_encoding"));
		ftpConfig.setWorkingDirectory(pro.getProperty("ftpClient_workingDirectory"));
		ftpConfig.setPassiveMode(Boolean.parseBoolean(pro.getProperty("ftpClient_passiveMode")));
		ftpConfig.setRenameUploaded(Boolean.parseBoolean(pro.getProperty("ftpClient_renameUploaded")));
		ftpConfig.setRetryTimes(Integer.parseInt(pro.getProperty("ftpClient_retryTimes")));
		ftpConfig.setTransferFileType(Integer.parseInt(pro.getProperty("ftpClient_transferFileType")));
		ftpConfig.setBufferSize(Integer.parseInt(pro.getProperty("ftpClient_bufferSize")));
		// 初始化对象池
		ftpClientPool = new GenericObjectPool<FTPClient>(new FTPClientFactory(ftpConfig), poolConfig);


	}
	public FTPClientPool() {
	}

	public GenericObjectPool<FTPClient> getFtpClientPool() {
		return ftpClientPool;
	}

	/*public void setFtpClientPool(GenericObjectPool<FTPClient> ftpClientPool) {
		Properties pro = new Properties();
		try {
			pro.load(configLocation.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		// 初始化对象池配置
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setBlockWhenExhausted(Boolean.parseBoolean(pro.getProperty("ftpClient_blockWhenExhausted")));
		poolConfig.setMaxWaitMillis(Long.parseLong(pro.getProperty("ftpClient_maxWait")));
		poolConfig.setMinIdle(Integer.parseInt(pro.getProperty("ftpClient_minIdle")));
		poolConfig.setMaxIdle(Integer.parseInt(pro.getProperty("ftpClient_maxIdle")));
		poolConfig.setMaxTotal(Integer.parseInt(pro.getProperty("ftpClient_maxTotal")));
		poolConfig.setTestOnBorrow(Boolean.parseBoolean(pro.getProperty("ftpClient_testOnBorrow")));
		poolConfig.setTestOnReturn(Boolean.parseBoolean(pro.getProperty("ftpClient_testOnReturn")));
		poolConfig.setTestOnCreate(Boolean.parseBoolean(pro.getProperty("ftpClient_testOnCreate")));
		poolConfig.setTestWhileIdle(Boolean.parseBoolean(pro.getProperty("ftpClient_testWhileIdle")));
		poolConfig.setLifo(Boolean.parseBoolean(pro.getProperty("ftpClient_lifo")));

		FTPConfig ftpConfig = new FTPConfig();
		ftpConfig.setHost(pro.getProperty("ftpClient_host"));
		ftpConfig.setPort(Integer.parseInt(pro.getProperty("ftpClient_port")));
		ftpConfig.setUsername(pro.getProperty("ftpClient_username"));
		ftpConfig.setPassword(pro.getProperty("ftpClient_pasword"));
		ftpConfig.setClientTimeout(Integer.parseInt(pro.getProperty("ftpClient_clientTimeout")));
		ftpConfig.setEncoding(pro.getProperty("ftpClient_encoding"));
		ftpConfig.setWorkingDirectory(pro.getProperty("ftpClient_workingDirectory"));
		ftpConfig.setPassiveMode(Boolean.parseBoolean(pro.getProperty("ftpClient_passiveMode")));
		ftpConfig.setRenameUploaded(Boolean.parseBoolean(pro.getProperty("ftpClient_renameUploaded")));
		ftpConfig.setRetryTimes(Integer.parseInt(pro.getProperty("ftpClient_retryTimes")));
		ftpConfig.setTransferFileType(Integer.parseInt(pro.getProperty("ftpClient_transferFileType")));
		ftpConfig.setBufferSize(Integer.parseInt(pro.getProperty("ftpClient_bufferSize")));
		// 初始化对象池
		this.ftpClientPool = new GenericObjectPool<FTPClient>(new FTPClientFactory(ftpConfig), poolConfig);
	}
*/
	public FTPClientPool(Resource configLocation) {


	}

	public FTPClient borrowObject() throws Exception {
		return ftpClientPool.borrowObject();
	}

	public void returnObject(FTPClient ftpClient) {
		ftpClientPool.returnObject(ftpClient);
	}
}
