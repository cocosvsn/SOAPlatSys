package com.cbs.cbsmgr.managerimpl;

import java.util.List;

import com.soaplat.sysmgr.common.IGetPK;
import com.cbs.cbsmgr.bean.SmsAccount;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.ISmsAccountManager;

public class SmsAccountManagerImpl implements ISmsAccountManager {

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
		SmsAccount amsAccount = (SmsAccount)baseDAO.getById(SmsAccount.class, Long.valueOf(pkid));
		if(amsAccount != null)
		{
			baseDAO.delete(amsAccount);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List amsAccountList = baseDAO.findAll("SmsAccount", "smsAccountId");
		return amsAccountList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List amsAccountList = baseDAO.findByProperty("SmsAccount", propertyName, value);
		return amsAccountList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		SmsAccount amsAccount = (SmsAccount)baseDAO.getById(SmsAccount.class, Long.valueOf(pkid));
		return amsAccount;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		SmsAccount amsAccount = (SmsAccount)baseDAO.loadById(SmsAccount.class, Long.valueOf(pkid));
		return amsAccount;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		SmsAccount amsAccount = new SmsAccount();
		amsAccount = (SmsAccount)object;
		baseDAO.save(amsAccount);
		Long LCurPk = amsAccount.getSmsAccountId();
		//
		return baseDAO.getById(SmsAccount.class, LCurPk);
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
