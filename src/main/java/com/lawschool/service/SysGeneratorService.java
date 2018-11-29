package com.lawschool.service;

import com.lawschool.dao.SysGeneratorDao;
import com.lawschool.util.GeneratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;


@Service
public class SysGeneratorService {
	@Autowired
	private SysGeneratorDao sysGeneratorDao;

	public List<Map<String, Object>> queryList(Map<String, Object> map) {
		return sysGeneratorDao.queryList(map);
	}

	public int queryTotal(Map<String, Object> map) {
		return sysGeneratorDao.queryTotal(map);
	}

	public Map<String, String> queryTable(String tableName) {
		return sysGeneratorDao.queryTable(tableName);
	}

	public List<Map<String, String>> queryColumns(String tableName) {
		return sysGeneratorDao.queryColumns(tableName);
	}

	public byte[] generatorCode(String[] tableNames) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);

//		List<String> templates = new ArrayList<String>();
//		templates.add("vm/Entity.java.vm");
//		templates.add("vm/Dao.java.vm");
//		templates.add("vm/Dao.xml.vm");
//		templates.add("vm/Service.java.vm");
//		templates.add("vm/ServiceImpl.java.vm");
//		templates.add("vm/Controller.java.vm");
//		templates.add("vm/list.html.vm");
//		templates.add("vm/list.js.vm");
//		templates.add("vm/menu.sql.vm");
//		GeneratorUtils.setTemplates(templates);

		for(String tableName : tableNames){
			//查询表信息
			Map<String, String> table = queryTable(tableName);
			//查询列信息
			List<Map<String, String>> columns = queryColumns(tableName);
			//生成代码
			GeneratorUtils.generatorCode(table, columns, zip);
		}
		zip.close();
		return outputStream.toByteArray();
	}
}
