package com.soaplat.cmsmgr.serviceimpl;

import java.util.List;

import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.soaplat.cmsmgr.manageriface.IProgPublishersManager;
import com.soaplat.cmsmgr.serviceiface.IProgPublishersService;

/**
 * Title 		:the Class ProgPublishersService.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class ProgPublishersService implements IProgPublishersService {

	/** The progpublishers manager. */
	public IProgPublishersManager progpublishersManager=null;
	
	/**
	 * Instantiates a new progpublishers service.
	 * 
	 * @param progpublishersManager the progpublishers manager
	 */
	public ProgPublishersService() {
		progpublishersManager=(IProgPublishersManager)ApplicationContextHolder.webApplicationContext.getBean("progpublishersManager");
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#delete(java.lang.Object[])
	 */
	public void delete(Object[] object) {
		progpublishersManager.delete(object);

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#deleteById(java.lang.String)
	 */
	public void deleteById(String pkid) {
		progpublishersManager.deleteById(pkid);

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#findAll()
	 */
	public List findAll() {
		List list=progpublishersManager.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List list=progpublishersManager.findByProperty(propertyName, value);
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		Object object=progpublishersManager.getById(pkid);
		return object;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		Object object=progpublishersManager.loadById(pkid);
		return object;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#save(java.lang.Object)
	 */
	public Object save(Object object) {
		Object saveobject=progpublishersManager.save(object);
		return saveobject;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#save(java.lang.Object[])
	 */
	public void save(Object[] object) {
		progpublishersManager.save(object);

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#update(java.lang.Object)
	 */
	public void update(Object object) {
		progpublishersManager.update(object);

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#update(java.lang.Object[])
	 */
	public void update(Object[] object) {
		progpublishersManager.update(object);

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
		List list=progpublishersManager.findbyExample(exampleentity);
		return list;
	}

}
