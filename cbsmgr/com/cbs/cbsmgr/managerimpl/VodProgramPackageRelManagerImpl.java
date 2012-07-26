package com.cbs.cbsmgr.managerimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.sysmgr.common.IGetPK;
import com.cbs.cbsmgr.bean.VodProgramPackageRel;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.IVodProgramPackageRelManager;

import flex.messaging.io.ArrayCollection;

public class VodProgramPackageRelManagerImpl implements IVodProgramPackageRelManager {

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
		VodProgramPackageRel vodProgramPackageRel = (VodProgramPackageRel)baseDAO.getById(VodProgramPackageRel.class, pkid);
		if(vodProgramPackageRel != null)
		{
			baseDAO.delete(vodProgramPackageRel);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List vodProgramPackageRelList = baseDAO.findAll("VodProgramPackageRel", "relId");
		return vodProgramPackageRelList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List vodProgramPackageRelList = baseDAO.findByProperty("VodProgramPackageRel", propertyName, value);
		return vodProgramPackageRelList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		VodProgramPackageRel vodProgramPackageRel = (VodProgramPackageRel)baseDAO.getById(VodProgramPackageRel.class, pkid);
		return vodProgramPackageRel;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		VodProgramPackageRel vodProgramPackageRel = (VodProgramPackageRel)baseDAO.loadById(VodProgramPackageRel.class, pkid);
		return vodProgramPackageRel;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		VodProgramPackageRel vodProgramPackageRel = new VodProgramPackageRel();
		vodProgramPackageRel = (VodProgramPackageRel)object;
		//programFiles.setInputtime(new Date());
		String strCurMaxPK = baseDAO.getMaxPropertyValue("VodProgramPackageRel", "relId");
		String strMaxPK = getcbspk.GetTablePK("VodProgramPackageRel", strCurMaxPK);
		vodProgramPackageRel.setRelId(strMaxPK);
		baseDAO.save(vodProgramPackageRel);
		//
		return baseDAO.getById(VodProgramPackageRel.class, strMaxPK);
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
	public List findprogrambyvodpackageid(String vodPackageId) {
		Map map = new HashMap(0);
		map.put("vodPackageId", vodPackageId);
		List list=baseDAO.queryByNamedQuery("select_programbyvodpackageid",map);
		return list;
	}
	public int deleteprogrampackagerelbypackageid(String vodPackageId)
	{
		Map map = new HashMap(0);
		map.put("vodPackageId", vodPackageId);
		int ret = baseDAO.updateByNamedQuery("delete_programpackagerelbypackageid",map);
		return ret;
	}
	public void delete(ArrayCollection arr) {
		// TODO Auto-generated method stub
		
	}

	public void save(ArrayCollection arr) {
		// TODO Auto-generated method stub
		if (arr.size()>0)
		{
			for(int i=0;i<arr.size();i++)
			{
				this.save(arr.get(i));
			}
		}
	}

	public void update(ArrayCollection arr) {
		// TODO Auto-generated method stub
		
	}

	public int updateprogrampackagerel(ArrayCollection arr) {
		// TODO Auto-generated method stub
		String vodPackage = "";
		if (arr.size()>0)
		{
			vodPackage = ((VodProgramPackageRel)(arr.get(0))).getVodPackageId();
			this.deleteprogrampackagerelbypackageid(vodPackage);
			this.save(arr);
		}
		return 0;
	}

}
