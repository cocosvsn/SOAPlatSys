package com.cbs.cbsmgr.managerimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.sysmgr.common.IGetPK;
import com.cbs.cbsmgr.bean.CbsChannelList;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.IChannelListManager;

public class ChannelListManagerImpl implements IChannelListManager {

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
		CbsChannelList ChannelList = (CbsChannelList)baseDAO.getById(CbsChannelList.class, pkid);
		if(ChannelList != null)
		{
			baseDAO.delete(ChannelList);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List ChannelListList = baseDAO.findAll("ChannelList", "channelid");
		return ChannelListList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List ChannelListList = baseDAO.findByProperty("ChannelList", propertyName, value);
		return ChannelListList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		CbsChannelList ChannelList = (CbsChannelList)baseDAO.getById(CbsChannelList.class, pkid);
		return ChannelList;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		CbsChannelList ChannelList = (CbsChannelList)baseDAO.loadById(CbsChannelList.class, pkid);
		return ChannelList;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		CbsChannelList ChannelList = new CbsChannelList();
		ChannelList = (CbsChannelList)object;
		String strCurMaxPK = baseDAO.getMaxPropertyValue("ChannelList", "channelid");
		String strMaxPK = getcbspk.GetTablePK("ChannelList", strCurMaxPK);
		ChannelList.setChannelid(strMaxPK);
		baseDAO.save(ChannelList);
		return baseDAO.getById(CbsChannelList.class, strMaxPK);
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
