package com.soaplat.cmsmgr.serviceiface;

import com.soaplat.cmsmgr.serviceiface.IBaseService;

public interface IProgramInfoService extends IBaseService {
	public Object[] getProgAndFilesId(Object object);
	public Object saveProgAndFilesId(Object progobject,Object progfilesobject,String  filepath);
}
