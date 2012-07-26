/**
* copyright (c) by A-one 2010
*/
package com.soaplat.cmsmgr.util;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.Node;
import org.dom4j.dom.DOMElement;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * 封装生成XML文档内容的通用类
 * @author：Bunco
 * E-mail: cocosvsn@yahoo.com.cn
 * @date：2010-8-24 上午09:52:24
 * @description 节点序列用类似于XPath的表示方法, <br />
 * 以//开头表示根节点开始, 每个节点可以有属性, <br />
 * 属性紧接着节点名称用[]表示, 多个属性之间用|分隔. <br />
 * 节点下可以有任意多个子节点, 子节点之间用;号隔开. <br />
 * 如果节点下需要有文本节点, 则在节点后面加上~符号.
 *    还有些思路不清晰, 待解决
 */
public class XmlUtil {
	private final static Logger logger = Logger.getLogger(XmlUtil.class);
	private String encoding;			// 生成XML字符的字符编码
	private String nodeSequence;		// 节点序列, 类似于XPath[//root[properties|...]/childNodes[properties|...];...]
	private List<?> values;				// 按照节点序列, 从根节点到叶子节点的顺序, 同级的按先后顺序. 依次传递javaBean对象.
	private String[] nodes;
	private Document document;
	
	/**
	 * 
	 * @param encoding
	 * @param nodeSequence
	 * @param values
	 * @throws Exception 
	 */
	public XmlUtil(String encoding, String nodeSequence, List<?> values) throws Exception {
		this.encoding = encoding;
		this.values = values;
		if(nodeSequence.startsWith("//") && nodeSequence.length() > 2) { 
			this.nodeSequence = nodeSequence;
			this.nodes = nodeSequence.substring(2).split("/");
			logger.debug("根据节点序列获取到节点数为: " + this.nodes.length);
		} else {
			throw new Exception("节点序列错误!");
		}
	}
	
	
	public synchronized String getXml() {
		document = DocumentFactory.getInstance().createDocument(this.encoding);
		document.setRootElement(this.getElement(0).get(0));
		return null;
	}
	
	/**
	 * 根据节点深度, 创建指定深度的元素
	 * @param deep 
	 * @return
	 * @throws Exception 
	 */
	public List<Element> getElement(int deep) {
		List<Element> elements = new ArrayList<Element>();
		String currSeq = this.nodes[deep];
		logger.debug("获取到当前深度为[ " + deep + " ]的节点序列为: " + currSeq);
		String[] currNodes = currSeq.split(";");
		for (String string : currNodes) {
			String nodeName = string.substring(0, string.indexOf("["));
			boolean hasText = nodeName.endsWith("~");
			Element element = new DOMElement(hasText 
					? nodeName.substring(0, nodeName.length() - 1) : nodeName);
			elements.add(element);
		}
		return elements;
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			new XmlUtil("", "", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		try {
//			XMLWriter writer = null; // 声明写XML的对象
//			SAXReader reader = new SAXReader();
//
//			OutputFormat format = OutputFormat.createPrettyPrint();
//			format.setEncoding(" GBK "); // 设置XML文件的编码格式
//
//			String filePath = " d:\\student.xml ";
//			File file = new File(filePath);
//			if (file.exists()) {
//				Document document = reader.read(file); // 读取XML文件
//				Element root = document.getRootElement(); // 得到根节点
//				boolean bl = false;
//				for (Iterator i = root.elementIterator(" 学生 "); i.hasNext();) {
//					Element student = (Element) i.next();
//					if (student.attributeValue(" sid ").equals(" 001 ")) {
//						// 修改学生sid=001的学生信息
//						student.selectSingleNode(" 姓名 ").setText(" 王五 ");
//						student.selectSingleNode(" 年龄 ").setText(" 25 ");
//
//						writer = new XMLWriter(new FileWriter(filePath), format);
//						writer.write(document);
//						writer.close();
//						bl = true;
//						break;
//					}
//				}
//				if (bl) {
//					// 添加一个学生信息
//					Element student = root.addElement(" 学生 ");
//					student.addAttribute(" sid ", " 100 ");
//					Element sid = student.addElement(" 编号 ");
//					sid.setText(" 100 ");
//					Element name = student.addElement(" 姓名 ");
//					name.setText(" 嘎嘎 ");
//					Element sex = student.addElement(" 性别 ");
//					sex.setText(" 男 ");
//					Element age = student.addElement(" 年龄 ");
//					age.setText(" 21 ");
//
//					writer = new XMLWriter(new FileWriter(filePath), format);
//					writer.write(document);
//					writer.close();
//				}
//			} else {
//				// 新建student.xml文件并新增内容
//				Document _document = DocumentHelper.createDocument();
//				Element _root = _document.addElement(" 学生信息 ");
//				Element _student = _root.addElement(" 学生 ");
//				_student.addAttribute(" sid ", " 001 ");
//				Element _id = _student.addElement(" 编号 ");
//				_id.setText(" 001 ");
//				Element _name = _student.addElement(" 姓名 ");
//				_name.setText(" 灰机 ");
//				Element _age = _student.addElement(" 年龄 ");
//				_age.setText(" 18 ");
//
//				writer = new XMLWriter(new FileWriter(file), format);
//				writer.write(_document);
//				writer.close();
//			}
//			System.out.println(" 操作结束!  ");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
