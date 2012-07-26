package com.cbs.cbsmgr.serviceimpl;

import java.util.List;

import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.cbs.cbsmgr.manageriface.IFinancialTransactionManager;
import com.cbs.cbsmgr.serviceiface.IFinancialTransactionService;

public class FinancialTransactionService implements IFinancialTransactionService {

	public IFinancialTransactionManager financialTransactionManager = null;
	
	public FinancialTransactionService(/*IFinancialTransactionManager financialTransactionManager*/)
	{
		financialTransactionManager = (IFinancialTransactionManager)ApplicationContextHolder.webApplicationContext.getBean("financialTransactionManager");
	}
	
	public void delete(Object[] object) {
		// TODO Auto-generated method stub
		financialTransactionManager.delete(object);
	}

	public void deleteById(String pkid) {
		// TODO Auto-generated method stub
		financialTransactionManager.deleteById(pkid);
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List list = financialTransactionManager.findAll();
		return list;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List list = financialTransactionManager.findByProperty(propertyName, value);
		return list;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		Object object = financialTransactionManager.getById(pkid);
		return object;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		Object object = financialTransactionManager.loadById(pkid);
		return object;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		Object saveobject = financialTransactionManager.save(object);
		return saveobject;
	}

	public void save(Object[] object) {
		// TODO Auto-generated method stub
		financialTransactionManager.save(object);
	}

	public void update(Object object) {
		// TODO Auto-generated method stub
		financialTransactionManager.update(object);
	}

	public void update(Object[] object) {
		// TODO Auto-generated method stub
		financialTransactionManager.update(object);
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
		List list = financialTransactionManager.findbyExample(exampleentity);
		return list;
	}
	
	// 得到accountid的未入帐单的借的财务交易列表
	public List getDebitNotInInvoiceFts(Long accountId)
	{
		List list = financialTransactionManager.getDebitNotInInvoiceFts(accountId);
		return list;
	}
	
	// 得到accountid的未入帐单的贷的财务交易列表
	public List getCreditNotInInvoiceFts(Long accountId)
	{
		List list = financialTransactionManager.getCreditNotInInvoiceFts(accountId);
		return list;
	}
	
	// 得到accountid的未入帐单的所有的财务交易列表
	public List getAllNotInInvoiceFts(Long accountId)
	{
		List list = financialTransactionManager.getAllNotInInvoiceFts(accountId);
		return list;
	}
}
