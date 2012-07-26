package com.cbs.cbsmgr.managerimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.sysmgr.common.IGetPK;
import com.cbs.cbsmgr.bean.ProductType;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.IProductTypeManager;

public class ProductTypeManagerImpl implements IProductTypeManager {

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
		ProductType productType = (ProductType)baseDAO.getById(ProductType.class, Long.valueOf(pkid));
		if(productType != null)
		{
			baseDAO.delete(productType);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List productTypeList = baseDAO.findAll("ProductType", "productTypeId");
		return productTypeList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List productTypeList = baseDAO.findByProperty("ProductType", propertyName, value);
		return productTypeList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		ProductType productType = (ProductType)baseDAO.getById(ProductType.class, Long.valueOf(pkid));
		return productType;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		ProductType productType = (ProductType)baseDAO.loadById(ProductType.class, Long.valueOf(pkid));
		return productType;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		ProductType productType = new ProductType();
		productType = (ProductType)object;

		baseDAO.save(productType);
		Long lMaxPk = productType.getProductTypeId();
		return baseDAO.getById(ProductType.class, lMaxPk);
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
}
