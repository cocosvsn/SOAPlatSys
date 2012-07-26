package com.cbs.cbsmgr.managerimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.sysmgr.common.IGetPK;
import com.cbs.cbsmgr.bean.PackageItem;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.IPackageItemManager;

public class PackageItemManagerImpl implements IPackageItemManager {

	IBaseDAO baseDAO;
	IGetPK getcbspk;
	
	public void delete(Object[] object) {
		// TODO Auto-generated method stub
		if(object.length > 0)
		{
			for(int i = 0; i < object.length; i++)
			{
				baseDAO.delete(object[i]);
			}
		}
		else
		{
			return;
		}
	}

	public void deleteById(String pkid) {
		// TODO Auto-generated method stub
		PackageItem packageItem = (PackageItem)baseDAO.getById(PackageItem.class, Long.valueOf(pkid));
		if(packageItem != null)
		{
			baseDAO.delete(packageItem);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List packageItemList = baseDAO.findAll("PackageItem", "packageItemId");
		return packageItemList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List packageItemList = baseDAO.findByProperty("PackageItem", propertyName, value);
		return packageItemList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		PackageItem packageItem = (PackageItem)baseDAO.getById(PackageItem.class, Long.valueOf(pkid));
		return packageItem;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		PackageItem packageItem = (PackageItem)baseDAO.loadById(PackageItem.class, Long.valueOf(pkid));
		return packageItem;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		PackageItem packageItem = new PackageItem();
		packageItem = (PackageItem)object;
		baseDAO.save(packageItem);
		Long LCurPk = packageItem.getPackageItemId();
		System.out.println(LCurPk.toString());
		return baseDAO.getById(PackageItem.class, LCurPk);
	}

	public void save(Object[] object) {
		// TODO Auto-generated method stub
		if (object.length>0)
		{
			for(int i=0;i<object.length;i++)
			{
				this.save(object[i]);
			}
		}
	}

	public void update(Object object) {
		// TODO Auto-generated method stub
		baseDAO.update(object);
	}

	public void update(Object[] object) {
		// TODO Auto-generated method stub
		if (object.length>0)
		{
			for(int i=0;i<object.length;i++)
			{
				this.update(object[i]);
			}
		}
	}

	public void setGetcbspk(IGetPK getcbspk) 
	{
		this.getcbspk = getcbspk;
	}
	
	public void setBaseDAO(IBaseDAO baseDAO) 
	{
		this.baseDAO = baseDAO;
	}

	public List findbyExample(Object exampleentity) {
		List list = baseDAO.findbyExample(exampleentity);
		return list;
	}
	
	public List getPackageItemsByPackageId(Long packageId)
	{
		Map map = new HashMap();
		map.put("packageId", packageId);
		List list = baseDAO.queryByNamedQuery("select_packageItemsByPackageId", map);
		return list;
	}
}
