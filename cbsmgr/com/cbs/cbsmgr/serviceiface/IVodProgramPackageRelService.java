package com.cbs.cbsmgr.serviceiface;

import java.util.List;

import com.soaplat.sysmgr.serviceiface.IFlexBaseService;

import flex.messaging.io.ArrayCollection;

public interface IVodProgramPackageRelService extends IBaseService,IFlexBaseService {
	public List findprogrambyvodpackageid(String vodPackageId);
	public int deleteprogrampackagerelbypackageid(String vodPackageId);
	public int updateprogrampackagerel(ArrayCollection arr);
}
