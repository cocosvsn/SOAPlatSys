package com.cbs.cbsmgr.managerimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.sysmgr.common.IGetPK;
import com.cbs.cbsmgr.bean.ProductPackage;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.IPackageManager;

public class ProductPackageManagerImpl implements IPackageManager {

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

	public void deleteById(String pkid)
	{
		// package 是保留字，不要使用，以后注意，少走弯路。
		ProductPackage pckg = (ProductPackage)baseDAO.getById(ProductPackage.class, Long.valueOf(pkid));
		if(pckg != null)
		{
			baseDAO.delete(pckg);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List packageList = baseDAO.findAll("ProductPackage", "packageId");
		return packageList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List packageList = baseDAO.findByProperty("ProductPackage", propertyName, value);
		return packageList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		ProductPackage pckg = (ProductPackage)baseDAO.getById(ProductPackage.class, Long.valueOf(pkid));
		return pckg;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		ProductPackage pckg = (ProductPackage)baseDAO.loadById(ProductPackage.class, Long.valueOf(pkid));
		return pckg;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		ProductPackage pckg = new ProductPackage();
		pckg = (ProductPackage)object;
		baseDAO.save(pckg);
		Long LCurPk = pckg.getPackageId();
		return baseDAO.getById(ProductPackage.class, LCurPk);
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
	
	public List getPackagesByDescription(String description)
	{
		List list = null;
		if(description.equalsIgnoreCase(""))
		{
			list = this.findAll();
		}
		else
		{
			description = "%" + description + "%";
			Map map = new HashMap();
			map.put("description", description);
			list = baseDAO.queryByNamedQuery("select_packagesByDescription", map);
		}
		return list;
	}
}
