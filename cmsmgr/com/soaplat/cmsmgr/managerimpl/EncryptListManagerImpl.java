package com.soaplat.cmsmgr.managerimpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.cmsmgr.bean.EncryptList;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.cmsmgr.manageriface.IEncryptListManager;

// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class EncryptListManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class EncryptListManagerImpl implements IEncryptListManager {
	
	/** The EncryptListdao. */
	IBaseDAO baseDAO;
	
	/** The getcmspk. */
	IGetPK getcmspk;
	
	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#delete(java.lang.Object[])
	 */
	public void delete(Object[] object) {
		//��ɾ��
		if(object.length>0){
			for(int i=0;i<object.length;i++){
				baseDAO.delete(object[i]);
			}
		}else{
			return ;
		}	
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#deleteById(java.lang.String)
	 */
	public void deleteById(String pkid) {

		EncryptList encryptList=(EncryptList)baseDAO.getById(EncryptList.class, pkid);
		if(encryptList!=null){
			baseDAO.delete(encryptList);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List encryptListlist=baseDAO.findAll("EncryptList","encryptListid");
		return encryptListlist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List encryptListlist=baseDAO.findByProperty("EncryptList", propertyName, value);
		return encryptListlist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		EncryptList encryptList=(EncryptList)baseDAO.getById(EncryptList.class, pkid);
		return encryptList;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		EncryptList encryptList=(EncryptList)baseDAO.loadById(EncryptList.class, pkid);
		return encryptList;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		EncryptList encryptList = new EncryptList();
		encryptList = (EncryptList)object;
		String strCurMaxPK = baseDAO.getMaxPropertyValue("EncryptList", "encryptid");
		String strMaxPK = getcmspk.GetTablePK("EncryptList", strCurMaxPK);
		encryptList.setEncryptid(strMaxPK);
		baseDAO.save(encryptList);

		return baseDAO.getById(EncryptList.class, strMaxPK);

	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#update(java.lang.Object)
	 */
	public void update(Object object) {
		baseDAO.update(object);
		

	}

	/**
	 * Sets the baseDAO.
	 * 
	 * @param baseDAO the new baseDAO
	 */


	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object[])
	 */
	public void save(Object[] object) {
		if (object.length>0){
			for(int i=0;i<object.length;i++){
				this.save(object[i]);
			}
			
		}
		
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#update(java.lang.Object[])
	 */
	public void update(Object[] object) {
		if (object.length>0){
			for(int i=0;i<object.length;i++){
				this.update(object[i]);
			}
			
		}
		
	}

	public List findbyExample(Object exampleentity) {
		List list=baseDAO.findbyExample(exampleentity);
		return list;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public void setGetcmspk(IGetPK getcmspk) {
		this.getcmspk = getcmspk;
	}
	
	
	
	// 20100610 10:43
	// 根据日期查询加扰任务
	public List getEncryptListByDate(
			String datefrom,
			String dateto
			) throws ParseException
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date dB = format.parse(datefrom + " 00:00:00");
		Date dE = format.parse(dateto + " 23:59:59");
		java.sql.Timestamp from = new java.sql.Timestamp(dB.getTime());
		java.sql.Timestamp to = new java.sql.Timestamp(dE.getTime());
		
		Map map = new HashMap(0);
		map.put("from", from);
		map.put("to", to);
		List list = baseDAO.queryByNamedQuery("getEncryptListByDate",map);
		return list;
	}
	
	// 20100610 11:10
	// 根据节目包ID、节目包名称和日期查询加扰任务
	public List getEncryptListByProductidProductnameDate(
			String productid,
			String productname,
			String datefrom,
			String dateto
			) throws ParseException
	{
		productname = "%" + productname + "%";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date dB = format.parse(datefrom + " 00:00:00");
		Date dE = format.parse(dateto + " 23:59:59");
		java.sql.Timestamp from = new java.sql.Timestamp(dB.getTime());
		java.sql.Timestamp to = new java.sql.Timestamp(dE.getTime());
		
		Map map = new HashMap(0);
		/**
		 * HuangBo update by 2011年4月13日 9时48分
		 * 修改productid为模糊查询
		 */
		map.put("productid", "%" + productid + "%");
		map.put("productname", productname);
		map.put("from", from);
		map.put("to", to);
		List list = baseDAO.queryByNamedQuery("getEncryptListByProductidProductnameDate",map);
		return list;
	}
	
	// 20100610 11:10
	// 根据节目包ID和日期查询加扰任务
	public List getEncryptListByProductidDate(
			String productid,
			String datefrom,
			String dateto
			) throws ParseException
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date dB = format.parse(datefrom + " 00:00:00");
		Date dE = format.parse(dateto + " 23:59:59");
		java.sql.Timestamp from = new java.sql.Timestamp(dB.getTime());
		java.sql.Timestamp to = new java.sql.Timestamp(dE.getTime());
		
		Map map = new HashMap(0);
		/**
		 * HuangBo update by 2011年4月13日 9时48分
		 * 修改productid为模糊查询
		 */
		map.put("productid", "%" + productid + "%");
		map.put("from", from);
		map.put("to", to);
		List list = baseDAO.queryByNamedQuery("getEncryptListByProductidDate",map);
		return list;
	}
	
	// 20100610 11:10
	// 根据节目包名称和日期查询加扰任务
	public List getEncryptListByProductnameDate(
			String productname,
			String datefrom,
			String dateto
			) throws ParseException
	{
		productname = "%" + productname + "%";
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date dB = format.parse(datefrom + " 00:00:00");
		Date dE = format.parse(dateto + " 23:59:59");
		java.sql.Timestamp from = new java.sql.Timestamp(dB.getTime());
		java.sql.Timestamp to = new java.sql.Timestamp(dE.getTime());
		
		Map map = new HashMap(0);
		map.put("productname", productname);
		map.put("from", from);
		map.put("to", to);
		List list = baseDAO.queryByNamedQuery("getEncryptListByProductnameDate",map);
		return list;
	}
	
	

}
