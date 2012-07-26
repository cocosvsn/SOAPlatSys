package com.cbs.cbsmgr.managerimpl;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import com.soaplat.sysmgr.common.IGetPK;
import com.cbs.cbsmgr.bean.VodPackage;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.IVodPackageManager;

import flex.messaging.io.ArrayCollection;

public class VodPackageManagerImpl implements IVodPackageManager {

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
		VodPackage vodPackage = (VodPackage)baseDAO.getById(VodPackage.class, pkid);
		if(vodPackage != null)
		{
			baseDAO.delete(vodPackage);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List vodPackageList = baseDAO.findAll("VodPackage", "vodPackageId");
		return vodPackageList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List vodPackageList = baseDAO.findByProperty("VodPackage", propertyName, value);
		return vodPackageList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		VodPackage vodPackage = (VodPackage)baseDAO.getById(VodPackage.class, pkid);
		return vodPackage;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		VodPackage vodPackage = (VodPackage)baseDAO.loadById(VodPackage.class, pkid);
		return vodPackage;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		VodPackage vodPackage = new VodPackage();
		vodPackage = (VodPackage)object;
		vodPackage.setInputtime(new Date());
		String strCurMaxPK = baseDAO.getMaxPropertyValue("VodPackage", "vodPackageId");
		String strMaxPK = getcbspk.GetTablePK("VodPackage", strCurMaxPK);
		vodPackage.setVodPackageId(strMaxPK);
		baseDAO.save(vodPackage);
		//
		return baseDAO.getById(VodPackage.class, strMaxPK);
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
	public List findpackageinfobypackageidlist(ArrayCollection packageidlist) {
		String vodPackageId;
		List packageinfo =  new ArrayList();
		List subpackageinfo =  new ArrayList();
		if(packageidlist.size()>0)
		{
			for(int i=0;i<packageidlist.size();i++)
			{
				subpackageinfo.clear();
				vodPackageId = (String)packageidlist.get(i);
				subpackageinfo = baseDAO.findByProperty("VodPackage", "vodPackageId", vodPackageId);
				packageinfo.addAll(subpackageinfo);				
			}
		}
		else
		{
			return packageinfo;
		}
		return packageinfo;
	}
}
