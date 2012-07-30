/**
 * Copyright © 2012, Bunco All Rights Reserved.
 * Project: spring-flex
 * cn.sh.sbl.cms.listener.ServerConfigListener.java
 * Create By: Bunco
 * Create Date: 2012-5-4 上午7:37:46
 */
package cn.sh.sbl.cms.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.ServletContextResource;

/**
 * @author Bunco 
 * @E-mail: bunco.hb@gmail.com
 * @version 1.0 
 * @date 2012-5-4 上午7:37:46
 * @description 容器启动时自动初始化flex调用后台所需的endpoint
 */
public class ServerConfigListener implements ServletContextListener {
	private static Logger logger = LoggerFactory.getLogger("Cms");
//	private final String TEMPLATE_FILE = "classpath:config.vm";
//	private final String FLEX_REMOTE_CONFIG_FILE = "/flex/Config.xml";
	private final String FLEX_REMOTE_CONFIG_FILE = "/bin/com/sbl/vod/flexsrc/assets/remote/config.xml";
	private final String MESSAGE_BROKER_ENDPOINT = "http://%1$s:%2$s%3$s/messagebroker/amf";
	private final String FLEX_REMOTE_CONFIG_FILE_ENCODING = "UTF-8";
	public static ServletContext servletContext;
	public static String REAL_PATH = null;
	public static String TEMP_PATH = null;

	public void contextDestroyed(ServletContextEvent event) {
		logger.debug("ServerConfigListener.contextDestroyed");
	}

	public void contextInitialized(ServletContextEvent event) {
		servletContext = event.getServletContext();
		String hostAddress = null;
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
			if (hostAddress.startsWith("127.0.")) {
				hostAddress = this.getNetworkInterfaceIpAddress();
			}
		} catch (UnknownHostException e) {
			logger.debug("{}", e);
			hostAddress = "localhost";
		} catch (NullPointerException e) {
			logger.debug("{}", e);
			hostAddress = "localhost";
		}
		String contextPath = servletContext.getContextPath();
		String serverInfo = servletContext.getServerInfo();
		String servletContextName = servletContext.getServletContextName();
		String serverPort = getHttpPort("HTTP/1.1", "http");
		REAL_PATH = servletContext.getRealPath("/");
		TEMP_PATH = REAL_PATH.substring(0, REAL_PATH.indexOf(contextPath)) + 
				File.separator + "upload" + File.separator + "temp" + File.separator;
		logger.debug("ContextPath={{}}, ServerInfo={{}}, ServletContextName={{}}, ServerPort={{}}, RealPath={{}} TempPath={{}}", 
				new Object[] {contextPath, serverInfo, servletContextName, serverPort, REAL_PATH, TEMP_PATH});
		
		String endpointValue = String.format(this.MESSAGE_BROKER_ENDPOINT, 
				hostAddress, serverPort, "/".equals(contextPath) ? "" : contextPath);
		
		Resource configResource = new ServletContextResource(servletContext, FLEX_REMOTE_CONFIG_FILE);
		SAXReader reader =  new SAXReader();   
		Document doc;
		try {
			doc = reader.read(configResource.getFile());
			Node appNameNode = doc.selectSingleNode("/root/appname");
			Node endpointNode = doc.selectSingleNode("/root/endpoint");
			Node fileFromNode = doc.selectSingleNode("/root/fileFrom");
			logger.debug("selectSingleNode(/root/appname): Name={{}}, Text={{}}, Parent={{}}, StringValue={{}}", 
					new Object[] {appNameNode.getName(), appNameNode.getText(),
					appNameNode.getParent(), appNameNode.getStringValue()});
			logger.debug("selectSingleNode(/root/endpoint): Name={{}}, Text={{}}, Parent={{}}, StringValue={{}}", 
					new Object[] {endpointNode.getName(), endpointNode.getText(),
					endpointNode.getParent(), endpointNode.getStringValue()});
			logger.debug("selectSingleNode(/root/fileFrom): Name={{}}, Text={{}}, Parent={{}}, StringValue={{}}", 
					new Object[] {fileFromNode.getName(), fileFromNode.getText(),
					fileFromNode.getParent(), fileFromNode.getStringValue()});
			appNameNode.setText(servletContextName);
			endpointNode.setText(endpointValue);
			fileFromNode.setText(TEMP_PATH);
			logger.debug("{}", doc.asXML());
			OutputFormat outputFormat = OutputFormat.createPrettyPrint();
			outputFormat.setEncoding(FLEX_REMOTE_CONFIG_FILE_ENCODING);
			XMLWriter xmlWriter = new XMLWriter(
					new FileWriter(configResource.getFile()), outputFormat);
			xmlWriter.write(doc);
			xmlWriter.flush();
			xmlWriter.close();
		} catch (DocumentException e) {
			logger.error("Document 解析错误: ", e);
		} catch (IOException e) {
			logger.error("Document 读写错误: ", e);
		}
	}
	
	private static String getHttpPort(String protocol, String scheme){
		MBeanServer mBeanServer = null;
		if (MBeanServerFactory.findMBeanServer(null).size() > 0) {
			mBeanServer = (MBeanServer) MBeanServerFactory
					.findMBeanServer(null).get(0);
		}

		Set<ObjectName> names = null;
		try {
			names = mBeanServer.queryNames(new ObjectName(
					"Catalina:type=Connector,*"), null);
		} catch (Exception e) {
			logger.debug("{}", e);
		}

		Iterator<ObjectName> it = names.iterator();
		ObjectName oname = null;
		while (it.hasNext()) {
			oname = (ObjectName) it.next();
			String pvalue;
			String svalue;
			try {
				pvalue = (String) mBeanServer
						.getAttribute(oname, "protocol");
				svalue = (String) mBeanServer.getAttribute(oname, "scheme");
				logger.debug("find protocol: {{}}, scheme: {{}}", pvalue, svalue);
				if (protocol.equals(pvalue) && scheme.equals(svalue)) {
					return mBeanServer.getAttribute(oname, "port").toString();
				}
			} catch (AttributeNotFoundException e) {
				logger.debug("{}", e);
				e.printStackTrace();
			} catch (InstanceNotFoundException e) {
				logger.debug("{}", e);
			} catch (MBeanException e) {
				logger.debug("{}", e);
			} catch (ReflectionException e) {
				logger.debug("{}", e);
			}
		}

		return null;
	}
	
	private String getNetworkInterfaceIpAddress() {
		Enumeration<NetworkInterface> netInterfaces = null;  
	    try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			logger.debug("{}", e);
		}  
	    while (netInterfaces.hasMoreElements()) {  
	        NetworkInterface ni = netInterfaces.nextElement();
	        logger.debug("DisplayName: {}", ni.getDisplayName());  
	        logger.debug("Name: {}", ni.getName());  
	        if ("eth0".equalsIgnoreCase(ni.getName())) {
	        	Enumeration<InetAddress> ips = ni.getInetAddresses();
		        while (ips.hasMoreElements()) {
		        	String ipAddress = ips.nextElement().getHostAddress();
		        	logger.debug("IP: {}", ipAddress);
		        	if (-1 != ipAddress.indexOf('.')) {
						return ipAddress;
					}
		        } 
			}
	        throw new NullPointerException("no search interface \"eth0\"!");
	    }  
		throw new NullPointerException("interface \"eth0\" no search IPv4 address!");
	}
}
