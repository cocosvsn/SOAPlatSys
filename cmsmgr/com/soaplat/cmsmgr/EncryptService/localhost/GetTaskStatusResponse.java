
package com.soaplat.cmsmgr.EncryptService.localhost;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GetTaskStatusResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="addTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prio" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="scIp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="errMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getTaskStatusResult",
    "addTime",
    "prio",
    "state",
    "scIp",
    "errMsg"
})
@XmlRootElement(name = "GetTaskStatusResponse")
public class GetTaskStatusResponse {

    @XmlElement(name = "GetTaskStatusResult")
    protected int getTaskStatusResult;
    protected String addTime;
    @XmlSchemaType(name = "unsignedByte")
    protected short prio;
    protected int state;
    protected String scIp;
    protected String errMsg;

    /**
     * Gets the value of the getTaskStatusResult property.
     * 
     */
    public int getGetTaskStatusResult() {
        return getTaskStatusResult;
    }

    /**
     * Sets the value of the getTaskStatusResult property.
     * 
     */
    public void setGetTaskStatusResult(int value) {
        this.getTaskStatusResult = value;
    }

    /**
     * Gets the value of the addTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddTime() {
        return addTime;
    }

    /**
     * Sets the value of the addTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddTime(String value) {
        this.addTime = value;
    }

    /**
     * Gets the value of the prio property.
     * 
     */
    public short getPrio() {
        return prio;
    }

    /**
     * Sets the value of the prio property.
     * 
     */
    public void setPrio(short value) {
        this.prio = value;
    }

    /**
     * Gets the value of the state property.
     * 
     */
    public int getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     */
    public void setState(int value) {
        this.state = value;
    }

    /**
     * Gets the value of the scIp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScIp() {
        return scIp;
    }

    /**
     * Sets the value of the scIp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScIp(String value) {
        this.scIp = value;
    }

    /**
     * Gets the value of the errMsg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrMsg() {
        return errMsg;
    }

    /**
     * Sets the value of the errMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrMsg(String value) {
        this.errMsg = value;
    }

}
