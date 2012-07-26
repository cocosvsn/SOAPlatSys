package com.cbs.cbsmgr.serviceimpl;

import java.util.Date;
import java.util.List;

import com.cbs.cbsmgr.bean.Account;
import com.cbs.cbsmgr.bean.Customer;
import com.soaplat.sysmgr.common.ApplicationContextHolder;
import com.cbs.cbsmgr.manageriface.IAccountManager;
import com.cbs.cbsmgr.serviceiface.IAccountService;
import com.cbs.cbsmgr.dto.AccountDTO;

public class AccountService implements IAccountService {

	public IAccountManager accountManager = null;
	
	public AccountService(/*IAccountManager accountManager*/)
	{
		accountManager = (IAccountManager)ApplicationContextHolder.webApplicationContext.getBean("accountManager");
	}
	
	public void delete(Object[] object) {
		// TODO Auto-generated method stub
		accountManager.delete(object);
	}

	public void deleteById(String pkid) {
		// TODO Auto-generated method stub
		accountManager.deleteById(pkid);
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List list = accountManager.findAll();
		return list;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List list = accountManager.findByProperty(propertyName, value);
		return list;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		Object object = accountManager.getById(pkid);
		return object;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		Object object = accountManager.loadById(pkid);
		return object;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		Object saveobject = accountManager.save(object);
		return saveobject;
	}

	public void save(Object[] object) {
		// TODO Auto-generated method stub
		accountManager.save(object);
	}

	public void update(Object object) {
		// TODO Auto-generated method stub
		accountManager.update(object);
	}

	public void update(Object[] object) {
		// TODO Auto-generated method stub
		accountManager.update(object);
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.serviceiface.IBaseService#deleteByIDRetAll(java.lang.String)
	 */
	public List deleteByIDRetAll(String pkid) {
		this.deleteById(pkid);
		List list=this.findAll();
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
		List list = accountManager.findbyExample(exampleentity);
		return list;
	}
	
	public Account createAccount(Long customerId, AccountDTO accountDTO,
            Long accountType)/* throws CBSException*/ 
	{
		// �ж��������Ϸ���
		
		// �õ��ͻ���Ϣ����ʱ��ʹ��
		
		// �����ʻ�
		Account account = new Account(accountDTO);
		account.setCustomerId(customerId);
		account.setAccountTypeId(accountType);
		account.setCreateDate(new Date());
		account = (Account)accountManager.save(account);
		
		return account;
	}
}
