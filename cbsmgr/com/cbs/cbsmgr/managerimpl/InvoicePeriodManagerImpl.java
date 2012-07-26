package com.cbs.cbsmgr.managerimpl;

import java.util.List;

import com.soaplat.sysmgr.common.IGetPK;
import com.cbs.cbsmgr.bean.InvoicePeriod;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.IInvoicePeriodManager;

public class InvoicePeriodManagerImpl implements IInvoicePeriodManager {

	IBaseDAO baseDAO;
	//IBaseDAO baseDAO;
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
		InvoicePeriod invoicePeriod = (InvoicePeriod)baseDAO.getById(InvoicePeriod.class, Long.valueOf(pkid));
		if(invoicePeriod != null)
		{
			baseDAO.delete(invoicePeriod);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List invoicePeriodList = baseDAO.findAll("InvoicePeriod", "invoicePeriodId");
		return invoicePeriodList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List invoicePeriodList = baseDAO.findByProperty("InvoicePeriod", propertyName, value);
		return invoicePeriodList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		InvoicePeriod invoicePeriod = (InvoicePeriod)baseDAO.getById(InvoicePeriod.class, Long.valueOf(pkid));
		return invoicePeriod;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		InvoicePeriod invoicePeriod = (InvoicePeriod)baseDAO.loadById(InvoicePeriod.class, Long.valueOf(pkid));
		return invoicePeriod;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		InvoicePeriod invoicePeriod = new InvoicePeriod();
		invoicePeriod = (InvoicePeriod)object;
		baseDAO.save(invoicePeriod);
		Long LCurPk = invoicePeriod.getInvoicePeriodId();
		//
		return baseDAO.getById(InvoicePeriod.class, LCurPk);
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
	
	public void setGetcbspk(IGetPK getcbspk) {
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
