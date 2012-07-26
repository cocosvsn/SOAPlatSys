
package com.srmmgr.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for stbCustomer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="stbCustomer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="creditinfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cstcrttime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="cstdsttime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="cstfree" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="customerid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customerstatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customertype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="districtid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="inputmanid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="inputtime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="paymode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="productlist" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pwd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="smartcardid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="smscstid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="stbid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="stbip" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="stbmac" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "stbCustomer", propOrder = {
    "creditinfo",
    "cstcrttime",
    "cstdsttime",
    "cstfree",
    "customerid",
    "customerstatus",
    "customertype",
    "districtid",
    "inputmanid",
    "inputtime",
    "paymode",
    "productlist",
    "pwd",
    "remark",
    "smartcardid",
    "smscstid",
    "stbid",
    "stbip",
    "stbmac",
    "userid"
})
public class StbCustomer {

    protected String creditinfo;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar cstcrttime;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar cstdsttime;
    protected Double cstfree;
    protected String customerid;
    protected String customerstatus;
    protected String customertype;
    protected String districtid;
    protected String inputmanid;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar inputtime;
    protected String paymode;
    protected String productlist;
    protected String pwd;
    protected String remark;
    protected String smartcardid;
    protected String smscstid;
    protected String stbid;
    protected String stbip;
    protected String stbmac;
    protected String userid;

    /**
     * Gets the value of the creditinfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditinfo() {
        return creditinfo;
    }

    /**
     * Sets the value of the creditinfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditinfo(String value) {
        this.creditinfo = value;
    }

    /**
     * Gets the value of the cstcrttime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCstcrttime() {
        return cstcrttime;
    }

    /**
     * Sets the value of the cstcrttime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCstcrttime(XMLGregorianCalendar value) {
        this.cstcrttime = value;
    }

    /**
     * Gets the value of the cstdsttime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCstdsttime() {
        return cstdsttime;
    }

    /**
     * Sets the value of the cstdsttime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCstdsttime(XMLGregorianCalendar value) {
        this.cstdsttime = value;
    }

    /**
     * Gets the value of the cstfree property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getCstfree() {
        return cstfree;
    }

    /**
     * Sets the value of the cstfree property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setCstfree(Double value) {
        this.cstfree = value;
    }

    /**
     * Gets the value of the customerid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerid() {
        return customerid;
    }

    /**
     * Sets the value of the customerid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerid(String value) {
        this.customerid = value;
    }

    /**
     * Gets the value of the customerstatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerstatus() {
        return customerstatus;
    }

    /**
     * Sets the value of the customerstatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerstatus(String value) {
        this.customerstatus = value;
    }

    /**
     * Gets the value of the customertype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomertype() {
        return customertype;
    }

    /**
     * Sets the value of the customertype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomertype(String value) {
        this.customertype = value;
    }

    /**
     * Gets the value of the districtid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistrictid() {
        return districtid;
    }

    /**
     * Sets the value of the districtid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistrictid(String value) {
        this.districtid = value;
    }

    /**
     * Gets the value of the inputmanid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInputmanid() {
        return inputmanid;
    }

    /**
     * Sets the value of the inputmanid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInputmanid(String value) {
        this.inputmanid = value;
    }

    /**
     * Gets the value of the inputtime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getInputtime() {
        return inputtime;
    }

    /**
     * Sets the value of the inputtime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInputtime(XMLGregorianCalendar value) {
        this.inputtime = value;
    }

    /**
     * Gets the value of the paymode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymode() {
        return paymode;
    }

    /**
     * Sets the value of the paymode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymode(String value) {
        this.paymode = value;
    }

    /**
     * Gets the value of the productlist property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductlist() {
        return productlist;
    }

    /**
     * Sets the value of the productlist property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductlist(String value) {
        this.productlist = value;
    }

    /**
     * Gets the value of the pwd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * Sets the value of the pwd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPwd(String value) {
        this.pwd = value;
    }

    /**
     * Gets the value of the remark property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Sets the value of the remark property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemark(String value) {
        this.remark = value;
    }

    /**
     * Gets the value of the smartcardid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmartcardid() {
        return smartcardid;
    }

    /**
     * Sets the value of the smartcardid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmartcardid(String value) {
        this.smartcardid = value;
    }

    /**
     * Gets the value of the smscstid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmscstid() {
        return smscstid;
    }

    /**
     * Sets the value of the smscstid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmscstid(String value) {
        this.smscstid = value;
    }

    /**
     * Gets the value of the stbid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStbid() {
        return stbid;
    }

    /**
     * Sets the value of the stbid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStbid(String value) {
        this.stbid = value;
    }

    /**
     * Gets the value of the stbip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStbip() {
        return stbip;
    }

    /**
     * Sets the value of the stbip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStbip(String value) {
        this.stbip = value;
    }

    /**
     * Gets the value of the stbmac property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStbmac() {
        return stbmac;
    }

    /**
     * Sets the value of the stbmac property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStbmac(String value) {
        this.stbmac = value;
    }

    /**
     * Gets the value of the userid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserid() {
        return userid;
    }

    /**
     * Sets the value of the userid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserid(String value) {
        this.userid = value;
    }

}
