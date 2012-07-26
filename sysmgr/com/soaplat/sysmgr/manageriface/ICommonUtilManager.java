package com.soaplat.sysmgr.manageriface;

import java.util.List;

import flex.messaging.io.amf.ASObject;

public interface ICommonUtilManager {
	public List queryByCriteria(ASObject asobj);
	public List queryByHQL(String strSQL);
}
