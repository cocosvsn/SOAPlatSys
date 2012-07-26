package com.soaplat.cmsmgr.bean;
// default package



/**
 * ProgType entity. @author MyEclipse Persistence Tools
 */

public class ProgType  implements java.io.Serializable {


    // Fields    

     private String progtypeid;
     private String classname;
     private Long property;


    // Constructors

    /** default constructor */
    public ProgType() {
    }

	/** minimal constructor */
    public ProgType(String progtypeid) {
        this.progtypeid = progtypeid;
    }
    
    /** full constructor */
    public ProgType(String progtypeid, String classname, Long property) {
        this.progtypeid = progtypeid;
        this.classname = classname;
        this.property = property;
    }

   
    // Property accessors

    public String getProgtypeid() {
        return this.progtypeid;
    }
    
    public void setProgtypeid(String progtypeid) {
        this.progtypeid = progtypeid;
    }

    public String getClassname() {
        return this.classname;
    }
    
    public void setClassname(String classname) {
        this.classname = classname;
    }

    public Long getProperty() {
        return this.property;
    }
    
    public void setProperty(Long property) {
        this.property = property;
    }
   








}