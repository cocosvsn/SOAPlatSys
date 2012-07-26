package com.soaplat.cmsmgr.ConfigService;

import java.util.ArrayList;
import java.util.List;

import com.soaplat.cmsmgr.bean.PackStyle;

public interface ISpackStyleService {

	/***
	 * 
	 * @param packStyle
	 * @param array
	 * @return  
	 *   list
	 */
	public List addPackStyleAll(PackStyle packStyle, ArrayList array);
	
    public List delPackStyleAll(String pkid);
    
    public List searchPackStyleAll();
    
    public List updatePackStyleAll(PackStyle packStyle, ArrayList array);
    
//
}
