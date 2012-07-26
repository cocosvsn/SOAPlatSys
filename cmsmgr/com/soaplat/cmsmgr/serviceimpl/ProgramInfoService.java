package com.soaplat.cmsmgr.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.soaplat.cmsmgr.bean.ProgramInfo;
import com.soaplat.cmsmgr.manageriface.IProgramInfoManager;
import com.soaplat.cmsmgr.serviceiface.IProgramInfoService;

import com.soaplat.cmsmgr.manageriface.IPackStyleManager;
import com.soaplat.cmsmgr.bean.PackStyle;
/**
 * Title 		:the Class ProgramInfoService.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class ProgramInfoService implements IProgramInfoService {

	/** The programinfo manager. */
	public IProgramInfoManager programinfoManager=null;
	//add 2009-12-28 kid
	public IPackStyleManager packStyleManager = null;
	
	/**
	 * Instantiates a new programinfo service.
	 * 
	 * @param programinfoManager the programinfo manager
	 */
	public ProgramInfoService() {
		programinfoManager=(IProgramInfoManager)ApplicationContextHolder.webApplicationContext.getBean("programinfoManager");
		packStyleManager=(IPackStyleManager)ApplicationContextHolder.webApplicationContext.getBean("packStyleManager");
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#delete(java.lang.Object[])
	 */
	public void delete(Object[] object) {
		programinfoManager.delete(object);

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#deleteById(java.lang.String)
	 */
	public void deleteById(String pkid) {
		programinfoManager.deleteById(pkid);
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#findAll()
	 */
	public List findAll() {
		List list=programinfoManager.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List list=programinfoManager.findByProperty(propertyName, value);
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#getById(java.lang.String)
	 */
	public List getById(String pkid) {
		Object object=programinfoManager.getById(pkid);
		//根据OBJECT中styleid查询style信息
		ProgramInfo programinfo=new ProgramInfo();
		programinfo=(ProgramInfo)object;
		Long styleid = programinfo.getStyleid();
		Object styleobject = packStyleManager.getById(styleid.toString());
		PackStyle ps = (PackStyle)styleobject;
		String str = "";
		try{
//			str = new String(ps.getStylexml().getBytes((long)1,(int)(ps.getStylexml().length())),"gb2312");
			str = ps.getStylexml();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		List filelist = new ArrayList();
		filelist.add(object);
		filelist.add(str);
		return filelist;
	}
//	public Object getById(String pkid) {
//		Object object=programinfoManager.getById(pkid);
//		return object;
//	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		Object object=programinfoManager.loadById(pkid);
		return object;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#save(java.lang.Object)
	 */
	public Object save(Object object) {
//		Object saveobject=programinfoManager.save(object);
		Object saveobject=programinfoManager.saveProgAndFiles(object);
		return saveobject;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#save(java.lang.Object[])
	 */
	public void save(Object[] object) {
		programinfoManager.save(object);
//		programinfoManager.SaveProgAndFiles(object);
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#update(java.lang.Object)
	 */
	public void update(Object object) {
		programinfoManager.update(object);

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#update(java.lang.Object[])
	 */
	public void update(Object[] object) {
		programinfoManager.update(object);

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#deleteByIDRetAll(java.lang.String)
	 */
	public List deleteByIDRetAll(String pkid) {
		this.deleteById(pkid);
		List list=this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#deleteRetAll(java.lang.Object[])
	 */
	public List deleteRetAll(Object[] object) {
		this.delete(object);
		List list=this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#saveRetAll(java.lang.Object)
	 */
	public List saveRetAll(Object object) {
		this.save(object);
		List list=this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#saveRetAll(java.lang.Object[])
	 */
	public List saveRetAll(Object[] object) {
		this.save(object);
		List list=this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#updateRetAll(java.lang.Object)
	 */
	public List updateRetAll(Object object) {
		this.update(object);
		List list=this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#updateRetAll(java.lang.Object[])
	 */
	public List updateRetAll(Object[] object) {
		this.update(object);
		List list=this.findAll();
		return list;
	}

	public List findbyExample(Object exampleentity) {
		List list=programinfoManager.findbyExample(exampleentity);
		return list;
	}
	public Object[] getProgAndFilesId(Object object) {
		Object[] idobject=programinfoManager.getProgAndFilesId(object);
		return idobject;
	}
	public Object saveProgAndFilesId(Object progobject,Object progfilesobject,String filepath) {
		Object pfobject=programinfoManager.saveProgAndFiles(progobject, progfilesobject, filepath);
		return pfobject;
	}
}
