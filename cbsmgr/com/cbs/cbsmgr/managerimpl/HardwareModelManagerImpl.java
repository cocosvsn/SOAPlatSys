package com.cbs.cbsmgr.managerimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.sysmgr.common.IGetPK;
import com.cbs.cbsmgr.bean.HardwareModel;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.IHardwareModelManager;

public class HardwareModelManagerImpl implements IHardwareModelManager {

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
		HardwareModel hardwareModel = (HardwareModel)baseDAO.getById(HardwareModel.class, pkid);
		if(hardwareModel != null)
		{
			baseDAO.delete(hardwareModel);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List hardwareModelList = baseDAO.findAll("HardwareModel", "hardwareModelId");
		return hardwareModelList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List hardwareModelList = baseDAO.findByProperty("HardwareModel", propertyName, value);
		return hardwareModelList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		HardwareModel hardwareModel = (HardwareModel)baseDAO.getById(HardwareModel.class, pkid);
		return hardwareModel;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		HardwareModel hardwareModel = (HardwareModel)baseDAO.loadById(HardwareModel.class, pkid);
		return hardwareModel;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		HardwareModel hardwareModel = new HardwareModel();
		hardwareModel = (HardwareModel)object;
		String strCurMaxPK = baseDAO.getMaxPropertyValue("HardwareModel", "hardwareModelId");
		String strMaxPK = getcbspk.GetTablePK("HardwareModel", strCurMaxPK);
		hardwareModel.setHardwareModelId(strMaxPK);
		baseDAO.save(hardwareModel);
		return baseDAO.getById(HardwareModel.class, strMaxPK);
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
	
	public List getHardwareModelsByHardwareModelName(String hardwareModelName)
	{
		List list = null;
		if(hardwareModelName == null || hardwareModelName.equalsIgnoreCase(""))
		{
			list = this.findAll();
		}
		else
		{
			hardwareModelName = "%" + hardwareModelName + "%";
			Map map = new HashMap();
			map.put("hardwareModelName", hardwareModelName);
			list = baseDAO.queryByNamedQuery("select_hardwareModelsByHardwareModelName", map);
		}
		return list;
	}
}
