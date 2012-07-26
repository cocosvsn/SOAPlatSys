package com.soaplat.cmsmgr.dto;

import java.util.ArrayList;
import java.util.List;

public class BroadcastSite {

	String broadcastTime = null;
	String siteCode = null;
	String siteName = null;
	String scheduleDate = null;
	
	List<BroadcastProglist> broadcastProglists = new ArrayList<BroadcastProglist>();
//	List<BroadcastJs> broadcastJses = new ArrayList<BroadcastJs>();
//	List<BroadcastDelete> broadcastDeletes = new ArrayList<BroadcastDelete>();

	BroadcastJs broadcastJs = new BroadcastJs();
	BroadcastDelete broadcastDelete = new BroadcastDelete();
	
	public String getBroadcastTime() {
		return broadcastTime;
	}

	public void setBroadcastTime(String broadcastTime) {
		this.broadcastTime = broadcastTime;
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public List<BroadcastProglist> getBroadcastProglists() {
		return broadcastProglists;
	}

	public void setBroadcastProglists(List<BroadcastProglist> broadcastProglists) {
		this.broadcastProglists = broadcastProglists;
	}

	public BroadcastJs getBroadcastJs() {
		return broadcastJs;
	}

	public void setBroadcastJs(BroadcastJs broadcastJs) {
		this.broadcastJs = broadcastJs;
	}

	public BroadcastDelete getBroadcastDelete() {
		return broadcastDelete;
	}

	public void setBroadcastDelete(BroadcastDelete broadcastDelete) {
		this.broadcastDelete = broadcastDelete;
	}

//	public List<BroadcastJs> getBroadcastJses() {
//		return broadcastJses;
//	}
//
//	public void setBroadcastJses(List<BroadcastJs> broadcastJses) {
//		this.broadcastJses = broadcastJses;
//	}
//
//	public List<BroadcastDelete> getBroadcastDeletes() {
//		return broadcastDeletes;
//	}
//
//	public void setBroadcastDeletes(List<BroadcastDelete> broadcastDeletes) {
//		this.broadcastDeletes = broadcastDeletes;
//	}
	
	
	
}
