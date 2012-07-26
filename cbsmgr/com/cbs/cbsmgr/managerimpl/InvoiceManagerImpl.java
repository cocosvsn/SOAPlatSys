package com.cbs.cbsmgr.managerimpl;

import java.util.List;

import com.soaplat.sysmgr.common.IGetPK;
import com.cbs.cbsmgr.bean.Invoice;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.IInvoiceManager;

public class InvoiceManagerImpl implements IInvoiceManager {

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
		Invoice invoice = (Invoice)baseDAO.getById(Invoice.class, Long.valueOf(pkid));
		if(invoice != null)
		{
			baseDAO.delete(invoice);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List invoiceList = baseDAO.findAll("Invoice", "invoiceId");
		return invoiceList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List invoiceList = baseDAO.findByProperty("Invoice", propertyName, value);
		return invoiceList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		Invoice invoice = (Invoice)baseDAO.getById(Invoice.class, Long.valueOf(pkid));
		return invoice;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		Invoice invoice = (Invoice)baseDAO.loadById(Invoice.class, Long.valueOf(pkid));
		return invoice;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		Invoice invoice = new Invoice();
		invoice = (Invoice)object;
		baseDAO.save(invoice);
		Long LCurPk = invoice.getInvoiceId();
		return baseDAO.getById(Invoice.class, LCurPk);
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
