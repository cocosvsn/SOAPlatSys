package com.cbs.cbsmgr.manageriface;

import java.util.List;

public interface IHardwareModelManager extends IBaseManager {

	
	public List getHardwareModelsByHardwareModelName(String hardwareModelName);
}
