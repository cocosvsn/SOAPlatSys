package com.cbs.cbsmgr.managerimpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.cbs.cbsmgr.bean.Account;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.IAccountManager;

public class AccountManagerImpl implements IAccountManager {

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
		Account account = (Account)baseDAO.getById(Account.class, Long.valueOf(pkid));
		if(account != null)
		{
			baseDAO.delete(account);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List accountList = baseDAO.findAll("Account", "accountId");
		return accountList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List accountList = baseDAO.findByProperty("Account", propertyName, value);
		return accountList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		Account account = (Account)baseDAO.getById(Account.class, Long.valueOf(pkid));
		return account;
	}
	
//	public Object getById(Long pkid) {
//		// TODO Auto-generated method stub
//		Account account = (Account)baseDAO.getById(Account.class, pkid);
//		return account;
//	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		Account account = (Account)baseDAO.loadById(Account.class, Long.valueOf(pkid));
		return account;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		Account account = new Account();
		account = (Account)object;
		
		// 判断字段内容
		account.setCreateDate(new Date());

		if(account.getBbf() == null)
		{
			account.setBbf((double)0.0);
		}

		try
		{
			// 根据CreateDate和InvoicePeriodId，计算NextInvoiceDay
			int iInvoicePeriod;
			String strFirstDate;
			Date dNextInvoiceDay = new Date();
			GregorianCalendar gcFirstDate = new GregorianCalendar();
			dNextInvoiceDay = account.getCreateDate();
			iInvoicePeriod = Integer.parseInt(account.getInvoicePeriodId().toString());
			System.out.println("创建日期：" + dNextInvoiceDay.toString());
			
			// 月份计算
			gcFirstDate.setTime(dNextInvoiceDay);
			gcFirstDate.add(GregorianCalendar.MONTH, iInvoicePeriod);	// 在月份上 + iInvoicePeriod
			dNextInvoiceDay = gcFirstDate.getTime();
			
			// 转当前日期 --> String strFirstDate
			SimpleDateFormat sdfFirstDate = new SimpleDateFormat("yyyy-MM-01");
		    strFirstDate = sdfFirstDate.format(dNextInvoiceDay);
		    
			// String strFirstDate --> Date dNextInvoiceDay
		    SimpleDateFormat sdfNextInvoiceDay = new SimpleDateFormat("yyyy-MM-dd");
		    dNextInvoiceDay = sdfNextInvoiceDay.parse(strFirstDate);
		    
		    System.out.println("下一出帐日：" + dNextInvoiceDay.toString());

			account.setNextInvoiceDay(dNextInvoiceDay);
		}
		catch(Exception ex)
		{
			System.out.println("异常：" + ex.getMessage());
			return null;
		}
		finally
		{
		}
		
		// 保存到数据库
		baseDAO.save(account);
		Long LMaxPK = account.getAccountId();
		System.out.println("保存成功，Account_Id:" + LMaxPK.toString());
		return baseDAO.getById(Account.class, LMaxPK);
		
	}

	public void save(Object[] object) {
		// TODO Auto-generated method stub
		if (object.length > 0)
		{
			for(int i = 0; i < object.length; i++)
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
