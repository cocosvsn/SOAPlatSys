package com.soaplat.cmsmgr.dto;

import java.util.ArrayList;
import java.util.List;

public class TransferTask {

//	public String proglistId = null;
//	public String requestId = null;
//	public String createDate = null;
//	public String type = null;
	
	public List<TransferEntity> transferEntities = new ArrayList<TransferEntity>();

	
	
	
	
	public List<TransferEntity> getTransferEntities() {
		return transferEntities;
	}

	public void setTransferEntities(List<TransferEntity> transferEntities) {
		this.transferEntities = transferEntities;
	}


	
	
}
