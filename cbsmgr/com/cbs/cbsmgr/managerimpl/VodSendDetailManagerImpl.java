package com.cbs.cbsmgr.managerimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.sysmgr.common.IGetPK;
import com.cbs.cbsmgr.bean.VodProgramPackageRel;
import com.cbs.cbsmgr.bean.VodSendDetail;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.IVodSendDetailManager;

import flex.messaging.io.ArrayCollection;

public class VodSendDetailManagerImpl implements IVodSendDetailManager {

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
		VodSendDetail vodSendDetail = (VodSendDetail)baseDAO.getById(VodSendDetail.class, pkid);
		if(vodSendDetail != null)
		{
			baseDAO.delete(vodSendDetail);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List vodSendDetailList = baseDAO.findAll("VodSendDetail", "vodSendDetailId");
		return vodSendDetailList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List vodSendDetailList = baseDAO.findByProperty("VodSendDetail", propertyName, value);
		return vodSendDetailList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		VodSendDetail vodSendDetail = (VodSendDetail)baseDAO.getById(VodSendDetail.class, pkid);
		return vodSendDetail;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		VodSendDetail vodSendDetail = (VodSendDetail)baseDAO.loadById(VodSendDetail.class, pkid);
		return vodSendDetail;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		VodSendDetail vodSendDetail = new VodSendDetail();
		vodSendDetail = (VodSendDetail)object;
		//vodSendDetail.setCreateDate(new Date());
		String strCurMaxPK = baseDAO.getMaxPropertyValue("VodSendDetail", "vodSendDetailId");
		String strMaxPK = getcbspk.GetTablePK("VodSendDetail", strCurMaxPK);
		vodSendDetail.setVodSendDetailId(strMaxPK);
		baseDAO.save(vodSendDetail);
		//
		return baseDAO.getById(VodSendDetail.class, strMaxPK);
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
	public List findproductinfobysend(String VodSendId) {
		Map map = new HashMap(0);
		map.put("vodsendid", VodSendId);
		List list=baseDAO.queryByNamedQuery("select_productbysendid",map);
		return list;
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

	public List findvodsendandvodsenddetail(long dealState) {
		Map map = new HashMap(0);
		map.put("dealState", dealState);
		List list=baseDAO.queryByNamedQuery("select_vodsendandvodsenddetail",map);
		return list;
	}
	public int deletesenddetailbysendid(String vodSendId)
	{
		Map map = new HashMap(0);
		map.put("vodSendId", vodSendId);
		int ret = baseDAO.updateByNamedQuery("delete_senddetailbysendid",map);
		return ret;
	}

	public int updatesenddetailbysendid(ArrayCollection arr) {
		// TODO Auto-generated method stub
		String vodSendDetail = "";
		if (arr.size()>0)
		{
			vodSendDetail = ((VodSendDetail)(arr.get(0))).getVodSendId();
			this.deletesenddetailbysendid(vodSendDetail);
			this.save(arr);
		}
		return 0;
	}
}
