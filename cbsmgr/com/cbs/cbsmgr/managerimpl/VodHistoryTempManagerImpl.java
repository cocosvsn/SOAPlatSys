package com.cbs.cbsmgr.managerimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.sysmgr.common.IGetPK;
import com.cbs.cbsmgr.bean.VodHistoryTemp;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.IVodHistoryTempManager;

public class VodHistoryTempManagerImpl implements IVodHistoryTempManager {

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
		VodHistoryTemp vodHistoryTemp = (VodHistoryTemp)baseDAO.getById(VodHistoryTemp.class, pkid);
		if(vodHistoryTemp != null)
		{
			baseDAO.delete(vodHistoryTemp);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List vodHistoryTempList = baseDAO.findAll("VodHistoryTemp", "vodHistoryTempId");
		return vodHistoryTempList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List vodHistoryTempList = baseDAO.findByProperty("VodHistoryTemp", propertyName, value);
		return vodHistoryTempList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		VodHistoryTemp vodHistoryTemp = (VodHistoryTemp)baseDAO.getById(VodHistoryTemp.class, pkid);
		return vodHistoryTemp;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		VodHistoryTemp vodHistoryTemp = (VodHistoryTemp)baseDAO.loadById(VodHistoryTemp.class, pkid);
		return vodHistoryTemp;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		VodHistoryTemp vodHistoryTemp = new VodHistoryTemp();
		vodHistoryTemp = (VodHistoryTemp)object;
//		vodHistoryTemp.setCreateDate(new Date());
//		String strCurMaxPK = baseDAO.getMaxPropertyValue("VodHistoryTemp", "vodHistoryTempId");
//		String strMaxPK = getcbspk.GetTablePK("VodHistoryTemp",strCurMaxPK);
//		vodHistoryTemp.setVodHistoryId(strMaxPK);
		baseDAO.save(vodHistoryTemp);
		//
		return vodHistoryTemp;
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
	
	// ��ݿͻ�Id��vodProductId����ѯ��vodHistoryTemp���õ����е㲥��¼��amount�ܺ�
	public Double getTotalAmountByCustomerIdAndVodProductId(Long customerId, String vodProductId)
	{
		Double totalAmount = 0.0;

		Map map = new HashMap();
		map.put("customerId", customerId);
		map.put("vodProductId", vodProductId);
		List list = baseDAO.queryByNamedQuery("select_vodHistoryTempsbycustomerIdandvodProductId", map);
		
		for(int i = 0; i < list.size(); i++)
		{
			VodHistoryTemp vodHistoryTemp = (VodHistoryTemp)list.get(i);
			Double amount = vodHistoryTemp.getAmount();
			
			totalAmount += amount;
		}
		
		return totalAmount;
	}
	
	// 20091224 13:12
	// ��ҳ��ѯVodHistory���м�¼�������δ����״̬�����򣺿ͻ����㲥����
	public List getNotDealedVodHistoryTempsWithCount(int firstResult, int maxResults)
	{
		Map map = new HashMap();
		List list = baseDAO.queryByNamedQueryWithCountLimited(
				"select_NotDealedVodHistoryTemps", 
				map, 
				firstResult, 
				maxResults
				);
		return list;
	}
}
