package com.cbs.cbsmgr.managerimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.sysmgr.common.IGetPK;
import com.cbs.cbsmgr.bean.ProductCategory;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.IProductCategoryManager;

public class ProductCategoryManagerImpl implements IProductCategoryManager {

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
		ProductCategory productCategory = (ProductCategory)baseDAO.getById(ProductCategory.class, Long.valueOf(pkid));
		if(productCategory != null)
		{
			baseDAO.delete(productCategory);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List productCategoryList = baseDAO.findAll("ProductCategory", "productCategoryId");
		return productCategoryList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List productCategoryList = baseDAO.findByProperty("ProductCategory", propertyName, value);
		return productCategoryList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		ProductCategory productCategory = (ProductCategory)baseDAO.getById(ProductCategory.class, Long.valueOf(pkid));
		return productCategory;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		ProductCategory productCategory = (ProductCategory)baseDAO.loadById(ProductCategory.class, Long.valueOf(pkid));
		return productCategory;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		ProductCategory productCategory = new ProductCategory();
		productCategory = (ProductCategory)object;
		baseDAO.save(productCategory);
		Long LCurPk = productCategory.getProductCategoryId();
		return baseDAO.getById(ProductCategory.class, LCurPk);
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
	
	public List getProductCategoriesByDescription(String description)
	{
		List productCategories = null;
		if(description.equalsIgnoreCase(""))
		{
			Map map = new HashMap();
			productCategories = baseDAO.queryByNamedQuery("select_allProductCategories", map);
		}
		else
		{
			description = "%" + description + "%";
			Map map = new HashMap();
			map.put("description", description);
			productCategories = baseDAO.queryByNamedQuery("select_productCategoriesByDescription", map);
		}
		return productCategories;
	}
	
	// 根据文件ID查询得到产品
	public List getProductCategoriesByProgfileid(String progfileid)
	{
		Map map = new HashMap();
		map.put("progfileid", progfileid);
		List list = baseDAO.queryByNamedQuery("select_ProductCategoriesByProgfileid", map);
		return list;
	}
}
