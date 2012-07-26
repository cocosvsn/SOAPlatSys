package com.soaplat.cmsmgr.serviceiface;

import java.util.List;

import com.soaplat.cmsmgr.serviceiface.IBaseService;

public interface IProgramFilesService extends IBaseService {
	public List findstoragelistbyfileid(String programid,String filetypeid);
	public String findmaxprogramfileidbyprogramidandfiletype(String programid,String filetypeid);
}
