package com.soaplat.cmsmgr.dto;

import java.util.ArrayList;
import java.util.List;

public class BroadcastPushVod {

	String id = null;
	
	List<BroadcastSite> broadcastSites = new ArrayList<BroadcastSite>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<BroadcastSite> getBroadcastSites() {
		return broadcastSites;
	}

	public void setBroadcastSites(List<BroadcastSite> broadcastSites) {
		this.broadcastSites = broadcastSites;
	}

	
	
}
