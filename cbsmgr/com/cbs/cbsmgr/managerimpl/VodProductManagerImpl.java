package com.cbs.cbsmgr.managerimpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.sysmgr.common.IGetPK;
import com.cbs.cbsmgr.bean.VodProduct;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.IVodProductManager;

public class VodProductManagerImpl implements IVodProductManager {

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
		VodProduct vodProduct = (VodProduct)baseDAO.getById(VodProduct.class, pkid);
		if(vodProduct != null)
		{
			baseDAO.delete(vodProduct);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List vodProductList = baseDAO.findAll("VodProduct", "vodProductId");
		return vodProductList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List vodProductList = baseDAO.findByProperty("VodProduct", propertyName, value);
		return vodProductList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		VodProduct vodProduct = (VodProduct)baseDAO.getById(VodProduct.class, pkid);
		return vodProduct;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		VodProduct vodProduct = (VodProduct)baseDAO.loadById(VodProduct.class, pkid);
		return vodProduct;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		VodProduct vodProduct = new VodProduct();
		vodProduct = (VodProduct)object;
		//vodProduct.setInputtime(new Date());
		String strCurMaxPK = baseDAO.getMaxPropertyValue("VodProduct", "vodProductId");
		String strMaxPK = getcbspk.GetTablePK("VodProduct", strCurMaxPK);
		vodProduct.setVodProductId(strMaxPK);
		baseDAO.save(vodProduct);
		//
		return baseDAO.getById(VodProduct.class, strMaxPK);
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
	
	// 根据节目ID，查询表VodProgramPackageRel VodProduct，得到VodProductId
	public String getVodProductIdByProgramId(String programId)
	{
		String vodProductId = "";
		
		Map map = new HashMap();
		map.put("programid", programId);
		List list = baseDAO.queryByNamedQuery("select_vodproductbyprogramid", map);
		if(list.size() > 0)
		{
			VodProduct vodProduct = (VodProduct)list.get(0);
			vodProductId = vodProduct.getVodProductId();
		}
		
		return vodProductId;
	}
	
	// 查询套餐下的点播产品
	// 条件：productCategoryId
	// 查询表：vodCbsProductRel，VodProduct,VodPackage,VodDisplayCategory
	// hql:
//	select vpt.vodProductId,vpt.type,vpt.billingType,vpt.price,vpt.validFrom,vpt.validTo,
//	vpt.sendTag,vpt.packagePrice,vpg.vodPackageId,vpg.ptglobalid,vpg.description,vpg.subtitlelanguage,
//	vpg.audiolanguage,vpg.director,vpg.productname,vpg.actors,vpg.shootdate,vpg.issuedate,
//	vpg.countrydist,vpg.inputmanid,vpg.inputtime,vpg.remark,vdc.vodDisplayCategoryId,vdc.title
//	from vodCbsProductRel vcp, VodProduct vpt, VodPackage vpg, VodDisplayCategory vdc
//	where vcp.productCategoryId = :productCategoryId and vcp.vodProductId = vpt.vodProductId and vpt.vodPackageId = vpg.vodPackageId and vpg.vodDisplayCategoryId = vdc.vodDisplayCategoryId
	public List getVodProductsByProductCategoryId(Long productCategoryId)
	{
		Map map = new HashMap();
		map.put("productCategoryId", productCategoryId);
		List list = baseDAO.queryByNamedQuery("select_productsByProductCategoryId", map);
		return list;
	}
}
