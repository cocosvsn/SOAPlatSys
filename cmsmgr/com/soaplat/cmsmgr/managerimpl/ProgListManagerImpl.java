package com.soaplat.cmsmgr.managerimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.cmsmgr.bean.ProgList;
import com.soaplat.cmsmgr.bean.ProgPackage;
import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.cmsmgr.manageriface.IProgListManager;
import com.soaplat.cmsmgr.manageriface.IProgPackageManager;

// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class ProgListManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class ProgListManagerImpl implements IProgListManager {
	
	/** The ProgListdao. */
	IBaseDAO baseDAO;
	
	/** The getcmspk. */
	IGetPK getcmspk;
	
//	public IProgPackageManager progPackageManager = null;
//	public ProgPackageManager progPackageManager
	
	public ProgListManagerImpl()
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

		ProgList ProgList=(ProgList)baseDAO.getById(ProgList.class, pkid);
		if(ProgList!=null){
			baseDAO.delete(ProgList);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List ProgListlist=baseDAO.findAll("ProgList","pdate");
		return ProgListlist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List ProgListlist=baseDAO.findByProperty("ProgList", propertyName, value);
		return ProgListlist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		ProgList ProgList=(ProgList)baseDAO.getById(ProgList.class, pkid);
		return ProgList;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		ProgList ProgList=(ProgList)baseDAO.loadById(ProgList.class, pkid);
		return ProgList;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		ProgList ProgList = new ProgList();
		ProgList = (ProgList)object;

		String strMaxPK = getcmspk.GetTablePK("ProgList");
		ProgList.setPdate(strMaxPK);
		baseDAO.save(ProgList);
		//
		return baseDAO.getById(ProgList.class, strMaxPK);
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
	
	public List getProgListsByIdActAndPop(String idAct, String pop)
	{
		Map map = new HashMap();
		map.put("idAct", idAct);
		List list = baseDAO.queryByNamedQuery("select_ProgListsByIdActAndPop", map);
		return list;
	}
}
