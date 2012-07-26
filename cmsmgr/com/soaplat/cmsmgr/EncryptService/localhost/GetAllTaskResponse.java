
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
 *         &lt;element name="GetAllTaskResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="nNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="taskIds" type="{http://localhost/}ArrayOfUnsignedInt" minOccurs="0"/>
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
    "getAllTaskResult",
    "nNumber",
    "taskIds"
})
@XmlRootElement(name = "GetAllTaskResponse")
public class GetAllTaskResponse {

    @XmlElement(name = "GetAllTaskResult")
    protected int getAllTaskResult;
    protected int nNumber;
    protected ArrayOfUnsignedInt taskIds;

    /**
     * Gets the value of the getAllTaskResult property.
     * 
     */
    public int getGetAllTaskResult() {
        return getAllTaskResult;
    }

    /**
     * Sets the value of the getAllTaskResult property.
     * 
     */
    public void setGetAllTaskResult(int value) {
        this.getAllTaskResult = value;
    }

    /**
     * Gets the value of the nNumber property.
     * 
     */
    public int getNNumber() {
        return nNumber;
    }

    /**
     * Sets the value of the nNumber property.
     * 
     */
    public void setNNumber(int value) {
        this.nNumber = value;
    }

    /**
     * Gets the value of the taskIds property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfUnsignedInt }
     *     
     */
    public ArrayOfUnsignedInt getTaskIds() {
        return taskIds;
    }

    /**
     * Sets the value of the taskIds property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfUnsignedInt }
     *     
     */
    public void setTaskIds(ArrayOfUnsignedInt value) {
        this.taskIds = value;
    }

}
