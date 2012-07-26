/**
 * copyright (c) by A-one 2010
 */
package com.soaplat.cmsmgr.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

import org.apache.log4j.Logger;

import com.soaplat.cmsmgr.managerimpl.CmsTransactionManagerImpl;

/**
 * Title 		: 配置文件读写工具类
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn, b_huang@sbl.sh.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		：2010-6-21 上午10:46:02
 */
public class PropertyUtil {
	
	/**
	 * 日志
	 */
	private static Logger logger = Logger.getLogger(PropertyUtil.class);
	
	private static Properties properties = new Properties();
	
	/**
	 * 加载配置文件
	 * @param filePath 配置文件路径
	 * @return 返回加载配置文件是否成功
	 */
	private static boolean loadPropertyFile(String filePath) {
		logger.info("加载配置文件!         loadPropertyFile");
		
		try {
			properties.load(PropertyUtil.class.getResourceAsStream(filePath));
			logger.info("加载配置文件成功!");
			return true;
		} catch (FileNotFoundException e) {
			logger.error("加载配置文件失败, 文件没找到: FileInputStream(\"" + filePath
					+ "\")! FileNotFoundException: " + e);
			return false;
		} catch (IOException e) {
			logger.error("加载配置文件失败, 文件读取错误: FileInputStream(\"" + filePath
					+ "\")! IOException: " + e);
			return false;
		}
	}
	
	/**
	 * 读取配置文件内容
	 * @param filePath 配置文件路径
	 * @return 返回配置文件内容所有键值对
	 */
	public static Map<String, String> getProperties(String filePath) {
		logger.info("读取配置文件信息!         getProperties");
		
		Map<String, String> propertiesContainer = new HashMap<String, String>(0);
		if(loadPropertyFile(filePath)) {
			Enumeration<?> enumeration = properties.keys();
			for (; enumeration.hasMoreElements();) {
				String key = (String) enumeration.nextElement();
				propertiesContainer.put(key, properties.getProperty(key));
			}
		}
		return propertiesContainer;
	}
	
	/**
	 * 读取配置文件中Key的值
	 * @param key 属性的键
	 * @param filePath 配置文件路径
	 * @return 返回属性的值
	 */
	public static String getValue(String key, String filePath) {
		logger.info("读取配置文件信息, 根据key值来获取配置文件的value值!           getValue");
		
		if(loadPropertyFile(filePath))
			return properties.getProperty(key);
		return null;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String filePath = "./viewCategory.properties";
		Map<String, String> map = getProperties(filePath);
		List<String> list = new ArrayList<String>(map.keySet());
		Collections.sort(list);
		Collections.reverse(list);
		for (Object string : list) {
			System.out.println(string + ": " + map.get(string));
		}
		
//		File file = new File("c:\\log\\Log_Cms_20100621.log");
//		if (file.exists()) {
//			System.out.println(file.delete());
//		}
		
//		try {
//			SmbFile smbFile = new SmbFile("");
//			smbFile.delete();
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (SmbException e) {
//			e.printStackTrace();
//		}
		
//		String tmpString = "维果·莫特森 马尔科姆·麦克道威尔 祖雷克哈·罗宾逊          岳跃利  白珊  李强"; //"[ \\s\\u00A0]+$岳跃利  白珊  李强"; //"岳跃利？？白珊？？李强";
//		tmpString = tmpString.replaceAll("\u00A0", "\u0020");
//		int len = tmpString.length();
//		char[] tmp = tmpString.toCharArray();
//		for (int i = 0; i < len; ++ i) {
//			System.out.println((int) tmp[i]);
//		}
		
//		CmsTransactionManagerImpl cmsTransactionManagerImpl = new CmsTransactionManagerImpl();
		String path = "c:\\PPVP20100625141459000421.xml";
//		List<?> list = cmsTransactionManagerImpl.getProgPackageFilesByXml(path);
//		for (Object object : list) {
//			System.out.println(object);
//		}
		
//		String strxml = "";
//		File file = new File(path);
//		byte[] bytes = new byte[(int)(file.length())];
//		FileInputStream fileInputStream;
//		try {
//			fileInputStream = new FileInputStream(file);
//			while ((fileInputStream.read(bytes)) != -1) 
//			{
//				String str = new String(bytes, "UTF-8");
//				strxml += str;
//			}
//			String tmpString = strxml.substring(71, 96);
//			int len = tmpString.length();
//			char[] tmp = tmpString.toCharArray();
//			for (int i = 0; i < len; ++ i) {
//				System.out.println((int) tmp[i]);
//			}
//			System.out.println(tmp);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
