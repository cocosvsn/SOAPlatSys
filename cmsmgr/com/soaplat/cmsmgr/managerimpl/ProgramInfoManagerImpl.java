package com.soaplat.cmsmgr.managerimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.soaplat.cmsmgr.bean.ProgramInfo;
import com.soaplat.cmsmgr.bean.ProgramFiles;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.cmsmgr.manageriface.IProgramInfoManager;

import com.soaplat.amsmgr.bean.AmsStoragePrgRel;
// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class ProgramInfoManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class ProgramInfoManagerImpl implements IProgramInfoManager {
	
	/** The programinfodao. */
	IBaseDAO baseDAO;
	
	/** The getcmspk. */
	IGetPK getcmspk;
	
	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#delete(java.lang.Object[])
	 */
	public void delete(Object[] object) {
		if(object.length>0){
			for(int i=0;i<object.length;i++){
				baseDAO.delete(object[i]);
			}
		}else{
			return ;
		}	
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#deleteById(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void deleteById(String pkid) {
		List<ProgramFiles> programFiles = this.baseDAO.findByProperty("ProgramFiles", "programid", pkid);
		for (ProgramFiles programFile : programFiles) {
			this.baseDAO.delete(programFile);
		}

		ProgramInfo programinfo=(ProgramInfo)baseDAO.getById(ProgramInfo.class, pkid);
		if(programinfo!=null){
			baseDAO.delete(programinfo);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List programinfolist=baseDAO.findAll("ProgramInfo","programid");
		return programinfolist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List programinfolist=baseDAO.findByProperty("ProgramInfo", propertyName, value);
		return programinfolist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		ProgramInfo programinfo=(ProgramInfo)baseDAO.getById(ProgramInfo.class, pkid);
		return programinfo;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		ProgramInfo programinfo=(ProgramInfo)baseDAO.loadById(ProgramInfo.class, pkid);
		return programinfo;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		String strMaxPK="";
		ProgramInfo programinfo=new ProgramInfo();
		programinfo=(ProgramInfo)object;
		
		if(programinfo.getProgramid() == null || programinfo.getProgramid().equalsIgnoreCase(""))
		{
			String progtype = programinfo.getProgtype();
			String progproperty = programinfo.getProgproperty();
			strMaxPK = progtype + "|" + progproperty;
			strMaxPK = getcmspk.GetTablePK("ProgramInfo", strMaxPK);
			programinfo.setProgramid(strMaxPK);
		}
		if(programinfo.getInputtime() == null)
			programinfo.setInputtime(new Date());
		baseDAO.save(programinfo);
		return baseDAO.getById(ProgramInfo.class, programinfo.getProgramid());
		
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#update(java.lang.Object)
	 */
	public void update(Object object) {
		baseDAO.update(object);
	}

	/**
	 * Sets the baseDAO.
	 * 
	 * @param baseDAO the new baseDAO
	 */


	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object[])
	 */
	public void save(Object[] object) {
		if (object.length>0){
			for(int i=0;i<object.length;i++){
				this.save(object[i]);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#update(java.lang.Object[])
	 */
	public void update(Object[] object) {
		if (object.length>0){
			for(int i=0;i<object.length;i++){
				this.update(object[i]);
			}
		}
	}

	public List findbyExample(Object exampleentity) {
		List list=baseDAO.findbyExample(exampleentity);
		return list;
	}

	public Object saveProgAndFiles(Object object) {
		String strMaxPK="";
		String programid="";
		String preStr="";
		ProgramInfo programinfo=new ProgramInfo();
		programinfo=(ProgramInfo)object;
//		strMaxPK=baseDAO.getMaxPropertyValue("ProgramInfo", "programinfo");
		String progtype  = programinfo.getProgtype();
		String progproperty  = programinfo.getProgproperty();
		strMaxPK=progtype+"|"+progproperty;
		strMaxPK=getcmspk.GetTablePK("ProgramInfo", strMaxPK);
		programinfo.setProgramid(strMaxPK);
		programid = strMaxPK;
		if(programinfo.getInputtime() == null)
			programinfo.setInputtime(new Date());
//		baseDAO.save(programinfo);
		Map map = new HashMap(0);
		preStr=programid+"001"+"%";
		map.put("id", preStr);
		List list=baseDAO.queryByNamedQuery("select_maxprogfileid",map);
		if (list.get(0)==null)
		{
			strMaxPK=getcmspk.GetTablePK("ProgramFiles", programid+"001");
			
		}else
		{
			strMaxPK=list.get(0).toString();
			strMaxPK=getcmspk.GetTablePK("ProgramFiles", strMaxPK);
		}
		ProgramFiles programfiles = new ProgramFiles();
		programfiles.setProgfileid(strMaxPK);
		programfiles.setFiletypeid("1");
		programfiles.setProgramid(programid);
		if(programfiles.getInputtime() == null)
			programfiles.setInputtime(new Date());
		baseDAO.save(programinfo);
		baseDAO.save(programfiles);
//		return null;
		return baseDAO.getById(ProgramInfo.class, strMaxPK);
	}
	
	/**
	 * HuangBo update by 2010年8月26日 10时40分
	 * 此方法 安迪 所写, 在节目导入的时候, 为文件生成主键.
	 * 此前是为上海做节目导入的时候, 为节目文件生成主键ID所写.
	 * @param programInfo 节目对象, 是没有主键的节目对象.
	 * @return Object[2]
	 * object[0]: programID
	 * object[1]: strMaxPK
	 */
	public Object[] getProgAndFilesId(Object programInfo) {
		String strMaxPK = "";
		String programid = "";
		String preStr = "";
		ProgramInfo programinfo = (ProgramInfo) programInfo;
		String progtype = programinfo.getProgtype();
		String progproperty = programinfo.getProgproperty();
		strMaxPK = progtype + "|" + progproperty;
		strMaxPK = getcmspk.GetTablePK("ProgramInfo", strMaxPK);
		programinfo.setProgramid(strMaxPK);
		programid = strMaxPK;
		Map map = new HashMap(0);
		preStr = programid + "001" + "%";
		map.put("id", preStr);
		List list = baseDAO.queryByNamedQuery("select_maxprogfileid", map);
		if (list.get(0) == null) {
			strMaxPK = getcmspk.GetTablePK("ProgramFiles", programid + "001");

		} else {
			strMaxPK = list.get(0).toString();
			strMaxPK = getcmspk.GetTablePK("ProgramFiles", strMaxPK);
		}
		Object[] objectid = new Object[10];
		objectid[0] = programid;
		objectid[1] = strMaxPK;
		return objectid;
	}

	public Object saveProgAndFiles(Object progobject, Object progfilesobject,
			String filepath) {
		String strMaxPK = "";
		ProgramInfo programinfo = new ProgramInfo();
		programinfo = (ProgramInfo) progobject;
		ProgramFiles programfiles = new ProgramFiles();
		programfiles = (ProgramFiles) progfilesobject;
		if (programfiles.getInputtime() == null)
			programfiles.setInputtime(new Date());
		strMaxPK = programfiles.getProgfileid();
		baseDAO.save(programinfo);
		baseDAO.save(programfiles);

		// 同时保存节目存放位置表 2009-12-26 KID
		AmsStoragePrgRel amsstorageprgrel = new AmsStoragePrgRel();
		String Stglobalid = programfiles.getStorageid();
		String Stdirglobalid = programfiles.getStoragedirid();
		String Prglobalid = programfiles.getProgramid();
		String Ftypeglobalid = programfiles.getFiletypeid();
		String Filename = programfiles.getFilename();
		String Inputmanid = programfiles.getInputmanid();

		amsstorageprgrel.setStglobalid(Stglobalid);
		amsstorageprgrel.setStdirglobalid(Stdirglobalid);
		amsstorageprgrel.setPrglobalid(Prglobalid);
		amsstorageprgrel.setFtypeglobalid(Ftypeglobalid);
		amsstorageprgrel.setFilename(Filename);
		amsstorageprgrel.setInputmanid(Inputmanid);
		amsstorageprgrel.setUploadtime(new Date());
		amsstorageprgrel.setFilepath(filepath);
		// Edited by Andy at 20100125 15:00
		if (amsstorageprgrel.getFiledate() == null)
			amsstorageprgrel.setFiledate(new Date());
		if (amsstorageprgrel.getInputtime() == null)
			amsstorageprgrel.setInputtime(new Date());
		amsstorageprgrel.setProgfileid(programfiles.getProgfileid());
		// End
		strMaxPK = getcmspk.GetTablePK("AmsStoragePrgRel");
		amsstorageprgrel.setRelid(strMaxPK);
		baseDAO.save(amsstorageprgrel);

		// return null;
		return baseDAO.getById(ProgramFiles.class, strMaxPK);
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public void setGetcmspk(IGetPK getcmspk) {
		this.getcmspk = getcmspk;
	}

	public List getProgramInfosByProductid(String productid) {
		Map map = new HashMap();
		map.put("productid", productid);
		List list = baseDAO.queryByNamedQuery("select_programInfosByProductid",
				map);
		return list;
	}

	/**
	 * 根据拼接的HQL语句查询节目信息
	 * @param hql 拼接好的HQL语句
	 * @param params HQL语句参数列表
	 * @return 返回查询得到的节目信息 List<ProgramInfo>
	 */
	@SuppressWarnings("unchecked")
	public List<ProgramInfo> queryProgramInfos(String hql, Map<String, Object> params) {
		return (List<ProgramInfo>) this.baseDAO.query(hql, params);
	}
}
