package com.soaplat.cmsmgr.ConfigService;

import java.util.Date;
import java.util.List;

import com.soaplat.cmsmgr.bean.PortalMod;
import com.soaplat.cmsmgr.manageriface.IPortalModManager;
import com.soaplat.cmsmgr.manageriface.IStyleRelManager;
import com.soaplat.sysmgr.common.ApplicationContextHolder;

public class PortalModService implements IPortalModService{

	public IPortalModManager portalModManager = null;
	
	public PortalModService()
	{
		portalModManager = (IPortalModManager)ApplicationContextHolder.webApplicationContext.getBean("portalModManager");
	}
	
	public PortalMod addPortalModAll(PortalMod portalMod) {
		
		
			portalMod.setInputtime(new Date());
			portalModManager.save(portalMod);
			return portalMod;
	}
  
	public List deletePortalModAll(String id) {
			portalModManager.deleteById(id);
			List deleteList=portalModManager.findAll();
			return deleteList;
	}

	public List searchPortalModAll() {
		   List searchList=portalModManager.findAll();
		   return searchList;
	}

	public List updatePortalModAll(PortalMod portalMod) {
	       portalModManager.update(portalMod);
	       List updateList=portalModManager.findAll();
	       return updateList;
	}


}
