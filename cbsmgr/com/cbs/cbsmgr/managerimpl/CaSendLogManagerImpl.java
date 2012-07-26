package com.cbs.cbsmgr.managerimpl;

import java.util.List;

import com.soaplat.sysmgr.common.IGetPK;
import com.cbs.cbsmgr.bean.CaSendLog;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.ICaSendLogManager;

public class CaSendLogManagerImpl implements ICaSendLogManager {

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
		CaSendLog caSendLog = (CaSendLog)baseDAO.getById(CaSendLog.class, Long.valueOf(pkid));
		if(caSendLog != null)
		{
			baseDAO.delete(caSendLog);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List caSendLogList = baseDAO.findAll("CaSendLog", "caSendLogId");
		return caSendLogList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List caSendLogList = baseDAO.findByProperty("CaSendLog", propertyName, value);
		return caSendLogList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		CaSendLog caSendLog = (CaSendLog)baseDAO.getById(CaSendLog.class, Long.valueOf(pkid));
		return caSendLog;
	}
	
//	public Object getById(Long pkid) {
//		// TODO Auto-generated method stub
//		CaSendLog caSendLog = (CaSendLog)baseDAO.getById(CaSendLog.class, pkid);
//		return caSendLog;
//	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		CaSendLog caSendLog = (CaSendLog)baseDAO.loadById(CaSendLog.class, Long.valueOf(pkid));
		return caSendLog;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		CaSendLog caSendLog = (CaSendLog)object;

		String strCurMaxPK = baseDAO.getMaxPropertyValue("CaSendLog", "caSendLogId");
		String strMaxPK = getcbspk.GetTablePK("CaSendLog", strCurMaxPK);

		baseDAO.save(caSendLog);

		System.out.println("±£´æ³É¹¦£¬CaSendLogId£º" + caSendLog.getCaSendLogId().toString());
		return caSendLog;
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
