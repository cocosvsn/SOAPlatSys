package com.soaplat.cmsmgr.bean;
// default package



/**
 * PackStyleFileType entity. @author MyEclipse Persistence Tools
 */

public class PackStyleFileType  implements java.io.Serializable {


    // Fields    

     private Long cmspackStyleFileTypeId;
     private Long styleid;
     private String filetypeid;
     private Long bcstatus;


    // Constructors

    /** default constructor */
    public PackStyleFileType() {
    }

	/** minimal constructor */
    public PackStyleFileType(Long cmspackStyleFileTypeId) {
        this.cmspackStyleFileTypeId = cmspackStyleFileTypeId;
    }
    
    /** full constructor */
    public PackStyleFileType(Long cmspackStyleFileTypeId, Long styleid, String filetypeid, Long bcstatus) {
        this.cmspackStyleFileTypeId = cmspackStyleFileTypeId;
        this.styleid = styleid;
        this.filetypeid = filetypeid;
        this.bcstatus = bcstatus;
    }

   
    // Property accessors

    public Long getCmspackStyleFileTypeId() {
        return this.cmspackStyleFileTypeId;
    }
    
    public void setCmspackStyleFileTypeId(Long cmspackStyleFileTypeId) {
        this.cmspackStyleFileTypeId = cmspackStyleFileTypeId;
    }

    public Long getStyleid() {
        return this.styleid;
    }
    
    public void setStyleid(Long styleid) {
        this.styleid = styleid;
    }

    public String getFiletypeid() {
        return this.filetypeid;
    }
    
    public void setFiletypeid(String filetypeid) {
        this.filetypeid = filetypeid;
    }

    public Long getBcstatus() {
        return this.bcstatus;
    }
    
    public void setBcstatus(Long bcstatus) {
        this.bcstatus = bcstatus;
    }
   








}