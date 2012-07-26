package com.cbs.cbsmgr.managerimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.sysmgr.common.IGetPK;
import com.cbs.cbsmgr.bean.VodCampaignCategory;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.IVodCampaignCategoryManager;

public class VodCampaignCategoryManagerImpl implements IVodCampaignCategoryManager {

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
		VodCampaignCategory vodCampaignCategory = (VodCampaignCategory)baseDAO.getById(VodCampaignCategory.class, pkid);
		if(vodCampaignCategory != null)
		{
			baseDAO.delete(vodCampaignCategory);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List vodCampaignCategoryList = baseDAO.findAll("VodCampaignCategory", "vodCampaignCategoryId");
		return vodCampaignCategoryList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List vodCampaignCategoryList = baseDAO.findByProperty("VodCampaignCategory", propertyName, value);
		return vodCampaignCategoryList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		VodCampaignCategory vodCampaignCategory = (VodCampaignCategory)baseDAO.getById(VodCampaignCategory.class, pkid);
		return vodCampaignCategory;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		VodCampaignCategory vodCampaignCategory = (VodCampaignCategory)baseDAO.loadById(VodCampaignCategory.class, pkid);
		return vodCampaignCategory;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		VodCampaignCategory vodCampaignCategory = new VodCampaignCategory();
		vodCampaignCategory = (VodCampaignCategory)object;
		vodCampaignCategory.setCreateDate(new Date());

		String strCurMaxPK = baseDAO.getMaxPropertyValue("VodCampaignCategory", "vodCampaignCategoryId");
		String strMaxPK = getcbspk.GetTablePK("VodCampaignCategory",strCurMaxPK);


		vodCampaignCategory.setVodCampaignCategoryId(strMaxPK);
		baseDAO.save(vodCampaignCategory);
		//
		return baseDAO.getById(VodCampaignCategory.class, strMaxPK);
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
		if (object.length > 0)
		{
			for(int i = 0; i < object.length; i++)
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
	
	// 得到所有有效的点播优惠
	public List getValidVodCampaignCategories()
	{
		Map map = new HashMap();
		List list = baseDAO.queryByNamedQuery("select_vodCampaignCategories", map);
		return list;
	}
}
