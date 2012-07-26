package com.soaplat.cmsmgr.manageriface;

import java.util.List;
import java.util.Map;

import com.soaplat.cmsmgr.bean.ProgramInfo;
import com.soaplat.sysmgr.manageriface.IBaseManager;

public interface IProgramInfoManager extends IBaseManager {
	public Object saveProgAndFiles(Object object) ;
	public Object[] getProgAndFilesId(Object object);
	public Object saveProgAndFiles(Object progobject,Object progfilesobject,String filepath);
	
	public List getProgramInfosByProductid(String productid);
	
	/**
	 * 根据拼接的HQL语句查询节目信息
	 * @param hql 拼接好的HQL语句
	 * @param params HQL语句参数列表
	 * @return 返回查询得到的节目信息List<ProgramInfo>
	 */
	public List<ProgramInfo> queryProgramInfos(String hql, Map<String, Object> params);
}
