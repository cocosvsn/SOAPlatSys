package com.cbs.cbsmgr.managerimpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cbs.cbsmgr.bean.PpSrvPdtRel;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.IPpSrvPdtRelManager;

public class PpSrvPdtRelManagerImpl implements IPpSrvPdtRelManager {

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
		PpSrvPdtRel ppSrvPdtRel = (PpSrvPdtRel)baseDAO.getById(PpSrvPdtRel.class, pkid);
		if(ppSrvPdtRel != null)
		{
			baseDAO.delete(ppSrvPdtRel);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List ppSrvPdtRelList = baseDAO.findAll("PpSrvPdtRel", "ppSrvPdtRelId");
		return ppSrvPdtRelList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List ppSrvPdtRelList = baseDAO.findByProperty("PpSrvPdtRel", propertyName, value);
		return ppSrvPdtRelList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		PpSrvPdtRel ppSrvPdtRel = (PpSrvPdtRel)baseDAO.getById(PpSrvPdtRel.class, pkid);
		return ppSrvPdtRel;
	}
	
//	public Object getById(Long pkid) {
//		// TODO Auto-generated method stub
//		PpSrvPdtRel ppSrvPdtRel = (PpSrvPdtRel)baseDAO.getById(PpSrvPdtRel.class, pkid);
//		return ppSrvPdtRel;
//	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		PpSrvPdtRel ppSrvPdtRel = (PpSrvPdtRel)baseDAO.loadById(PpSrvPdtRel.class, pkid);
		return ppSrvPdtRel;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		PpSrvPdtRel ppSrvPdtRel = new PpSrvPdtRel();
		ppSrvPdtRel = (PpSrvPdtRel)object;
		
		String strCurMaxPK = baseDAO.getMaxPropertyValue("PpSrvPdtRel", "relid");
		String strMaxPK = getcbspk.GetTablePK("PpSrvPdtRel",strCurMaxPK);
		ppSrvPdtRel.setRelid(strMaxPK);
		// 保存到数据库
		baseDAO.save(ppSrvPdtRel);

		return baseDAO.getById(PpSrvPdtRel.class, strMaxPK);
		
	}

	public void save(Object[] object) {
		// TODO Auto-generated method stub
		if (object.length > 0)
		{
			for(int i = 0; i < object.length; i++)
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
	
	public List getPpSrvPdtRelsByProductIdAndProductCategoryId(String productId, Long productCategoryId)
	{
		Map map = new HashMap();
		map.put("productid", productId);
		map.put("productCategoryId", productCategoryId);
		List list = baseDAO.queryByNamedQuery("select_ppSrvPdtRelsByProductIdAndProductCategoryId", map);
		return list;
	}
}
