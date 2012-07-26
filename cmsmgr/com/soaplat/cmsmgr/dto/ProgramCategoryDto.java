package com.soaplat.cmsmgr.dto;

import java.util.List;

public class ProgramCategoryDto {

	private String dicGlobalId;
	private String dictCode;
	private String dictName;
	
	private List cmsServices;

	
	
	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public List getCmsServices() {
		return cmsServices;
	}

	public void setCmsServices(List cmsServices) {
		this.cmsServices = cmsServices;
	}

	public String getDicGlobalId() {
		return dicGlobalId;
	}

	public void setDicGlobalId(String dicGlobalId) {
		this.dicGlobalId = dicGlobalId;
	}
}
