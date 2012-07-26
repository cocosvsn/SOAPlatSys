package com.srmmgr.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.2.3
 * Fri Nov 20 13:40:50 CST 2009
 * Generated source version: 2.2.3
 * 
 */
 
@WebService(targetNamespace = "http://webservice.srmmgr.com/", name = "IStbCustomerWS")
@XmlSeeAlso({ObjectFactory.class})
public interface IStbCustomerWS {

    @ResponseWrapper(localName = "updateStbCustomerResponse", targetNamespace = "http://webservice.srmmgr.com/", className = "com.srmmgr.webservice.UpdateStbCustomerResponse")
    @RequestWrapper(localName = "updateStbCustomer", targetNamespace = "http://webservice.srmmgr.com/", className = "com.srmmgr.webservice.UpdateStbCustomer")
    @WebResult(name = "return", targetNamespace = "")
    @WebMethod
    public int updateStbCustomer(
        @WebParam(name = "arg0", targetNamespace = "")
        java.util.List<com.srmmgr.webservice.StbCustomer> arg0
    );

    @ResponseWrapper(localName = "getAllStbCustomerResponse", targetNamespace = "http://webservice.srmmgr.com/", className = "com.srmmgr.webservice.GetAllStbCustomerResponse")
    @RequestWrapper(localName = "getAllStbCustomer", targetNamespace = "http://webservice.srmmgr.com/", className = "com.srmmgr.webservice.GetAllStbCustomer")
    @WebResult(name = "return", targetNamespace = "")
    @WebMethod
    public java.util.List<java.lang.Object> getAllStbCustomer();

    @ResponseWrapper(localName = "deleteStbCustomerResponse", targetNamespace = "http://webservice.srmmgr.com/", className = "com.srmmgr.webservice.DeleteStbCustomerResponse")
    @RequestWrapper(localName = "deleteStbCustomer", targetNamespace = "http://webservice.srmmgr.com/", className = "com.srmmgr.webservice.DeleteStbCustomer")
    @WebResult(name = "return", targetNamespace = "")
    @WebMethod
    public int deleteStbCustomer(
        @WebParam(name = "arg0", targetNamespace = "")
        java.util.List<com.srmmgr.webservice.StbCustomer> arg0
    );

    @ResponseWrapper(localName = "saveStbCustomerResponse", targetNamespace = "http://webservice.srmmgr.com/", className = "com.srmmgr.webservice.SaveStbCustomerResponse")
    @RequestWrapper(localName = "saveStbCustomer", targetNamespace = "http://webservice.srmmgr.com/", className = "com.srmmgr.webservice.SaveStbCustomer")
    @WebResult(name = "return", targetNamespace = "")
    @WebMethod
    public int saveStbCustomer(
        @WebParam(name = "arg0", targetNamespace = "")
        java.util.List<com.srmmgr.webservice.StbCustomer> arg0
    );
}
