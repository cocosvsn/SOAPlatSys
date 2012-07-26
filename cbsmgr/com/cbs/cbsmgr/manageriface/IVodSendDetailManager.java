package com.cbs.cbsmgr.manageriface;

import java.util.List;

import com.soaplat.sysmgr.manageriface.IFlexBaseManager;

import flex.messaging.io.ArrayCollection;

public interface IVodSendDetailManager extends IBaseManager,IFlexBaseManager{
	public List findproductinfobysend(String VodSendId);
	public List findvodsendandvodsenddetail(long dealState);
	public int deletesenddetailbysendid(String vodSendId);
	public int updatesenddetailbysendid(ArrayCollection arr);
}
