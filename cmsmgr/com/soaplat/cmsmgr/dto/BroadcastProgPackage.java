package com.soaplat.cmsmgr.dto;

import java.util.ArrayList;
import java.util.List;

public class BroadcastProgPackage {

	String ppid = null;
	String stbTitle = null;
	String keyids = null;

	
	List<BroadcastFile> broadcastFiles = new ArrayList<BroadcastFile>();
	List<BroadcastPushCondition> broadcastPushConditions = new ArrayList<BroadcastPushCondition>();
	
	
	public String getPpid() {
		return ppid;
	}
	public void setPpid(String ppid) {
		this.ppid = ppid;
	}
	public String getStbTitle() {
		return stbTitle;
	}
	public void setStbTitle(String stbTitle) {
		this.stbTitle = stbTitle;
	}
	public String getKeyids() {
		return keyids;
	}
	public void setKeyids(String keyids) {
		this.keyids = keyids;
	}
	public List<BroadcastFile> getBroadcastFiles() {
		return broadcastFiles;
	}
	public void setBroadcastFiles(List<BroadcastFile> broadcastFiles) {
		this.broadcastFiles = broadcastFiles;
	}
	public List<BroadcastPushCondition> getBroadcastPushConditions() {
		return broadcastPushConditions;
	}
	public void setBroadcastPushConditions(
			List<BroadcastPushCondition> broadcastPushConditions) {
		this.broadcastPushConditions = broadcastPushConditions;
	}

}
