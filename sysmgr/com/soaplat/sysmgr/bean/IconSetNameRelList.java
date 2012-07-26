package com.soaplat.sysmgr.bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Title 		:the Class IconSetNameRelList.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class IconSetNameRelList {
	
	/** The iconsetname. */
	private String iconsetname;
	
	/** The iconsetnamelist. */
	public List iconsetnamelist = new ArrayList();

	/**
	 * Gets the iconsetname.
	 * 
	 * @return the iconsetname
	 */
	public String getIconsetname() {
		return iconsetname;
	}

	/**
	 * Sets the iconsetname.
	 * 
	 * @param iconsetname the new iconsetname
	 */
	public void setIconsetname(String iconsetname) {
		this.iconsetname = iconsetname;
	}

	public List getIconsetnamelist() {
		return iconsetnamelist;
	}

	public void setIconsetnamelist(List iconsetnamelist) {
		this.iconsetnamelist = iconsetnamelist;
	}

	
}
