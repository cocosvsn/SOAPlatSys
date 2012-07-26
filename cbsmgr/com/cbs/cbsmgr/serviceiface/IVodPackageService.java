package com.cbs.cbsmgr.serviceiface;

import java.util.List;

import flex.messaging.io.ArrayCollection;

public interface IVodPackageService extends IBaseService {
	public List findpackageinfobypackageidlist(ArrayCollection packageidlist);
	public int deletePackageCascade(String pkid);
}
