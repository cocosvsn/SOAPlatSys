package com.cbs.cbsmgr.manageriface;

import java.util.List;

import flex.messaging.io.ArrayCollection;

public interface IVodPackageManager extends IBaseManager {
	public List findpackageinfobypackageidlist(ArrayCollection packageidlist);
}
