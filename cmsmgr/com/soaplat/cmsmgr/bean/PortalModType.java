package com.soaplat.cmsmgr.bean;
// default package



/**
 * PortalModType entity. @author MyEclipse Persistence Tools
 */

public class PortalModType  implements java.io.Serializable {


    // Fields    

     private String idFiletype;
     private String typename;
     private String postfix;
     private String modefilepath;


    // Constructors

    /** default constructor */
    public PortalModType() {
    }

	/** minimal constructor */
    public PortalModType(String idFiletype) {
        this.idFiletype = idFiletype;
    }
    
    /** full constructor */
    public PortalModType(String idFiletype, String typename, String postfix, String modefilepath) {
        this.idFiletype = idFiletype;
        this.typename = typename;
        this.postfix = postfix;
        this.modefilepath = modefilepath;
    }

   
    // Property accessors

    public String getIdFiletype() {
        return this.idFiletype;
    }
    
    public void setIdFiletype(String idFiletype) {
        this.idFiletype = idFiletype;
    }

    public String getTypename() {
        return this.typename;
    }
    
    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getPostfix() {
        return this.postfix;
    }
    
    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }

    public String getModefilepath() {
        return this.modefilepath;
    }
    
    public void setModefilepath(String modefilepath) {
        this.modefilepath = modefilepath;
    }
   








}