package com.soaplat.cmsmgr.managerimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.cmsmgr.bean.CmsService;
import com.soaplat.cmsmgr.bean.ProgPackage;
import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.cmsmgr.manageriface.ICmsServiceManager;
import com.soaplat.cmsmgr.manageriface.IProgPackageManager;

// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class CmsServiceManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class CmsServiceManagerImpl implements ICmsServiceManager {
	
	/** The cmsServicedao. */
	IBaseDAO baseDAO;
	
	/** The getcmspk. */
	IGetPK getcmspk;
	
//	public IProgPackageManager progPackageManager = null;
//	public ProgPackageManager progPackageManager
	
	public CmsServiceManagerImpl()
	{
//		progPackageManager = (IProgPackageManager)ApplicationContextHolder.webApplicationContext.getBean("progPackageManager");
	}
	
	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#delete(java.lang.Object[])
	 */
	public void delete(Object[] object) {
		//��ɾ��
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
	public void deleteById(String pkid) {

		CmsService cmsService=(CmsService)baseDAO.getById(CmsService.class, pkid);
		if(cmsService!=null){
			baseDAO.delete(cmsService);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List cmsServicelist=baseDAO.findAll("CmsService","displayorder");
		return cmsServicelist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List cmsServicelist=baseDAO.findByProperty("CmsService", propertyName, value);
		return cmsServicelist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		CmsService cmsService=(CmsService)baseDAO.getById(CmsService.class, pkid);
		return cmsService;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		CmsService cmsService=(CmsService)baseDAO.loadById(CmsService.class, pkid);
		return cmsService;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		CmsService cmsService = new CmsService();
		cmsService = (CmsService)object;
		String strCurMaxPK = baseDAO.getMaxPropertyValue("CmsService", "srvid");
		String strMaxPK = getcmspk.GetTablePK("CmsService", strCurMaxPK);
		cmsService.setSrvid(strMaxPK);
		baseDAO.save(cmsService);
		//
		return baseDAO.getById(CmsService.class, strMaxPK);
//		return null;

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

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public void setGetcmspk(IGetPK getcmspk) {
		this.getcmspk = getcmspk;
	}
	
	// 
	public List getCmsServicesByProgramCategoryId(String genre)
	{
		Map map = new HashMap();
		map.put("genre", genre);
		List list = baseDAO.queryByNamedQuery("select_cmsServicesByProgramCategoryId", map);
		return list;
	}
	
	public List getCmsServicesByProductid(String productid)
	{
		Map map = new HashMap();
		map.put("productid", productid);
		List list = baseDAO.queryByNamedQuery("select_cmsServicesByProductid", map);
		return list;
	}
}
