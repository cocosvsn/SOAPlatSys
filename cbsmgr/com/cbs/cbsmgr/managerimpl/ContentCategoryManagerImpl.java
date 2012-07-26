package com.cbs.cbsmgr.managerimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.sysmgr.common.IGetPK;
import com.cbs.cbsmgr.bean.ContentCategory;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.IContentCategoryManager;

public class ContentCategoryManagerImpl implements IContentCategoryManager {

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
		ContentCategory contentCategory = (ContentCategory)baseDAO.getById(ContentCategory.class, pkid);
		if(contentCategory != null)
		{
			baseDAO.delete(contentCategory);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List contentCategoryList = baseDAO.findAll("ContentCategory", "contentCategoryId");
		return contentCategoryList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List contentCategoryList = baseDAO.findByProperty("ContentCategory", propertyName, value);
		return contentCategoryList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		ContentCategory contentCategory = (ContentCategory)baseDAO.getById(ContentCategory.class, pkid);
		return contentCategory;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		ContentCategory contentCategory = (ContentCategory)baseDAO.loadById(ContentCategory.class, pkid);
		return contentCategory;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		ContentCategory contentCategory = new ContentCategory();
		contentCategory = (ContentCategory)object;
		String strCurMaxPK = baseDAO.getMaxPropertyValue("ContentCategory", "contentCategoryId");
		String strMaxPK = getcbspk.GetTablePK("ContentCategory", strCurMaxPK);
		contentCategory.setContentCategoryId(strMaxPK);
		baseDAO.save(contentCategory);
		return baseDAO.getById(ContentCategory.class, strMaxPK);
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
