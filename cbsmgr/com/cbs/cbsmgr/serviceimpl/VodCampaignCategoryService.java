package com.cbs.cbsmgr.serviceimpl;

import java.util.List;

import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.cbs.cbsmgr.manageriface.IVodCampaignCategoryManager;
import com.cbs.cbsmgr.serviceiface.IVodCampaignCategoryService;

public class VodCampaignCategoryService implements IVodCampaignCategoryService {

	public IVodCampaignCategoryManager vodCampaignCategoryManager = null;
	
	public VodCampaignCategoryService(/*IVodCampaignCategoryManager vodCampaignCategoryManager*/)
	{
		vodCampaignCategoryManager = (IVodCampaignCategoryManager)ApplicationContextHolder.webApplicationContext.getBean("vodCampaignCategoryManager");
	}
	
	public void delete(Object[] object) {
		// TODO Auto-generated method stub
		vodCampaignCategoryManager.delete(object);
	}

	public void deleteById(String pkid) {
		// TODO Auto-generated method stub
		vodCampaignCategoryManager.deleteById(pkid);
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List list = vodCampaignCategoryManager.findAll();
		return list;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List list = vodCampaignCategoryManager.findByProperty(propertyName, value);
		return list;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		Object object = vodCampaignCategoryManager.getById(pkid);
		return object;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		Object object = vodCampaignCategoryManager.loadById(pkid);
		return object;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		Object saveobject = vodCampaignCategoryManager.save(object);
		return saveobject;
	}

	public void save(Object[] object) {
		// TODO Auto-generated method stub
		vodCampaignCategoryManager.save(object);
	}

	public void update(Object object) {
		// TODO Auto-generated method stub
		vodCampaignCategoryManager.update(object);
	}

	public void update(Object[] object) {
		// TODO Auto-generated method stub
		vodCampaignCategoryManager.update(object);
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
		List list = vodCampaignCategoryManager.findbyExample(exampleentity);
		return list;
	}
}
