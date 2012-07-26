package com.cbs.cbsmgr.managerimpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cbs.cbsmgr.bean.Product;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.IProductManager;

public class ProductManagerImpl implements IProductManager {

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
		Product product = (Product)baseDAO.getById(Product.class, Long.valueOf(pkid));
		if(product != null)
		{
			baseDAO.delete(product);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List productList = baseDAO.findAll("Product", "productId");
		return productList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List productList = baseDAO.findByProperty("Product", propertyName, value);
		return productList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		Product product = (Product)baseDAO.getById(Product.class, Long.valueOf(pkid));
		return product;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		Product product = (Product)baseDAO.loadById(Product.class, Long.valueOf(pkid));
		return product;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		Product product = new Product();
		product = (Product)object;
		baseDAO.save(product);
		Long LCurPk = product.getProductId();
		return baseDAO.getById(Product.class, LCurPk);
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
	
	// 得到Product，根据accountId productCategoryId
	public List getProductByAccountIdAndProductCategoryId(Long accountId, Long productCategoryId)
	{
		Map map = new HashMap();
		map.put("accountId", accountId);
		map.put("productCategoryId", productCategoryId);
		List products = baseDAO.queryByNamedQuery("select_productbyaccountidandproductcategoryid", map);

		return products;
	}
	
	// 得到正常状态 的product
	public List getNormalStatusProductsByCustomerId(Long customerId)
	{
		Map map = new HashMap();
		map.put("customerId", customerId);
		List products = baseDAO.queryByNamedQuery("select_normalStatusProductsByCustomerId", map);

		return products;
	}
	
	// 得到客户id下所有的硬件product
	public List getHardwareProductsByCustomerId(Long customerId)
	{
		Map map = new HashMap();
		map.put("customerId", customerId);
		List products = baseDAO.queryByNamedQuery("select_hardwareProductsByCustomerId", map);

		return products;
	}
	
	// 得到正常状态 的product
	public List getNormalProductsByCustomerId(Long customerId)
	{
		Map map = new HashMap();
		map.put("customerId", customerId);
		List list = baseDAO.queryByNamedQuery("select_normalProductsByCustomerId", map);
		return list;
	}
	
	// 20091224 15:36
	// 分页查询Product表中记录，条件：正常状态、周期性产品BT00000003、计费有效期=date，排序：客户，创建日期
	public List getProductsForGeneratingPeriodFee(Date date, int firstResult, int maxResults)
	{
		try
		{
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");   
		    String strDate = sdf2.format(date);   
		    
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
			Date dB = format.parse(strDate + " 00:00:00");
			Date dE = format.parse(strDate + " 23:59:59");
			java.sql.Timestamp dateb = new java.sql.Timestamp(dB.getTime());
			java.sql.Timestamp datee = new java.sql.Timestamp(dE.getTime());
			
			Map map = new HashMap();
			map.put("billingTypeId", "BT00000003");
			map.put("dateb", dateb);
			map.put("datee", datee);
			List list = baseDAO.queryByNamedQueryWithCountLimited(
					"select_ProductsForGeneratingPeriodFee", 
					map,
					firstResult, 
					maxResults
					);
			return list;
		}
		catch(Exception ex)
		{
			ex.getMessage();
			return null;
		}
	}
	
	// 20091225 14:55
	// 根据username，查询Hardware、Product，得到Product
	public List getProductsByUsername(String userName)
	{
		Map map = new HashMap();
		map.put("userName", userName);
		List list = baseDAO.queryByNamedQuery(
				"select_ProductsByUsername", 
				map
				);
		return list;
	}
}
