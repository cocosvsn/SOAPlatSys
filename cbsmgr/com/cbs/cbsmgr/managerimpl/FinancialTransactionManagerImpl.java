package com.cbs.cbsmgr.managerimpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.sysmgr.common.IGetPK;
import com.cbs.cbsmgr.bean.FinancialTransaction;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.IFinancialTransactionManager;

public class FinancialTransactionManagerImpl implements IFinancialTransactionManager {

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
		FinancialTransaction financialTransaction = (FinancialTransaction)baseDAO.getById(FinancialTransaction.class, Long.valueOf(pkid));
		if(financialTransaction != null)
		{
			baseDAO.delete(financialTransaction);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List financialTransactionList = baseDAO.findAll("FinancialTransaction", "ftId");
		return financialTransactionList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List financialTransactionList = baseDAO.findByProperty("FinancialTransaction", propertyName, value);
		return financialTransactionList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		FinancialTransaction financialTransaction = (FinancialTransaction)baseDAO.getById(FinancialTransaction.class, Long.valueOf(pkid));
		return financialTransaction;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		FinancialTransaction financialTransaction = (FinancialTransaction)baseDAO.loadById(FinancialTransaction.class, Long.valueOf(pkid));
		return financialTransaction;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		FinancialTransaction financialTransaction = new FinancialTransaction();
		financialTransaction = (FinancialTransaction)object;
		baseDAO.save(financialTransaction);
		Long LCurPk = financialTransaction.getFtId();
		return baseDAO.getById(FinancialTransaction.class, LCurPk);
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
	
	// 得到accountid的未入帐单的借的财务交易列表
	public List getDebitNotInInvoiceFts(Long accountId)
	{
		Map map = new HashMap();
		map.put("accountId", accountId);
		List ftslist = baseDAO.queryByNamedQuery("select_debitnotininvoicefts", map);
		return ftslist;
	}
	
	// 得到accountid的未入帐单的贷的财务交易列表
	public List getCreditNotInInvoiceFts(Long accountId)
	{
		Map map = new HashMap();
		map.put("accountId", accountId);
		List ftslist = baseDAO.queryByNamedQuery("select_creditnotininvoicefts", map);
		return ftslist;
	}
	
	// 得到accountid的未入帐单的所有的财务交易列表
	public List getAllNotInInvoiceFts(Long accountId)
	{
		Map map = new HashMap();
		map.put("accountId", accountId);
		List ftslist = baseDAO.queryByNamedQuery("select_allnotininvoicefts", map);
		return ftslist;
	}
	
	// 查询未入账单的财务交易 Edited by Andy 20091223 17:10
	public List getNotInInvoiceFts(int firstResult, int maxResults)
	{
		Map map = new HashMap();
		List list = baseDAO.queryByNamedQueryWithCountLimited(
				"select_NotInInvoiceFts", 
				map, 
				firstResult, 
				maxResults
				);
		return list;
	}
	
	// 20100322 20:45
	// 财务报表
	public List reportFTs(
			Long customerId,			
			String createDateFrom,			// 格式：yyyy-MM-dd
			String createDateTo				// 格式：yyyy-MM-dd
			)
	{
		// 返回：
		// List list
		//		(FinancialTransaction)list.get(0)
		//		(Address)list.get(1)
		
		Map map = new HashMap();
		List list = null;
		
		try 
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date dB = format.parse(createDateFrom + " 00:00:00");
			Date dE = format.parse(createDateTo + " 23:59:59");
			java.sql.Timestamp dateFrom = new java.sql.Timestamp(dB.getTime());
			java.sql.Timestamp dateTo = new java.sql.Timestamp(dE.getTime());
			if (customerId > 0) 
			{
				map.put("customerId", customerId);
				map.put("dateFrom", dateFrom);
				map.put("dateTo", dateTo);
				list = baseDAO.queryByNamedQuery("reportFTsWithCustomerid", map);
			} 
			else 
			{
				map.put("dateFrom", dateFrom);
				map.put("dateTo", dateTo);
				list = baseDAO.queryByNamedQuery("reportFTsWithoutCustomerid", map);
			}
		}
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
		//		List list = baseDAO.queryByNamedQueryWithCountLimited(
//				"select_NotInInvoiceFts", 
//				map, 
//				firstResult, 
//				maxResults
//				);
		return list;
	}
}
