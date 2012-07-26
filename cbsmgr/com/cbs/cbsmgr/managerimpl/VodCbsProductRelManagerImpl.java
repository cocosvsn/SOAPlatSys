package com.cbs.cbsmgr.managerimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.sysmgr.common.IGetPK;
import com.cbs.cbsmgr.bean.VodCbsProductRel;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.IVodCbsProductRelManager;

public class VodCbsProductRelManagerImpl implements IVodCbsProductRelManager {

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
		VodCbsProductRel vodCbsProductRel = (VodCbsProductRel)baseDAO.getById(VodCbsProductRel.class, pkid);
		if(vodCbsProductRel != null)
		{
			baseDAO.delete(vodCbsProductRel);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List vodCbsProductRelList = baseDAO.findAll("VodCbsProductRel", "relId");
		return vodCbsProductRelList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List vodCbsProductRelList = baseDAO.findByProperty("VodCbsProductRel", propertyName, value);
		return vodCbsProductRelList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		VodCbsProductRel vodCbsProductRel = (VodCbsProductRel)baseDAO.getById(VodCbsProductRel.class, pkid);
		return vodCbsProductRel;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		VodCbsProductRel vodCbsProductRel = (VodCbsProductRel)baseDAO.loadById(VodCbsProductRel.class, pkid);
		return vodCbsProductRel;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		VodCbsProductRel vodCbsProductRel = new VodCbsProductRel();
		vodCbsProductRel = (VodCbsProductRel)object;
//		vodCbsProductRel.setCreateDate(new Date());
		String strCurMaxPK = baseDAO.getMaxPropertyValue("VodCbsProductRel", "relId");
		String strMaxPK = getcbspk.GetTablePK("VodCbsProductRel", strCurMaxPK);
		vodCbsProductRel.setRelId(strMaxPK);
		baseDAO.save(vodCbsProductRel);
		//
		return baseDAO.getById(VodCbsProductRel.class, strMaxPK);
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
	
	// 根据vodProductId,productCategoryId，查询关系记录
	public VodCbsProductRel getVodCbsProductRelByVodProductIdAndProductCategoryId(String vodProductId, Long productCategoryId)
	{
		VodCbsProductRel vodCbsProductRel;
		Map map = new HashMap();
		map.put("vodProductId", vodProductId);
		map.put("productCategoryId", productCategoryId);
		List list = baseDAO.queryByNamedQuery("select_vodCbsProductRelByVodProductIdAndProductCategoryId", map);
		if(list == null)
		{
			vodCbsProductRel = null;
		}
		else if(list.size() == 0)
		{
			vodCbsProductRel = null;
		}
		else
		{
			vodCbsProductRel = (VodCbsProductRel)list.get(0);
		}
		return vodCbsProductRel;
	}
}
