/**
* copyright (c) by A-one 2010
*/
package com.soaplat.cmsmgr.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author：Bunco
 * E-mail: cocosvsn@yahoo.com.cn
 * @date：Aug 12, 2010 10:21:37 AM
 * @version: 1.0
 * description: 利用dom4j解析xml文档, 并递归调用解析生成json对象.
 */
public class Xml2Json {
	private static final String rootObject = "'{'{0}'}'";
	private static final String childObject2 = "\"{0}\":\"{1}\"";
	private static final String childObject = "\"{0}\":{1}";
	private static final String childArray = "\"{0}\":[{1}]";
	private String json;
	
	/**
	 * Class文件同级目录下的xml文件名
	 * @param fileName 文件名包括路径, 可以为相对路径或绝对路径
	 * @throws FileNotFoundException 
	 * @throws DocumentException
	 */
	public Xml2Json(String fileName) throws FileNotFoundException, DocumentException {
		InputStream in = Xml2Json.class.getResourceAsStream(fileName);
		if(null == in) {
			in = new FileInputStream(fileName);
		}
		Document document = new SAXReader().read(in);
		this.json = this.getChild(document.getRootElement())
				.replaceAll("\\\\", "\\\\\\\\");
	}
	
	public Xml2Json(InputStream in) throws DocumentException {
		Document document = new SAXReader().read(in);
		this.json = this.getChild(document.getRootElement())
				.replaceAll("\\\\", "\\\\\\\\");
	}
	
	public Xml2Json(Document document) {
		this.json = this.getChild(document.getRootElement())
				.replaceAll("\\\\", "\\\\\\\\");
	}
	
	@SuppressWarnings("unchecked")
	private String getChild(Element element) {
		String tmp = "";
		if (0 < element.attributes().size()) {
			for (Attribute attribute : (List<Attribute>) element.attributes()) {
				tmp += getChildAttrs(attribute) + ",";
			}
		}
		
		if (0 < element.elements().size()) {
			List<Element> elements = element.elements();
			Set<String> elementNameSet = new HashSet<String>(0);
			for (Element e : elements) {
				elementNameSet.add(e.getName());
			}
			
			for (String string : elementNameSet) {
				List<Element> elementList = element.elements(string);
//				if (1 < elementList.size()) {
				String tmpArr = "";
				for (Element element2 : elementList) {
					tmpArr += getChild(element2) + ",";
				}
				tmp += MessageFormat.format(childArray, elementList.get(0).getName(), tmpArr.substring(0, tmpArr.length() - 1)) + ","; 
//				} else {
//					tmp += MessageFormat.format(childObject, elementList.get(0).getName(), getChild(elementList.get(0)) + ",");
//				}
			}
			return MessageFormat.format(rootObject, tmp.substring(0, tmp.length() - 1));
		} else if(0 < element.attributes().size()) {
			tmp = "".equals(element.getText().trim()) ? tmp.substring(0, tmp.length() - 1)
					: tmp + MessageFormat.format(
							childObject2, element.getName(), element.getText().trim());
			return MessageFormat.format(rootObject, tmp);
		} else {
			return tmp + "\"" + element.getText().trim() + "\"";
		}
	}

	private String getChildAttrs(Attribute attribute) {
		return MessageFormat.format(
				childObject2, attribute.getName(), attribute.getValue());
	}
	
	public String getJson() {
		return this.json;
	}

	/**
	 * @param args
	 * @throws DocumentException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) {
		Xml2Json xml2Json;
		try {
			xml2Json = new Xml2Json("c:/加扰.xml");
			System.out.println(xml2Json.getJson());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
