package com.soaplat.amsmgr.serviceimpl;

import java.util.List;

import com.soaplat.amsmgr.manageriface.IAmsStorageManager;
import com.soaplat.amsmgr.serviceiface.IAmsStorageService;
import com.soaplat.sysmgr.common.ApplicationContextHolder;


/**
 * Title 		:the Class AmsStorageService.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class AmsStorageService implements IAmsStorageService {

	/** The storage manager. */
	public IAmsStorageManager storageManager=null;
	
	/**
	 * Instantiates a new ams storage service.
	 */
	public AmsStorageService() {
		storageManager=(IAmsStorageManager)ApplicationContextHolder.webApplicationContext.getBean("amsstorageManager");
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#delete(java.lang.Object[])
	 */
	public void delete(Object[] object) {
		storageManager.delete(object);

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#deleteById(java.lang.String)
	 */
	public void deleteById(String pkid) {
		storageManager.deleteById(pkid);

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#findAll()
	 */
	public List findAll() {
		List list=storageManager.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List list=storageManager.findByProperty(propertyName, value);
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		Object object=storageManager.getById(pkid);
		return object;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		Object object=storageManager.loadById(pkid);
		return object;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#save(java.lang.Object)
	 */
	public Object save(Object object) {
		Object saveobject=storageManager.save(object);
		return saveobject;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#save(java.lang.Object[])
	 */
	public void save(Object[] object) {
		storageManager.save(object);

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#update(java.lang.Object)
	 */
	public void update(Object object) {
		storageManager.update(object);

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#update(java.lang.Object[])
	 */
	public void update(Object[] object) {
		storageManager.update(object);

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

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#findbyExample(java.lang.Object)
	 */
	public List findbyExample(Object exampleentity) {
		List list=storageManager.findbyExample(exampleentity);
		return list;
	}
}
