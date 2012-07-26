package com.cbs.cbsmgr.serviceimpl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.cbs.cbsmgr.bean.PackageItem;
import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.cbs.cbsmgr.manageriface.IPackageItemManager;
import com.cbs.cbsmgr.serviceiface.IPackageItemService;

public class PackageItemService implements IPackageItemService {

	public IPackageItemManager packageItemManager = null;
	
	public PackageItemService(/*IPackageItemManager packageItemManager*/)
	{
		packageItemManager = (IPackageItemManager)ApplicationContextHolder.webApplicationContext.getBean("packageItemManager");
	}
	
	public void delete(Object[] object) {
		// TODO Auto-generated method stub
		packageItemManager.delete(object);
	}

	public void deleteById(String pkid) {
		// TODO Auto-generated method stub
		packageItemManager.deleteById(pkid);
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List list = packageItemManager.findAll();
		return list;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List list = packageItemManager.findByProperty(propertyName, value);
		return list;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		Object object = packageItemManager.getById(pkid);
		return object;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		Object object = packageItemManager.loadById(pkid);
		return object;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		Object saveobject = packageItemManager.save(object);
		return saveobject;
	}

	public void save(Object[] object) {
		// TODO Auto-generated method stub
		packageItemManager.save(object);
	}

	public void update(Object object) {
		// TODO Auto-generated method stub
		packageItemManager.update(object);
	}

	public void update(Object[] object) {
		// TODO Auto-generated method stub
		packageItemManager.update(object);
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
		List list = packageItemManager.findbyExample(exampleentity);
		return list;
	}
	
	public Collection findByPackageId(Long packageId)
	{
		Collection collection = null;
		List packageItems = findByProperty("packageId",packageId);
		
		Iterator itr = packageItems.iterator();
		while (itr.hasNext()) 
		{
			PackageItem packageItem = (PackageItem) itr.next();
			collection.add(packageItem);
		}
		
		return collection;
	}
}
