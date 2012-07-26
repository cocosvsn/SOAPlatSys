package com.soaplat.cmsmgr.dto;

import java.util.Date;
import java.util.List;

import com.soaplat.sysmgr.bean.FunResource;

public class FunResourceDto {

	private FunResource funResource;
    private String state2;
	private List operators;
	
	
	
	

	public FunResource getFunResource() {
		return funResource;
	}

	public void setFunResource(FunResource funResource) {
		this.funResource = funResource;
	}

	public List getOperators() {
		return operators;
	}

	public void setOperators(List operators) {
		this.operators = operators;
	}

	public String getState2() {
		return state2;
	}

	public void setState2(String state2) {
		this.state2 = state2;
	}
    
}
