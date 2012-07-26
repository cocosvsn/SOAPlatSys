package com.soaplat.sysmgr.bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Title 		:the Class OperatorInfo.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class OperatorInfo {
	
	/** The operator. */
	public Operator operator;
	

	
	/** The menulist. */
	public List menulist = new ArrayList();
	
	/**
	 * Gets the operator.
	 * 
	 * @return the operator
	 */
	public Operator getOperator() {
		return operator;
		
	}
	
	/**
	 * Sets the operator.
	 * 
	 * @param operator the new operator
	 */
	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public List getMenulist() {
		return menulist;
	}

	public void setMenulist(List menulist) {
		this.menulist = menulist;
	}
	
	
}
