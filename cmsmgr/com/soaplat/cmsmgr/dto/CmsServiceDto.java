package com.soaplat.cmsmgr.dto;

import java.util.Date;

import com.soaplat.cmsmgr.bean.CmsService;

public class CmsServiceDto {

	// CmsService
    private String srvid;
    private String srvname;
    private String defcatcode;
    private String isleaf;
    private Long defcatlevel;
    private String rootid;
    private String parentsid;
    private Long displayorder;
    private String defcatseq;
    private String inputmanid;
    private Date inputtime;
    private String remark;
    
    private String srvNameOrder;
    
    
    public CmsServiceDto()
    {
    	
    }
    
    public CmsServiceDto(CmsService cmsService)
    {
    	this.srvid = cmsService.getSrvid();
    	this.srvname = cmsService.getSrvname();
    	this.defcatcode = cmsService.getDefcatcode();
    	this.isleaf = cmsService.getIsleaf();
    	this.defcatlevel = cmsService.getDefcatlevel();
    	this.rootid = cmsService.getRootid();
    	this.parentsid = cmsService.getParentsid();
    	this.displayorder = cmsService.getDisplayorder();
    	this.defcatseq = cmsService.getDefcatseq();
    	this.inputmanid = cmsService.getInputmanid();
    	this.inputtime = cmsService.getInputtime();
    	this.remark = cmsService.getRemark();
    	
    	this.srvNameOrder = cmsService.getSrvname() + "[" + cmsService.getDisplayorder().toString() + "]";
    }
    
	public String getSrvid() {
		return srvid;
	}

	public void setSrvid(String srvid) {
		this.srvid = srvid;
	}

	public String getSrvname() {
		return srvname;
	}

	public void setSrvname(String srvname) {
		this.srvname = srvname;
	}

	public String getDefcatcode() {
		return defcatcode;
	}

	public void setDefcatcode(String defcatcode) {
		this.defcatcode = defcatcode;
	}

	public String getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}

	public Long getDefcatlevel() {
		return defcatlevel;
	}

	public void setDefcatlevel(Long defcatlevel) {
		this.defcatlevel = defcatlevel;
	}

	public String getRootid() {
		return rootid;
	}

	public void setRootid(String rootid) {
		this.rootid = rootid;
	}

	public String getParentsid() {
		return parentsid;
	}

	public void setParentsid(String parentsid) {
		this.parentsid = parentsid;
	}

	public Long getDisplayorder() {
		return displayorder;
	}

	public void setDisplayorder(Long displayorder) {
		this.displayorder = displayorder;
	}

	public String getDefcatseq() {
		return defcatseq;
	}

	public void setDefcatseq(String defcatseq) {
		this.defcatseq = defcatseq;
	}

	public String getInputmanid() {
		return inputmanid;
	}

	public void setInputmanid(String inputmanid) {
		this.inputmanid = inputmanid;
	}

	public Date getInputtime() {
		return inputtime;
	}

	public void setInputtime(Date inputtime) {
		this.inputtime = inputtime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSrvNameOrder() {
		return srvNameOrder;
	}

	public void setSrvNameOrder(String srvNameOrder) {
		this.srvNameOrder = srvNameOrder;
	}
}
