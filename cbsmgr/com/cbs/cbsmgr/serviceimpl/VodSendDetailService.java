package com.cbs.cbsmgr.serviceimpl;

import java.util.List;

import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.cbs.cbsmgr.manageriface.IVodSendDetailManager;
import com.cbs.cbsmgr.serviceiface.IVodSendDetailService;

import flex.messaging.io.ArrayCollection;

public class VodSendDetailService implements IVodSendDetailService {

	public IVodSendDetailManager vodSendDetailManager = null;
	
	public VodSendDetailService(/*IVodSendDetailManager vodSendDetailManager*/)
	{
		vodSendDetailManager = (IVodSendDetailManager)ApplicationContextHolder.webApplicationContext.getBean("vodSendDetailManager");
	}
	
	public void delete(Object[] object) {
		// TODO Auto-generated method stub
		vodSendDetailManager.delete(object);
	}

	public void deleteById(String pkid) {
		// TODO Auto-generated method stub
		vodSendDetailManager.deleteById(pkid);
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List list = vodSendDetailManager.findAll();
		return list;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List list = vodSendDetailManager.findByProperty(propertyName, value);
		return list;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		Object object = vodSendDetailManager.getById(pkid);
		return object;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		Object object = vodSendDetailManager.loadById(pkid);
		return object;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		Object saveobject = vodSendDetailManager.save(object);
		return saveobject;
	}

	public void save(Object[] object) {
		// TODO Auto-generated method stub
		vodSendDetailManager.save(object);
	}

	public void update(Object object) {
		// TODO Auto-generated method stub
		vodSendDetailManager.update(object);
	}

	public void update(Object[] object) {
		// TODO Auto-generated method stub
		vodSendDetailManager.update(object);
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
		List list = vodSendDetailManager.findbyExample(exampleentity);
		return list;
	}
	public List findproductinfobysend(String VodSendId)
	{
		List list = vodSendDetailManager.findproductinfobysend(VodSendId);
		return list;
	}

	public void delete(ArrayCollection arr) {
		// TODO Auto-generated method stub
		
	}

	public void save(ArrayCollection arr) {
		// TODO Auto-generated method stub
		vodSendDetailManager.save(arr);
	}

	public void update(ArrayCollection arr) {
		// TODO Auto-generated method stub
		
	}
	public int deletesenddetailbysendid(String vodSendId)
	{
		int ret  = vodSendDetailManager.deletesenddetailbysendid(vodSendId);
		return ret;
	}

	public int updateprogrampackagerel(ArrayCollection arr) {
		// TODO Auto-generated method stub
		int i = vodSendDetailManager.updatesenddetailbysendid(arr);
		return i;
	}
}
