
package com.soaplat.cmsmgr.EncryptService.localhost;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="taskId" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="taskPrio" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
 *         &lt;element name="taskContent" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
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
    "taskId",
    "taskPrio",
    "taskContent"
})
@XmlRootElement(name = "AddTask")
public class AddTask {

    @XmlSchemaType(name = "unsignedInt")
    protected long taskId;
    @XmlSchemaType(name = "unsignedByte")
    protected short taskPrio;
    protected byte[] taskContent;

    /**
     * Gets the value of the taskId property.
     * 
     */
    public long getTaskId() {
        return taskId;
    }

    /**
     * Sets the value of the taskId property.
     * 
     */
    public void setTaskId(long value) {
        this.taskId = value;
    }

    /**
     * Gets the value of the taskPrio property.
     * 
     */
    public short getTaskPrio() {
        return taskPrio;
    }

    /**
     * Sets the value of the taskPrio property.
     * 
     */
    public void setTaskPrio(short value) {
        this.taskPrio = value;
    }

    /**
     * Gets the value of the taskContent property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getTaskContent() {
        return taskContent;
    }

    /**
     * Sets the value of the taskContent property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setTaskContent(byte[] value) {
        this.taskContent = ((byte[]) value);
    }

}
