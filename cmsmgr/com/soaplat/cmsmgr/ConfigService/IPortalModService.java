package com.soaplat.cmsmgr.ConfigService;

import java.util.List;

import com.soaplat.cmsmgr.bean.PortalMod;


public interface IPortalModService {
	
	public PortalMod addPortalModAll(PortalMod portalMod);
	public List updatePortalModAll(PortalMod portalMod);
	public List deletePortalModAll(String id);
	public List searchPortalModAll();

}
