package com.cbs.cbsmgr.managerimpl;

import java.util.List;

import com.soaplat.sysmgr.common.IGetPK;
import com.cbs.cbsmgr.bean.VodDisplayCategory;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.IVodDisplayCategoryManager;

public class VodDisplayCategoryManagerImpl implements IVodDisplayCategoryManager {

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
		VodDisplayCategory vodDisplayCategory = (VodDisplayCategory)baseDAO.getById(VodDisplayCategory.class, pkid);
		if(vodDisplayCategory != null)
		{
			baseDAO.delete(vodDisplayCategory);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List vodDisplayCategoryList = baseDAO.findAll("VodDisplayCategory", "vodDisplayCategoryId");
		return vodDisplayCategoryList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List vodDisplayCategoryList = baseDAO.findByProperty("VodDisplayCategory", propertyName, value);
		return vodDisplayCategoryList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		VodDisplayCategory vodDisplayCategory = (VodDisplayCategory)baseDAO.getById(VodDisplayCategory.class, pkid);
		return vodDisplayCategory;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		VodDisplayCategory vodDisplayCategory = (VodDisplayCategory)baseDAO.loadById(VodDisplayCategory.class, pkid);
		return vodDisplayCategory;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		VodDisplayCategory vodDisplayCategory = new VodDisplayCategory();
		vodDisplayCategory = (VodDisplayCategory)object;
		//vodDisplayCategory(new Date());
		String strCurMaxPK = baseDAO.getMaxPropertyValue("VodDisplayCategory", "vodDisplayCategoryId");
		String strMaxPK = getcbspk.GetTablePK("VodDisplayCategory", strCurMaxPK);
		vodDisplayCategory.setVodDisplayCategoryId(strMaxPK);
		baseDAO.save(vodDisplayCategory);
		//
		return baseDAO.getById(VodDisplayCategory.class, strMaxPK);
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
