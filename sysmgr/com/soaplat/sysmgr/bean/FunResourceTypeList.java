package com.soaplat.sysmgr.bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Title 		:the Class FunResourceTypeList.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class FunResourceTypeList {
	
	/** The functype. */
	private String functype;
	
	/** The funresourcelist. */
	public List funresourcelist = new ArrayList();
	
	/**
	 * Gets the functype.
	 * 
	 * @return the functype
	 */
	public String getFunctype() {
		return functype;
	}
	
	/**
	 * Sets the functype.
	 * 
	 * @param functype the new functype
	 */
	public void setFunctype(String functype) {
		this.functype = functype;
	}

	public List getFunresourcelist() {
		return funresourcelist;
	}

	public void setFunresourcelist(List funresourcelist) {
		this.funresourcelist = funresourcelist;
	}
	
	
}
