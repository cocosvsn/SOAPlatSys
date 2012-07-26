package com.cbs.cbsmgr.serviceimpl;

import java.util.List;

import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.cbs.cbsmgr.manageriface.ISmsAccountManager;
import com.cbs.cbsmgr.serviceiface.ISmsAccountService;

public class SmsAccountService implements ISmsAccountService {

	public ISmsAccountManager smsAccountManager = null;
	
	public SmsAccountService(/*ISmsAccountManager smsAccountManager*/)
	{
		smsAccountManager = (ISmsAccountManager)ApplicationContextHolder.webApplicationContext.getBean("smsAccountManager");
	}
	
	public void delete(Object[] object) {
		// TODO Auto-generated method stub
		smsAccountManager.delete(object);
	}

	public void deleteById(String pkid) {
		// TODO Auto-generated method stub
		smsAccountManager.deleteById(pkid);
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List list = smsAccountManager.findAll();
		return list;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List list = smsAccountManager.findByProperty(propertyName, value);
		return list;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		Object object = smsAccountManager.getById(pkid);
		return object;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		Object object = smsAccountManager.loadById(pkid);
		return object;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		Object saveobject = smsAccountManager.save(object);
		return saveobject;
	}

	public void save(Object[] object) {
		// TODO Auto-generated method stub
		smsAccountManager.save(object);
	}

	public void update(Object object) {
		// TODO Auto-generated method stub
		smsAccountManager.update(object);
	}

	public void update(Object[] object) {
		// TODO Auto-generated method stub
		smsAccountManager.update(object);
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
		List list = this.findAll();
		return list;
	}

	public List findbyExample(Object exampleentity) {
		List list = smsAccountManager.findbyExample(exampleentity);
		return list;
	}
}
