package com.cbs.cbsmgr.manageriface;

import java.util.List;

import com.soaplat.sysmgr.manageriface.IFlexBaseManager;

import flex.messaging.io.ArrayCollection;

public interface IVodProgramPackageRelManager extends IBaseManager,IFlexBaseManager {
	public List findprogrambyvodpackageid(String VodSendId);
	public int deleteprogrampackagerelbypackageid(String vodPackageId);
	public int updateprogrampackagerel(ArrayCollection arr);
}
