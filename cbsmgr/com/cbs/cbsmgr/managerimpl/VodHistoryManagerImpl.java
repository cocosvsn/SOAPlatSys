package com.cbs.cbsmgr.managerimpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.sysmgr.common.IGetPK;
import com.cbs.cbsmgr.bean.VodHistory;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.IVodHistoryManager;

public class VodHistoryManagerImpl implements IVodHistoryManager {

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
		VodHistory vodHistory = (VodHistory)baseDAO.getById(VodHistory.class, pkid);
		if(vodHistory != null)
		{
			baseDAO.delete(vodHistory);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List vodHistoryList = baseDAO.findAll("VodHistory", "vodHistoryId");
		return vodHistoryList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List vodHistoryList = baseDAO.findByProperty("VodHistory", propertyName, value);
		return vodHistoryList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		VodHistory vodHistory = (VodHistory)baseDAO.getById(VodHistory.class, pkid);
		return vodHistory;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		VodHistory vodHistory = (VodHistory)baseDAO.loadById(VodHistory.class, pkid);
		return vodHistory;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		VodHistory vodHistory = new VodHistory();
		vodHistory = (VodHistory)object;
//		vodHistory.setCreateDate(new Date());
		String strCurMaxPK = baseDAO.getMaxPropertyValue("VodHistory", "vodHistoryId");
		String strMaxPK = getcbspk.GetTablePK("VodHistory", strCurMaxPK);
		vodHistory.setVodHistoryId(strMaxPK);
		baseDAO.save(vodHistory);
		//
		return baseDAO.getById(VodHistory.class, strMaxPK);
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
	
	// 根据customerId、dateFrom、dateTo，查询表VodHistory，得到点播流水列表
	public List getVodFlowDTOsByCustomerIdAndDate(Long customerId, Date dateFrom, Date dateTo)
	{
		// 把java.util.date 转换成 java.sql.date
		SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
		java.sql.Date jsDateFrom = java.sql.Date.valueOf(df.format(dateFrom));
		java.sql.Date jsDateTo = java.sql.Date.valueOf(df.format(dateTo));
		
		Map map = new HashMap();
		map.put("customerId", customerId);
		map.put("dateFrom", jsDateFrom);
		map.put("dateTo", jsDateTo);
		List products = baseDAO.queryByNamedQuery("select_vodHistoriesByCustomerIdAndDate", map);
		return products;
	}
	
	// 20100322 21:25
	// 点播历史报表
	public List reportVodHistories(
			Long customerId,				// 
			String vodDateFrom,				// 格式：yyyy-MM-dd
			String vodDateTo				// 格式：yyyy-MM-dd
			)
	{
		// 返回：
		// List<Object> list
		//		(VodHistory)Object[0]
		//		(Address)Object[1]
		//		(ProductCategory)Object[2]
		
		Map map = new HashMap();
		List list = null;
		
		try
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date dB = format.parse(vodDateFrom + " 00:00:00");
			Date dE = format.parse(vodDateTo + " 23:59:59");
			java.sql.Timestamp dateFrom = new java.sql.Timestamp(dB.getTime());
			java.sql.Timestamp dateTo = new java.sql.Timestamp(dE.getTime());
			if (customerId > 0) 
			{
				map.put("customerId", customerId);
				map.put("dateFrom", dateFrom);
				map.put("dateTo", dateTo);
				list = baseDAO.queryByNamedQuery("reportVodHistoriesWithCustomerid", map);
			}
			else 
			{
				map.put("dateFrom", dateFrom);
				map.put("dateTo", dateTo);
				list = baseDAO.queryByNamedQuery("reportVodHistoriesWithoutCustomerid", map);
			}
		}
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
		return list;
	}
}
