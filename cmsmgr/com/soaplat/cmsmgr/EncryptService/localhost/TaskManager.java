
/*
 * 
 */

package com.soaplat.cmsmgr.EncryptService.localhost;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.2.3
 * Mon Jan 11 17:39:05 CST 2010
 * Generated source version: 2.2.3
 * 
 */


@WebServiceClient(name = "TaskManager", 
                  wsdlLocation = "http://127.0.0.1:8000/TaskManager.asmx?WSDL",
                  targetNamespace = "http://localhost/") 
public class TaskManager extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://localhost/", "TaskManager");
    public final static QName TaskManagerSoap = new QName("http://localhost/", "TaskManagerSoap");
    public final static QName TaskManagerSoap12 = new QName("http://localhost/", "TaskManagerSoap12");
    static {
        URL url = null;
        try {
            url = new URL("http://127.0.0.1:8000/TaskManager.asmx?WSDL");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from http://127.0.0.1:8000/TaskManager.asmx?WSDL");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public TaskManager(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public TaskManager(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public TaskManager() {
        super(WSDL_LOCATION, SERVICE);
    }

    /**
     * 
     * @return
     *     returns TaskManagerSoap
     */
    @WebEndpoint(name = "TaskManagerSoap")
    public TaskManagerSoap getTaskManagerSoap() {
        return super.getPort(TaskManagerSoap, TaskManagerSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns TaskManagerSoap
     */
    @WebEndpoint(name = "TaskManagerSoap")
    public TaskManagerSoap getTaskManagerSoap(WebServiceFeature... features) {
        return super.getPort(TaskManagerSoap, TaskManagerSoap.class, features);
    }
    /**
     * 
     * @return
     *     returns TaskManagerSoap
     */
    @WebEndpoint(name = "TaskManagerSoap12")
    public TaskManagerSoap getTaskManagerSoap12() {
        return super.getPort(TaskManagerSoap12, TaskManagerSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns TaskManagerSoap
     */
    @WebEndpoint(name = "TaskManagerSoap12")
    public TaskManagerSoap getTaskManagerSoap12(WebServiceFeature... features) {
        return super.getPort(TaskManagerSoap12, TaskManagerSoap.class, features);
    }

}
