package com.lawschool.util.ftp;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;


/*@Configuration
public class FTPClientPoolConfiguration {

	public FTPClientPool getFTPClientPool() {
		Resource resource =new UrlResource(FTPClientPoolConfiguration.class.getClassLoader().getResource("properties/ftp-config.properties"));
		return new FTPClientPool(resource);
	}
}*/
