package com.cbs.cbsmgr.serviceimpl;

import java.util.List;

import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.cbs.cbsmgr.manageriface.IVodSendManager;
import com.cbs.cbsmgr.serviceiface.IVodSendService;

import com.cbs.cbsmgr.manageriface.IVodSendDetailManager;
import com.cbs.cbsmgr.serviceiface.IVodSendDetailService;

public class VodSendService implements IVodSendService {

	public IVodSendManager vodSendManager = null;
	public IVodSendDetailManager vodSendDetailManager = null;
	
	public VodSendService()
	{
		vodSendManager = (IVodSendManager)ApplicationContextHolder.webApplicationContext.getBean("vodSendManager");
		vodSendDetailManager = (IVodSendDetailManager)ApplicationContextHolder.webApplicationContext.getBean("vodSendDetailManager");
	}
	
	public void delete(Object[] object) {
		// TODO Auto-generated method stub
		vodSendManager.delete(object);
	}

	public void deleteById(String pkid) {
		// TODO Auto-generated method stub
		vodSendManager.deleteById(pkid);
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List list = vodSendManager.findAll();
		return list;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List list = vodSendManager.findByProperty(propertyName, value);
		return list;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		Object object = vodSendManager.getById(pkid);
		return object;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		Object object = vodSendManager.loadById(pkid);
		return object;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		Object saveobject = vodSendManager.save(object);
		return saveobject;
	}

	public void save(Object[] object) {
		// TODO Auto-generated method stub
		vodSendManager.save(object);
	}

	public void update(Object object) {
		// TODO Auto-generated method stub
		vodSendManager.update(object);
	}

	public void update(Object[] object) {
		// TODO Auto-generated method stub
		vodSendManager.update(object);
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
		List list = vodSendManager.findbyExample(exampleentity);
		return list;
	}
	public int updateVodSendByVodSendId(String vodSendId){
		int ret = vodSendManager.updateVodSendByVodSendId(vodSendId);
		return ret;
	}
	public int deleteVodSendCascade(String vodSendId){
		vodSendManager.deleteById(vodSendId);
		int ret = vodSendDetailManager.deletesenddetailbysendid(vodSendId);
		return ret;
	}
}
