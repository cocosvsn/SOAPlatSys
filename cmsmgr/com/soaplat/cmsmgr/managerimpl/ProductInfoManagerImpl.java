package com.soaplat.cmsmgr.managerimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.soaplat.cmsmgr.bean.ProductInfo;
import com.soaplat.cmsmgr.bean.TProductInfo;
import com.soaplat.sysmgr.common.IGetPK;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.soaplat.cmsmgr.manageriface.IProductInfoManager;

/**
 * Title 		:the Class ProductInfoManagerImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class ProductInfoManagerImpl implements IProductInfoManager {
	private static final Logger logger = Logger.getLogger("Cms");
	
	/** The productinfodao. */
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

		ProductInfo productinfo=(ProductInfo)baseDAO.getById(ProductInfo.class, pkid);
		if(productinfo!=null){
			baseDAO.delete(productinfo);
		}
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findAll()
	 */
	public List findAll() {
		List productinfolist=baseDAO.findAll("ProductInfo","productid");
		return productinfolist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		List productinfolist=baseDAO.findByProperty("ProductInfo", propertyName, value);
		return productinfolist;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#getById(java.lang.String)
	 */
	public Object getById(String pkid) {
		ProductInfo productinfo=(ProductInfo)baseDAO.getById(ProductInfo.class, pkid);
		return productinfo;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#loadById(java.lang.String)
	 */
	public Object loadById(String pkid) {
		ProductInfo productinfo=(ProductInfo)baseDAO.loadById(ProductInfo.class, pkid);
		return productinfo;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.manageriface.IBaseManager#save(java.lang.Object)
	 */
	public Object save(Object object) {
		ProductInfo productinfo=new ProductInfo();
		productinfo=(ProductInfo)object;
		String strMaxPK=getcmspk.GetTablePK("ProductInfo");
		productinfo.setProductid(strMaxPK);
		baseDAO.save(productinfo);
		//
		return baseDAO.getById(ProductInfo.class, strMaxPK);

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

	
	/**------------------------- HuangBo addtion by 2010年11月3日 16时38分------------------------------**/
	public TProductInfo getTProductInfoById(String productInfoId) {
		return (TProductInfo) this.baseDAO.getById(TProductInfo.class, productInfoId);
	}
	
	/**
	 * 根据编单日期, 节目包ID查询栏目单明细中节目包应用产品信息的KeyId
	 * @param scheduleDateStr 编单日期, 格式: 20100909000000
	 * @param progPackageId 节目包ID
	 * @return 返回应用产品信息的KeyId集合字符串
	 */
	@SuppressWarnings("unchecked")
	public String queryProductInfoKeyIdByScheduleDateAndProgPackageId(
			String scheduleDateStr, String progPackageId) {
		Map<String, String> map = new HashMap<String, String>(2);
		map.put("scheduleDate", scheduleDateStr);
		map.put("progPackageId", progPackageId);
		List<String> list = this.baseDAO.queryByNamedQuery(
				"query.TProductInfo.keyId.by.scheduleDate.progPackageId", map);
		if (0 < list.size()) {
			return list.get(0);
		}
		return "";
	}

	/**
	 * 根据产品信息ID查询产品描述XML
	 * @param productInfoId 产品信息ID
	 * @return 返回产品描述XML
	 */
	public String queryProductInfoXMLById(String productInfoId) {
		TProductInfo productInfo = (TProductInfo) this.baseDAO.getById(
				TProductInfo.class, productInfoId);
		if (null == productInfo) {
			throw new NullPointerException(" 查询产品描述XML失败, 产品信息不存在! ");
		} else {
			return productInfo.getProductXml();
		}
	}

	/**
	 * 根据编单日期ID, 查询该日期下所有编单明细已应用的产品信息是否已全部加密
	 * @param scheduleDate 编单日期ID
	 * @return 返回是否已全部加密完成
	 */
	@SuppressWarnings("unchecked")
	public boolean existNoEncryptProductInfo(String scheduleDate) {
		Map<String, String> map = new HashMap<String, String>(2);
		map.put("scheduleDate", scheduleDate);
		List<Object> list = this.baseDAO.queryByNamedQuery(
				"query.TProductInfo.count.by.scheduleDate", map);
		if (0 < list.size()) {
			logger.debug("编单[ " + scheduleDate + " ]存在未订价节目包数量: " + list.size());
			int unEncryptProductInfoCount = Integer.parseInt(list.get(0).toString());
			return 0 != unEncryptProductInfoCount;
		}
		return true; 
	}

	/**
	 * 根据节目包编号, 查询该节目包有效的最新历史订价信息
	 * @param progPackageId 节目包编号
	 * @return 返回最新的有效历史订价信息
	 */
	@SuppressWarnings("unchecked")
	public TProductInfo queryProductInfoByProgPackageId(String progPackageId) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("progPackageId", progPackageId);
		List<TProductInfo> list = this.baseDAO.queryByNamedQuery(
				"query.ProductInfo.by.progPackageId", map);
		if (0 < list.size()) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 根据节目包名称查询产品订价信息
	 * @param progPackageName 节目包名称
	 * @return List<TProductInfo>
	 */
	@SuppressWarnings("unchecked")
	public List<TProductInfo> queryProductInfosByProgPackageName(String progPackageName) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("progPackageName", progPackageName);
		return this.baseDAO.queryByNamedQuery(
				"query.TProductInfo.by.progPackageName", map);
	}

	/**
	 * 根据编单日期, 查询当天编单节目包可用于加密的产品信息
	 * @param scheduleDate 编单日期
	 * @return List<TProductInfo>
	 */
	@SuppressWarnings("unchecked")
	public List<TProductInfo> queryProductInfosByScheduleDateStr(String scheduleDate) {
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("scheduleDate", scheduleDate);
		return this.baseDAO.queryByNamedQuery(
				"query.TProductInfo.by.scheduleDate", map);
	}
}

