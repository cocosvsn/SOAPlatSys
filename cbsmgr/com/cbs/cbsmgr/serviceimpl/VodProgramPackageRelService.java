package com.cbs.cbsmgr.serviceimpl;

import java.util.List;

import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.cbs.cbsmgr.manageriface.IVodProgramPackageRelManager;
import com.cbs.cbsmgr.serviceiface.IVodProgramPackageRelService;

import flex.messaging.io.ArrayCollection;

public class VodProgramPackageRelService implements IVodProgramPackageRelService {

	public IVodProgramPackageRelManager vodProgramPackageRelManager = null;
	
	public VodProgramPackageRelService(/*IVodProgramPackageRelManager vodProgramPackageRelManager*/)
	{
		vodProgramPackageRelManager = (IVodProgramPackageRelManager)ApplicationContextHolder.webApplicationContext.getBean("vodProgramPackageRelManager");
	}
	
	public void delete(Object[] object) {
		// TODO Auto-generated method stub
		vodProgramPackageRelManager.delete(object);
	}

	public void deleteById(String pkid) {
		// TODO Auto-generated method stub
		vodProgramPackageRelManager.deleteById(pkid);
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List list = vodProgramPackageRelManager.findAll();
		return list;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List list = vodProgramPackageRelManager.findByProperty(propertyName, value);
		return list;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		Object object = vodProgramPackageRelManager.getById(pkid);
		return object;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		Object object = vodProgramPackageRelManager.loadById(pkid);
		return object;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		Object saveobject = vodProgramPackageRelManager.save(object);
		return saveobject;
	}

	public void save(Object[] object) {
		// TODO Auto-generated method stub
		vodProgramPackageRelManager.save(object);
	}

	public void update(Object object) {
		// TODO Auto-generated method stub
		vodProgramPackageRelManager.update(object);
	}

	public void update(Object[] object) {
		// TODO Auto-generated method stub
		vodProgramPackageRelManager.update(object);
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
		List list = vodProgramPackageRelManager.findbyExample(exampleentity);
		return list;
	}
	public List findprogrambyvodpackageid(String vodPackageId) {
		List list = vodProgramPackageRelManager.findprogrambyvodpackageid(vodPackageId);
		return list;
	}
	public int deleteprogrampackagerelbypackageid(String vodPackageId) {
		int ret = vodProgramPackageRelManager.deleteprogrampackagerelbypackageid(vodPackageId);
		return ret;

	}
	
	public void delete(ArrayCollection arr) {
		// TODO Auto-generated method stub
		
	}

	public void save(ArrayCollection arr) {
		// TODO Auto-generated method stub
		vodProgramPackageRelManager.save(arr);
	}

	public void update(ArrayCollection arr) {
		// TODO Auto-generated method stub
		
	}
	
	public int updateprogrampackagerel(ArrayCollection arr){
		int i = vodProgramPackageRelManager.updateprogrampackagerel(arr);
		return i;
	}
}
