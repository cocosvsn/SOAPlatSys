package com.soaplat.sysmgr.serviceimpl;

import java.util.List;

import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.soaplat.sysmgr.manageriface.ICommonUtilManager;

import flex.messaging.io.ArrayCollection;
import flex.messaging.io.amf.ASObject;


public class CommonUtilService {
	public ICommonUtilManager commonutilManager=null;
	public CommonUtilService() {
		commonutilManager=(ICommonUtilManager)ApplicationContextHolder.webApplicationContext.getBean("commonutilManager");
	}
	public List queryByCriteria(ASObject asobj) {
		// TODO Auto-generated method stub
		List list = commonutilManager.queryByCriteria(asobj);
		return list;
	}
	public List queryByHQL(String strSQL) {
		// TODO Auto-generated method stub
		List list = commonutilManager.queryByHQL(strSQL);
		return list;		
	}
	
	public void testtesttest(ArrayCollection arr){

		return;
	}
}
	