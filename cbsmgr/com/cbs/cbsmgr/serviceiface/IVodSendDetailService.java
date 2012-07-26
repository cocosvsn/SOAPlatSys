package com.cbs.cbsmgr.serviceiface;

import java.util.List;

import com.soaplat.sysmgr.serviceiface.IFlexBaseService;

import flex.messaging.io.ArrayCollection;

public interface IVodSendDetailService extends IBaseService,IFlexBaseService {
	public List findproductinfobysend(String VodSendId);
	public int deletesenddetailbysendid(String vodSendId);
	public int updateprogrampackagerel(ArrayCollection arr);

	
}
