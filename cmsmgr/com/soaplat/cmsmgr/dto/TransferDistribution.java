package com.soaplat.cmsmgr.dto;

import java.util.ArrayList;
import java.util.List;

public class TransferDistribution {

	public String comment = null;		// ×¢ÊÍ
	public String proglistId = null;
	public String requestId = null;
	public String createDate = null;
	public String type = null;
	
	
	public List<TransferTask> transferTasks = new ArrayList<TransferTask>();


	
	
	public List<TransferTask> getTransferTasks() {
		return transferTasks;
	}


	public void setTransferTasks(List<TransferTask> transferTasks) {
		this.transferTasks = transferTasks;
	}


	public String getProglistId() {
		return proglistId;
	}


	public void setProglistId(String proglistId) {
		this.proglistId = proglistId;
	}


	public String getRequestId() {
		return requestId;
	}


	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}


	public String getCreateDate() {
		return createDate;
	}


	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
