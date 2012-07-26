
package com.soaplat.cmsmgr.EncryptService.localhost;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
 *         &lt;element name="AddTaskResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "addTaskResult",
    "errMsg"
})
@XmlRootElement(name = "AddTaskResponse")
public class AddTaskResponse {

    @XmlElement(name = "AddTaskResult")
    protected int addTaskResult;
    protected String errMsg;

    /**
     * Gets the value of the addTaskResult property.
     * 
     */
    public int getAddTaskResult() {
        return addTaskResult;
    }

    /**
     * Sets the value of the addTaskResult property.
     * 
     */
    public void setAddTaskResult(int value) {
        this.addTaskResult = value;
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
