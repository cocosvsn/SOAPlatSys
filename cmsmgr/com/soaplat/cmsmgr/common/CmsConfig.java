package com.soaplat.cmsmgr.common;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import org.apache.log4j.Logger;


public class CmsConfig {
	
	private static String fileName = "./CmsConfig.properties";
	private Properties p = new Properties();
	private static final Logger cmsLog = Logger.getLogger("Cms");

	public CmsConfig() {
		InputStream in = CmsConfig.class.getResourceAsStream(fileName);
		try {
			p.load(in);
			in.close();
		} catch (IOException e) {
			cmsLog.error("CmsConfig配置文件读取异常：" + e.getMessage());
			cmsLog.info("CmsConfig配置文件：" + fileName);
			e.printStackTrace();
		}
	}
	
	public String getPropertyByName(String name) {
		String property = "";
		if (p.containsKey(name)) {
			property = p.getProperty(name);
		} else {
			cmsLog.warn("CmsConfig配置文件读取不存在的字段，字段名：" + name);
		}
		return property;
	}
	
	public static void main(String[] args) {
		CmsConfig cmsConfig = new CmsConfig();
		String value = cmsConfig.getPropertyByName("paperJS");
		value = MessageFormat.format(value, "20100909", "20100909", "20100909", "20100909");
		System.out.println(value);
	}
}
