package com.soaplat.cmsmgr.manageriface;

import java.util.List;

import com.soaplat.sysmgr.manageriface.IBaseManager;

public interface IProgPPRelManager extends IBaseManager {

	public List getProgPPRelsByProductidAndProgramid(String productId, String programId);
}
