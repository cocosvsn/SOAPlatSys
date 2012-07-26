package com.soaplat.sysmgr.serviceimpl;

import java.util.List;

import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.soaplat.sysmgr.manageriface.IDictManager;
import com.soaplat.sysmgr.serviceiface.IDictService;

/**
 * Title 		:the Class DictService.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class DictService implements IDictService {

	/** The dict manager. */
	public IDictManager dictManager=null;
	
	/**
	 * Instantiates a new dict service.
	 */
	public DictService() {
		dictManager=(IDictManager)ApplicationContextHolder.webApplicationContext.getBean("dictManager");
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#delete(java.lang.Object[])
	 */
	public void delete(Object[] object) {
		dictManager.delete(object);

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#deleteById(java.lang.String)
	 */
	public void deleteById(String pkid) {
		dictManager.deleteById(pkid);

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#findAll()
	 */
	public List findAll() {
		List list=dictManager.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List list=dictManager.findByProperty(propertyName, value);
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		Object object=dictManager.getById(pkid);
		return object;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		Object object=dictManager.loadById(pkid);
		return object;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#save(java.lang.Object)
	 */
	public Object save(Object object) {
		Object saveobject=dictManager.save(object);
		return saveobject;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#save(java.lang.Object[])
	 */
	public void save(Object[] object) {
		dictManager.save(object);

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#update(java.lang.Object)
	 */
	public void update(Object object) {
		dictManager.update(object);

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#update(java.lang.Object[])
	 */
	public void update(Object[] object) {
		dictManager.update(object);

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
		List list=dictManager.findbyExample(exampleentity);
		return list;
	}

}
