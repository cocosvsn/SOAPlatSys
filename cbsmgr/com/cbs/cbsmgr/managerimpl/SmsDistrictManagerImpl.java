package com.cbs.cbsmgr.managerimpl;

import java.util.Date;
import java.util.List;

import com.soaplat.sysmgr.common.IGetPK;
import com.cbs.cbsmgr.bean.SmsDistrict;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.ISmsDistrictManager;

public class SmsDistrictManagerImpl implements ISmsDistrictManager {

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
		SmsDistrict district = (SmsDistrict)baseDAO.getById(SmsDistrict.class, pkid);
		if(district != null)
		{
			baseDAO.delete(district);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List districtList = baseDAO.findAll("SmsDistrict", "districtId");
		return districtList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List districtList = baseDAO.findByProperty("SmsDistrict", propertyName, value);
		return districtList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		SmsDistrict district = (SmsDistrict)baseDAO.getById(SmsDistrict.class, pkid);
		return district;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		SmsDistrict district = (SmsDistrict)baseDAO.loadById(SmsDistrict.class, pkid);
		return district;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		SmsDistrict district = new SmsDistrict();
		district = (SmsDistrict)object;
		
		String strCurMaxPK = baseDAO.getMaxPropertyValue("SmsDistrict", "districtId");
		String strMaxPK = getcbspk.GetTablePK("SmsDistrict", strCurMaxPK);
		district.setDistrictId(strMaxPK);
		baseDAO.save(district);
		System.out.println("±£´æ³É¹¦£¬DistrictId£º" + strMaxPK.toString());
		return baseDAO.getById(SmsDistrict.class, strMaxPK);
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

	

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public void setGetcbspk(IGetPK getcbspk) {
		this.getcbspk = getcbspk;
	}

	public List findbyExample(Object exampleentity) {
		List list = baseDAO.findbyExample(exampleentity);
		return list;
	}
}
