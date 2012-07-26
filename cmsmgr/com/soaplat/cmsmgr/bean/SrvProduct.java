package com.soaplat.cmsmgr.bean;
// default package

import java.util.Date;


/**
 * SrvProduct entity. @author MyEclipse Persistence Tools
 */

public class SrvProduct  implements java.io.Serializable {


    // Fields    

     private String srvprogid;
     private String srvid;
     private Long productCategoryId;
     private Long selecttag;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public SrvProduct() {
    }

	/** minimal constructor */
    public SrvProduct(String srvprogid) {
        this.srvprogid = srvprogid;
    }
    
    /** full constructor */
    public SrvProduct(String srvprogid, String srvid, Long productCategoryId, Long default_, String inputmanid, Date inputtime, String remark) {
        this.srvprogid = srvprogid;
        this.srvid = srvid;
        this.productCategoryId = productCategoryId;
//        this.default_ = default_;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    public String getSrvprogid() {
        return this.srvprogid;
    }
    
    public void setSrvprogid(String srvprogid) {
        this.srvprogid = srvprogid;
    }

    public String getSrvid() {
        return this.srvid;
    }
    
    public void setSrvid(String srvid) {
        this.srvid = srvid;
    }

    public Long getProductCategoryId() {
        return this.productCategoryId;
    }
    
    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public Long getSelecttag() {
		return selecttag;
	}

	public void setSelecttag(Long selecttag) {
		this.selecttag = selecttag;
	}

	public String getInputmanid() {
        return this.inputmanid;
    }
    
    public void setInputmanid(String inputmanid) {
        this.inputmanid = inputmanid;
    }

    public Date getInputtime() {
        return this.inputtime;
    }
    
    public void setInputtime(Date inputtime) {
        this.inputtime = inputtime;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
   








}