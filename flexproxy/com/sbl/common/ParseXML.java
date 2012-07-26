package com.sbl.common;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParseXML {
	private static Logger logger = LoggerFactory.getLogger(ParseXML.class);
	private static final ParseXML instance = new ParseXML();

	private Document document;

	private ParseXML() {
		try {
			SAXReader reader = new SAXReader();
			this.document = reader.read(Thread.currentThread()
					.getContextClassLoader().getResource("ref-config.xml"));
		} catch (DocumentException e) {
			logger.warn("{}", e);
		}
	}

	public static ParseXML getInstance() {
		return instance;
	}

	@SuppressWarnings("unchecked")
	public RefVo getRef(String strName) {
		List<Element> list = this.document
				.selectNodes("//ref-method[ref-name='" + strName + "']/*");
		RefVo refVo = new RefVo();
		Iterator<Element> it = list.iterator();
		while (it.hasNext()) {
			Element elm = it.next();
			if ("ref-name".equals(elm.getName())) {
				refVo.setName(elm.getText());
			} else if ("ref-param".equals(elm.getName())) {
				Class<?>[] cls;
				List<Element> params = elm.elements();
				cls = new Class[params.size()];
				for (int i = 0; i < params.size(); i++) {
					if ("true".equalsIgnoreCase(params.get(i).attributeValue(
							"array"))) {
						try {
							cls[i] = Array.newInstance(
									Class.forName(params.get(i).attributeValue(
											"type")), 1).getClass();
						} catch (NegativeArraySizeException e) {
							logger.warn("{}", e);
						} catch (ClassNotFoundException e) {
							logger.warn("{}", e);
						}
					} else {
						try {
							cls[i] = Class.forName(params.get(i)
									.attributeValue("type"));
						} catch (ClassNotFoundException e) {
							logger.warn("{}", e);
						}
					}
				}
				refVo.setCls(cls);
			}
		}
		if (list.size() > 0) {
			Element parent = list.get(0).getParent();
			refVo.setMethod(parent.attributeValue("name"));
			parent = parent.getParent();
			refVo.setTheClass(parent.attributeValue("class"));
			refVo.setSpringRel(parent.attributeValue("spring-ref"));
		}
		return refVo;
	}

	public static void main(String[] args) throws Exception {
//		RefVo tt = ParseXML.getInstance().getRef("Login");
//		 System.out.println(tt);
//		 System.out.println();
//		 System.out.println(Array.newInstance(Class.forName("java.lang.Integer"),
//		 1).getClass());
//		 System.out.println(Integer[].class);
//		 System.out.println(Integer.class);
//		 System.out.println(tt.getSpringRel());
//		 Assert.isNull(tt.getSpringRel());
	}
}
