package com.cbs.cbsmgr.serviceimpl;

import java.util.List;

import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.cbs.cbsmgr.manageriface.IHistoryManager;
import com.cbs.cbsmgr.serviceiface.IHistoryService;

public class HistoryService implements IHistoryService {

	public IHistoryManager historyManager = null;
	
	public HistoryService(IHistoryManager historyManager)
	{
		historyManager = (IHistoryManager)ApplicationContextHolder.webApplicationContext.getBean("historyManager");
	}
	
	public void delete(Object[] object) {
		// TODO Auto-generated method stub
		historyManager.delete(object);
	}

	public void deleteById(String pkid) {
		// TODO Auto-generated method stub
		historyManager.deleteById(pkid);
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List list = historyManager.findAll();
		return list;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List list = historyManager.findByProperty(propertyName, value);
		return list;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		Object object = historyManager.getById(pkid);
		return object;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		Object object = historyManager.loadById(pkid);
		return object;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		Object saveobject = historyManager.save(object);
		return saveobject;
	}

	public void save(Object[] object) {
		// TODO Auto-generated method stub
		historyManager.save(object);
	}

	public void update(Object object) {
		// TODO Auto-generated method stub
		historyManager.update(object);
	}

	public void update(Object[] object) {
		// TODO Auto-generated method stub
		historyManager.update(object);
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
		List list = historyManager.findbyExample(exampleentity);
		return list;
	}
}
