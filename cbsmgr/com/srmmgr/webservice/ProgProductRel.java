
package com.srmmgr.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for progProductRel complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="progProductRel">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fglobalid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="filename" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ftypeglobalid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="inputmanid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="inputtime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="pglobalid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="productlist" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="relid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uploadtime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "progProductRel", propOrder = {
    "fglobalid",
    "filename",
    "ftypeglobalid",
    "inputmanid",
    "inputtime",
    "pglobalid",
    "productlist",
    "relid",
    "remark",
    "uploadtime"
})
public class ProgProductRel {

    protected String fglobalid;
    protected String filename;
    protected String ftypeglobalid;
    protected String inputmanid;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar inputtime;
    protected String pglobalid;		// progid
    protected String productlist;	// productid
    protected String relid;			// id
    protected String remark;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar uploadtime;

    /**
     * Gets the value of the fglobalid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFglobalid() {
        return fglobalid;
    }

    /**
     * Sets the value of the fglobalid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFglobalid(String value) {
        this.fglobalid = value;
    }

    /**
     * Gets the value of the filename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Sets the value of the filename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilename(String value) {
        this.filename = value;
    }

    /**
     * Gets the value of the ftypeglobalid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFtypeglobalid() {
        return ftypeglobalid;
    }

    /**
     * Sets the value of the ftypeglobalid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFtypeglobalid(String value) {
        this.ftypeglobalid = value;
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
     * Gets the value of the pglobalid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPglobalid() {
        return pglobalid;
    }

    /**
     * Sets the value of the pglobalid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPglobalid(String value) {
        this.pglobalid = value;
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
     * Gets the value of the relid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelid() {
        return relid;
    }

    /**
     * Sets the value of the relid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelid(String value) {
        this.relid = value;
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
     * Gets the value of the uploadtime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUploadtime() {
        return uploadtime;
    }

    /**
     * Sets the value of the uploadtime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUploadtime(XMLGregorianCalendar value) {
        this.uploadtime = value;
    }

}
