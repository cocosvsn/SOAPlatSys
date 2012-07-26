package com.soaplat.cmsmgr.dto;

import java.util.ArrayList;
import java.util.List;

public class BroadcastProglist {

	String scheduleDate = null;
	
	List<BroadcastColumn> broadcastColumns = new ArrayList<BroadcastColumn>();

	public String getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public List<BroadcastColumn> getBroadcastColumns() {
		return broadcastColumns;
	}

	public void setBroadcastColumns(List<BroadcastColumn> broadcastColumns) {
		this.broadcastColumns = broadcastColumns;
	}
	
	
}
