package com.cbs.cbsmgr.managerimpl;

import java.util.List;

import com.soaplat.sysmgr.common.IGetPK;
import com.cbs.cbsmgr.bean.History;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.IHistoryManager;

public class HistoryManagerImpl implements IHistoryManager {

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
		History history = (History)baseDAO.getById(History.class, Long.valueOf(pkid));
		if(history != null)
		{
			baseDAO.delete(history);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List historyList = baseDAO.findAll("History", "historyId");
		return historyList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List historyList = baseDAO.findByProperty("History", propertyName, value);
		return historyList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		History history = (History)baseDAO.getById(History.class, Long.valueOf(pkid));
		return history;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		History history = (History)baseDAO.loadById(History.class, Long.valueOf(pkid));
		return history;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		History history = new History();
		history = (History)object;
		baseDAO.save(history);
		Long LCurPk = history.getHistoryId();
		return baseDAO.getById(History.class, LCurPk);
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
