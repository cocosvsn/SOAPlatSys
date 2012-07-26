package com.cbs.cbsmgr.managerimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.sysmgr.common.IGetPK;
import com.cbs.cbsmgr.bean.VodFlow;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.IVodFlowManager;

public class VodFlowManagerImpl implements IVodFlowManager {

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
		VodFlow vodFlow = (VodFlow)baseDAO.getById(VodFlow.class, pkid);
		if(vodFlow != null)
		{
			baseDAO.delete(vodFlow);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List vodFlowList = baseDAO.findAll("VodFlow", "vodFlowId");
		return vodFlowList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List vodFlowList = baseDAO.findByProperty("VodFlow", propertyName, value);
		return vodFlowList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		VodFlow vodFlow = (VodFlow)baseDAO.getById(VodFlow.class, pkid);
		return vodFlow;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		VodFlow vodFlow = (VodFlow)baseDAO.loadById(VodFlow.class, pkid);
		return vodFlow;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		VodFlow vodFlow = new VodFlow();
		vodFlow = (VodFlow)object;
		String strCurMaxPK = baseDAO.getMaxPropertyValue("VodFlow", "vodFlowId");
		String strMaxPK = getcbspk.GetTablePK("VodFlow", strCurMaxPK);
		vodFlow.setVodFlowId(strMaxPK);
		baseDAO.save(vodFlow);
		return vodFlow;
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

	// 查询所有需要分析的点播流水
	public List getNotDealedVodFlowsWithCount(int firstResult, int maxResults)
	{
		Map map = new HashMap();
//		map.put("customerId", customerId);
		List list = baseDAO.queryByNamedQueryWithCountLimited(
				"select_NotDealedVodFlows", 
				map, 
				firstResult, 
				maxResults);
		return list;
	}
}
