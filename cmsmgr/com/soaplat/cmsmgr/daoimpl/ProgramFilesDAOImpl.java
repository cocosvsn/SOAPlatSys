package com.soaplat.cmsmgr.daoimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.cmsmgr.bean.ProgramFiles;
import com.soaplat.cmsmgr.daoiface.IProgramFilesDAO;
import com.soaplat.sysmgr.common.DAOImpl;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.sysmgr.common.IGetPK;

public class ProgramFilesDAOImpl implements IProgramFilesDAO {


	IBaseDAO baseDAO;
	IGetPK getcmspk;
	
	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public void setGetcmspk(IGetPK getcmspk) {
		this.getcmspk = getcmspk;
	}
	

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#deleteById(java.lang.String)
	 */
	public void deleteById(String pkid) {

		ProgramFiles programfiles=(ProgramFiles)baseDAO.getById(ProgramFiles.class, pkid);
		if(programfiles!=null){
			baseDAO.delete(programfiles);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List programfileslist=baseDAO.findAll("ProgramFiles","progfileid");
		return programfileslist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List programfileslist=baseDAO.findByProperty("ProgramFiles", propertyName, value);
		return programfileslist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public ProgramFiles getById(String pkid) {
		ProgramFiles programfiles=(ProgramFiles)baseDAO.getById(ProgramFiles.class, pkid);
		return programfiles;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public ProgramFiles loadById(String pkid) {
		ProgramFiles programfiles=(ProgramFiles)baseDAO.loadById(ProgramFiles.class, pkid);
		return programfiles;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public ProgramFiles save(ProgramFiles programfiles) {

		baseDAO.save(programfiles);
		return programfiles;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#update(java.lang.Object)
	 */
	public void update(ProgramFiles programfiles) {
		baseDAO.update(programfiles);
	}


	public List findbyExample(Object exampleentity) {
		List list=baseDAO.findbyExample(exampleentity);
		return list;
	}
	public List findbyprogramidfiletype(String programid,String filetypeid)
	{
		Map map = new HashMap(0);
		map.put("programid", programid);
		map.put("filetypeid", filetypeid);
		List list=baseDAO.queryByNamedQuery("select_programidfiletypeid",map);
		return list;
	}
	public List findstoragelistbyfileid(String programid,String filetypeid)
	{
		Map map = new HashMap(0);
		map.put("programid", programid);
		map.put("filetypeid", filetypeid);
		List list=baseDAO.queryByNamedQuery("select_storagelistbyfileid",map);
		return list;
	}
	public String findmaxprogramfileidbyprogramidandfiletype(String programid, String filetypeid)
	{
		String preStr = "";
		String strMaxPK = "";
		Map map = new HashMap(0);
		preStr = programid + filetypeid + "%";
		map.put("id", preStr);
		List list = baseDAO.queryByNamedQuery("select_maxprogfileid", map);
		if (list.get(0) == null)
		{
			strMaxPK = getcmspk.GetTablePK("ProgramFiles", programid + filetypeid);
			
		}else
		{
			strMaxPK = list.get(0).toString();
			strMaxPK = getcmspk.GetTablePK("ProgramFiles", strMaxPK);
		}
		return strMaxPK;
	}
	
	/**
	 * 获取节目文件ID
	 * @param programInfoId
	 * @return
	 */
	public String getProgramFilesID(String programInfoId) {
		String strMaxPK;
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("id", programInfoId + "001" + "%");
		List list = baseDAO.queryByNamedQuery("select_maxprogfileid", map);
		if (list.get(0) == null) {
			strMaxPK = getcmspk.GetTablePK("ProgramFiles", programInfoId + "001");

		} else {
			strMaxPK = list.get(0).toString();
			strMaxPK = getcmspk.GetTablePK("ProgramFiles", strMaxPK);
		}
		return strMaxPK;
	}
}
