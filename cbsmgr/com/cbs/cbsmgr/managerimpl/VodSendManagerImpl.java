package com.cbs.cbsmgr.managerimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.sysmgr.common.IGetPK;
import com.cbs.cbsmgr.bean.VodSend;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.IVodSendManager;

public class VodSendManagerImpl implements IVodSendManager {

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
		VodSend vodSend = (VodSend)baseDAO.getById(VodSend.class, pkid);
		if(vodSend != null)
		{
			baseDAO.delete(vodSend);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List vodSendList = baseDAO.findAll("VodSend", "vodSendId");
		return vodSendList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List vodSendList = baseDAO.findByProperty("VodSend", propertyName, value);
		return vodSendList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		VodSend vodSend = (VodSend)baseDAO.getById(VodSend.class, pkid);
		return vodSend;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		VodSend vodSend = (VodSend)baseDAO.loadById(VodSend.class, pkid);
		return vodSend;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		VodSend vodSend = new VodSend();
		vodSend = (VodSend)object;
		vodSend.setCreateDate(new Date());
		String strCurMaxPK = baseDAO.getMaxPropertyValue("VodSend", "vodSendId");
		String strMaxPK = getcbspk.GetTablePK("VodSend", strCurMaxPK);
		vodSend.setVodSendId(strMaxPK);
		baseDAO.save(vodSend);
		//
		return baseDAO.getById(VodSend.class, strMaxPK);
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
	public int updateVodSendByVodSendId(String vodSendId)
	{
		Map map = new HashMap(0);
		map.put("vodSendId", vodSendId);
		int ret = baseDAO.updateByNamedQuery("update_VodSendByVodSendId",map);
		return ret;
	}
}
