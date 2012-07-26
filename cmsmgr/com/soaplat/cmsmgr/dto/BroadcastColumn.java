package com.soaplat.cmsmgr.dto;

import java.util.ArrayList;
import java.util.List;

public class BroadcastColumn {

	String defCatCode = null;
	String name = null;
	String type = null;
	String siteCode = null;
	
	List<BroadcastProgPackage> broadcastProgPackages = new ArrayList<BroadcastProgPackage>();

	public String getDefCatCode() {
		return defCatCode;
	}

	public void setDefCatCode(String defCatCode) {
		this.defCatCode = defCatCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<BroadcastProgPackage> getBroadcastProgPackages() {
		return broadcastProgPackages;
	}

	public void setBroadcastProgPackages(
			List<BroadcastProgPackage> broadcastProgPackages) {
		this.broadcastProgPackages = broadcastProgPackages;
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}
	
	
	
	
}
