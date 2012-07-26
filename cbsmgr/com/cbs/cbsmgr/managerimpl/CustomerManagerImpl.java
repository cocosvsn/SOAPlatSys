package com.cbs.cbsmgr.managerimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.sysmgr.common.IGetPK;
import com.cbs.cbsmgr.bean.Customer;
import com.cbs.cbsmgr.dto.CustomerDisplayDTO;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.ICustomerManager;

public class CustomerManagerImpl implements ICustomerManager {

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
		Customer customer = (Customer)baseDAO.getById(Customer.class, Long.valueOf(pkid));
		if(customer != null)
		{
			baseDAO.delete(customer);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List customerList = baseDAO.findAll("Customer", "customerId");
		return customerList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List customerList = baseDAO.findByProperty("Customer", propertyName, value);
		return customerList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		Customer customer = (Customer)baseDAO.getById(Customer.class, Long.valueOf(pkid));
		return customer;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		Customer customer = (Customer)baseDAO.loadById(Customer.class, Long.valueOf(pkid));
		return customer;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		Customer customer = new Customer();
		customer = (Customer)object;
		
		customer.setCaptureDate(new Date());
//		if(customer.getLoyalty() == null)
//		{
//			customer.setLoyalty((long)0);
//		}
//		if(customer.getPostalAddressId() == null)
//		{
//			customer.setPostalAddressId((long)0);
//		}
//		if(customer.getDealerTag() == null)
//		{
//			customer.setDealerTag("N");
//		}
//		if(customer.getDepotTag() == null)
//		{
//			customer.setDepotTag("N");
//		}
//		if(customer.getProductProviderTag() == null)
//		{
//			customer.setProductProviderTag("N");
//		}
		
		if(customer.getCustomerTypeId() == null
			|| customer.getCustomerStatusId() == null
//			|| customer.getLoyalty() == null
			|| customer.getDefaultAddressId() == null
//			|| customer.getPostalAddressId() == null
//			|| customer.getDealerTag() == null
//			|| customer.getDepotTag() == null
//			|| customer.getProductProviderTag() == null
			)
		{
			System.out.println("保存失败，输入数据有空值。");
			return null;
		}
		else
		{
			baseDAO.save(customer);
			Long LCurPk = customer.getCustomerId();
			System.out.println("保存成功，CustomerId：" + LCurPk.toString());
			return baseDAO.getById(Customer.class, LCurPk);
		}
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

	

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public void setGetcbspk(IGetPK getcbspk) {
		this.getcbspk = getcbspk;
	}

	public List findbyExample(Object exampleentity) {
		List list = baseDAO.findbyExample(exampleentity);
		return list;
	}
	
	public List getCustomerDisplayDTOByCustomerId(Long customerId)
	{
		Map map = new HashMap();
		map.put("customerId", customerId);
		List ftslist = baseDAO.queryByNamedQuery("select_customerByCustomerId", map);
		return ftslist;
	}
}
