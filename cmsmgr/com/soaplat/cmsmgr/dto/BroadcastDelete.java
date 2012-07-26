package com.soaplat.cmsmgr.dto;

import java.util.ArrayList;
import java.util.List;

public class BroadcastDelete {


	List<BroadcastProgPackage> broadcastProgPackages = new ArrayList<BroadcastProgPackage>();

	public List<BroadcastProgPackage> getBroadcastProgPackages() {
		return broadcastProgPackages;
	}

	public void setBroadcastProgPackages(
			List<BroadcastProgPackage> broadcastProgPackages) {
		this.broadcastProgPackages = broadcastProgPackages;
	}
}
