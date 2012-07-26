package com.soaplat.cmsmgr.EncryptService.localhost;

import javax.xml.ws.Holder;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class testWebService {

	public static void main(String[] args) {
 		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		 factory.setAddress("http://127.0.0.1:8000/TaskManager.asmx");
		 factory.setServiceClass(TaskManagerSoap.class);
		 TaskManagerSoap taskManager = (TaskManagerSoap)factory.create();
		 taskManager.getAllTask(new Holder<Integer>(1), new Holder<Integer>(1), new Holder<ArrayOfUnsignedInt>());
	}
}
