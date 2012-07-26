package com.cbs.cbsmgr.manageriface;

import java.util.List;

public interface IHardwareManager extends IBaseManager {

	//
	public List getHardwaresByHardwareModelIdAndSerialNo(String hardwareModelId, String serialNo);
	
	//
	public List getHardwaresByHardwareModelIdAndCardNo(String hardwareModelId, String cardNo);
	
	//
	public List getHardwaresByHardwareModelIdAndOtherNo(String hardwareModelId, String otherNo);
}
