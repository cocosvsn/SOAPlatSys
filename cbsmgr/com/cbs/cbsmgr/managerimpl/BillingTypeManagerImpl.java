package com.cbs.cbsmgr.managerimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.sysmgr.common.IGetPK;
import com.cbs.cbsmgr.bean.BillingType;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.IBillingTypeManager;

public class BillingTypeManagerImpl implements IBillingTypeManager {

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
		BillingType billingType = (BillingType)baseDAO.getById(BillingType.class, pkid);
		if(billingType != null)
		{
			baseDAO.delete(billingType);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List billingTypeList = baseDAO.findAll("BillingType", "billingTypeId");
		return billingTypeList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List billingTypeList = baseDAO.findByProperty("BillingType", propertyName, value);
		return billingTypeList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		BillingType billingType = (BillingType)baseDAO.getById(BillingType.class, pkid);
		return billingType;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		BillingType billingType = (BillingType)baseDAO.loadById(BillingType.class, pkid);
		return billingType;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		BillingType billingType = new BillingType();
		billingType = (BillingType)object;
		String strCurMaxPK = baseDAO.getMaxPropertyValue("BillingType", "billingTypeId");
		String strMaxPK = getcbspk.GetTablePK("BillingType", strCurMaxPK);
		billingType.setBillingTypeId(strMaxPK);
		baseDAO.save(billingType);
		return baseDAO.getById(BillingType.class, strMaxPK);
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
