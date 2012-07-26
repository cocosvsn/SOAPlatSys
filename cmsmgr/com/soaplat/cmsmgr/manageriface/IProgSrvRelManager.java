package com.soaplat.cmsmgr.manageriface;

import java.util.List;

import com.soaplat.sysmgr.manageriface.IBaseManager;

public interface IProgSrvRelManager extends IBaseManager {

	public List getProgSrvRelsByProductidAndSrvid(String productId, String srvId);
}
