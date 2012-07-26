package com.cbs.cbsmgr.managerimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.sysmgr.common.IGetPK;
import com.cbs.cbsmgr.bean.Hardware;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.IHardwareManager;

public class HardwareManagerImpl implements IHardwareManager {

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
		Hardware hardware = (Hardware)baseDAO.getById(Hardware.class, pkid);
		if(hardware != null)
		{
			baseDAO.delete(hardware);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List hardwareList = baseDAO.findAll("Hardware", "hardwareId");
		return hardwareList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List hardwareList = baseDAO.findByProperty("Hardware", propertyName, value);
		return hardwareList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		Hardware hardware = (Hardware)baseDAO.getById(Hardware.class, pkid);
		return hardware;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		Hardware hardware = (Hardware)baseDAO.loadById(Hardware.class, pkid);
		return hardware;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		Hardware hardware = new Hardware();
		hardware = (Hardware)object;
		String strCurMaxPK = baseDAO.getMaxPropertyValue("Hardware", "hardwareId");
		String strMaxPK = getcbspk.GetTablePK("Hardware", strCurMaxPK);
		hardware.setHardwareId(strMaxPK);
		baseDAO.save(hardware);
		return baseDAO.getById(Hardware.class, strMaxPK);
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
	
	//
	public List getHardwaresByHardwareModelIdAndSerialNo(String hardwareModelId, String serialNo)
	{
		Map map = new HashMap();
		map.put("hardwareModelId", hardwareModelId);
		map.put("serialNo", serialNo);
		List hardwares = baseDAO.queryByNamedQuery("select_hardwaresByHardwareModelIdAndSerialNo", map);

		return hardwares;
	}
	
	//
	public List getHardwaresByHardwareModelIdAndCardNo(String hardwareModelId, String cardNo)
	{
		Map map = new HashMap();
		map.put("hardwareModelId", hardwareModelId);
		map.put("cardNo", cardNo);
		List hardwares = baseDAO.queryByNamedQuery("select_hardwaresByHardwareModelIdAndCardNo", map);

		return hardwares;
	}
	
	//
	public List getHardwaresByHardwareModelIdAndOtherNo(String hardwareModelId, String otherNo)
	{
		Map map = new HashMap();
		map.put("hardwareModelId", hardwareModelId);
		map.put("otherNo", otherNo);
		List hardwares = baseDAO.queryByNamedQuery("select_hardwaresByHardwareModelIdAndOtherNo", map);

		return hardwares;
	}
}
