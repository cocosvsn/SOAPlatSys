package com.cbs.cbsmgr.serviceimpl;

import java.util.List;

import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.cbs.cbsmgr.manageriface.IVodPackageManager;
import com.cbs.cbsmgr.serviceiface.IVodPackageService;

import com.cbs.cbsmgr.manageriface.IVodProgramPackageRelManager;
import com.cbs.cbsmgr.serviceiface.IVodProgramPackageRelService;


import flex.messaging.io.ArrayCollection;

public class VodPackageService implements IVodPackageService {

	public IVodPackageManager vodPackageManager = null;
	public IVodProgramPackageRelManager vodProgramPackageRelManager = null;
	
	public VodPackageService(/*IVodPackageManager vodPackageManager*/)
	{
		vodPackageManager = (IVodPackageManager)ApplicationContextHolder.webApplicationContext.getBean("vodPackageManager");
		vodProgramPackageRelManager = (IVodProgramPackageRelManager)ApplicationContextHolder.webApplicationContext.getBean("vodProgramPackageRelManager");
	}
	
	public void delete(Object[] object) {
		// TODO Auto-generated method stub
		vodPackageManager.delete(object);
	}

	public void deleteById(String pkid) {
		// TODO Auto-generated method stub
		vodPackageManager.deleteById(pkid);
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List list = vodPackageManager.findAll();
		return list;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List list = vodPackageManager.findByProperty(propertyName, value);
		return list;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		Object object = vodPackageManager.getById(pkid);
		return object;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		Object object = vodPackageManager.loadById(pkid);
		return object;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		Object saveobject = vodPackageManager.save(object);
		return saveobject;
	}

	public void save(Object[] object) {
		// TODO Auto-generated method stub
		vodPackageManager.save(object);
	}

	public void update(Object object) {
		// TODO Auto-generated method stub
		vodPackageManager.update(object);
	}

	public void update(Object[] object) {
		// TODO Auto-generated method stub
		vodPackageManager.update(object);
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#deleteByIDRetAll(java.lang.String)
	 */
	public List deleteByIDRetAll(String pkid) {
		this.deleteById(pkid);
		List list = this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#deleteRetAll(java.lang.Object[])
	 */
	public List deleteRetAll(Object[] object) {
		this.delete(object);
		List list = this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#saveRetAll(java.lang.Object)
	 */
	public List saveRetAll(Object object) {
		this.save(object);
		List list = this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#saveRetAll(java.lang.Object[])
	 */
	public List saveRetAll(Object[] object) {
		this.save(object);
		List list = this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#updateRetAll(java.lang.Object)
	 */
	public List updateRetAll(Object object) {
		this.update(object);
		List list = this.findAll();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#updateRetAll(java.lang.Object[])
	 */
	public List updateRetAll(Object[] object) {
		this.update(object);
		List list = this.findAll();
		return list;
	}

	public List findbyExample(Object exampleentity) {
		List list = vodPackageManager.findbyExample(exampleentity);
		return list;
	}
	public List findpackageinfobypackageidlist(ArrayCollection packageidlist) {
		List list = vodPackageManager.findpackageinfobypackageidlist(packageidlist);
		return list;
	}
	public int deletePackageCascade(String pkid){
		vodPackageManager.deleteById(pkid);
		int ret = vodProgramPackageRelManager.deleteprogrampackagerelbypackageid(pkid);
		return ret;
	}
}
