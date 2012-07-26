package com.cbs.cbsmgr.serviceimpl;

import java.util.List;

import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.cbs.cbsmgr.manageriface.IInvoiceManager;
import com.cbs.cbsmgr.serviceiface.IInvoiceService;

public class InvoiceService implements IInvoiceService {

	public IInvoiceManager invoiceManager = null;
	
	public InvoiceService(/*IInvoiceManager invoiceManager*/)
	{
		invoiceManager = (IInvoiceManager)ApplicationContextHolder.webApplicationContext.getBean("invoiceManager");
	}
	
	public void delete(Object[] object) {
		// TODO Auto-generated method stub
		invoiceManager.delete(object);
	}

	public void deleteById(String pkid) {
		// TODO Auto-generated method stub
		invoiceManager.deleteById(pkid);
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List list = invoiceManager.findAll();
		return list;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List list = invoiceManager.findByProperty(propertyName, value);
		return list;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		Object object = invoiceManager.getById(pkid);
		return object;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		Object object = invoiceManager.loadById(pkid);
		return object;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		Object saveobject = invoiceManager.save(object);
		return saveobject;
	}

	public void save(Object[] object) {
		// TODO Auto-generated method stub
		invoiceManager.save(object);
	}

	public void update(Object object) {
		// TODO Auto-generated method stub
		invoiceManager.update(object);
	}

	public void update(Object[] object) {
		// TODO Auto-generated method stub
		invoiceManager.update(object);
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
		List list = invoiceManager.findbyExample(exampleentity);
		return list;
	}
}
