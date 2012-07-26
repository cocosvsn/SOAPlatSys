package com.cbs.cbsmgr.managerimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soaplat.sysmgr.common.IGetPK;
import com.cbs.cbsmgr.bean.Account;
import com.cbs.cbsmgr.bean.CampaignCategory;
import com.cbs.cbsmgr.bean.Customer;
import com.cbs.cbsmgr.bean.VodHistory;
import com.soaplat.sysmgr.common.IBaseDAO;
import com.cbs.cbsmgr.manageriface.ICampaignCategoryManager;

public class CampaignCategoryManagerImpl implements ICampaignCategoryManager {

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
		CampaignCategory CampaignCategory = (CampaignCategory)baseDAO.getById(CampaignCategory.class, pkid);
		if(CampaignCategory != null)
		{
			baseDAO.delete(CampaignCategory);
		}
	}

	public List findAll() {
		// TODO Auto-generated method stub
		List CampaignCategoryList = baseDAO.findAll("CampaignCategory", "CampaignCategoryId");
		return CampaignCategoryList;
	}

	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List CampaignCategoryList = baseDAO.findByProperty("CampaignCategory", propertyName, value);
		return CampaignCategoryList;
	}

	public Object getById(String pkid) {
		// TODO Auto-generated method stub
		CampaignCategory CampaignCategory = (CampaignCategory)baseDAO.getById(CampaignCategory.class, pkid);
		return CampaignCategory;
	}

	public Object loadById(String pkid) {
		// TODO Auto-generated method stub
		CampaignCategory CampaignCategory = (CampaignCategory)baseDAO.loadById(CampaignCategory.class, pkid);
		return CampaignCategory;
	}

	public Object save(Object object) {
		// TODO Auto-generated method stub
		CampaignCategory CampaignCategory = new CampaignCategory();
		CampaignCategory = (CampaignCategory)object;
		String strCurMaxPK = baseDAO.getMaxPropertyValue("CampaignCategory", "CampaignCategoryId");
		String strMaxPK = getcbspk.GetTablePK("CampaignCategory", strCurMaxPK);
		CampaignCategory.setCampaignCategoryId(strMaxPK);
		baseDAO.save(CampaignCategory);
		return baseDAO.getById(CampaignCategory.class, strMaxPK);
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
	
	// ��ѯ��Ʒ���Ż�
	// �����select_CampaignCategories
	// PRODUCT_CATEGORY_ID
	// SERVICE_TYPE
	// CUSTOMER_TYPE_COLLECTION
	// CUSTOMER_STATUS_COLLECTION
	// DISTRICT_COLLECTION
	// ACCOUNT_TYPE_COLLECTION
	// ACCOUNT_STATUS_COLLECTION
	// MOP_COLLECTION
	// VALID_FROM
	// VALID_TO
	// ACTIVE_TAG
	// ����
	// PRIORITY
	public List getCampaignCategories(
			VodHistory vodHistory,
			Customer customer,
			Account account
			)
	{
		String customerTypeCollection = "%(" + customer.getCustomerTypeId() + ")%";
		String customerStatusCollection = "%(" + customer.getCustomerStatusId() + ")%";
		String districtCollection = "%(" + customer.getDistrictId() + ")%";
		String accountTypeCollection = "%(" + account.getAccountTypeId() + ")%";
		String accountStatusCollection = "%(" + account.getAccountStatusId() + ")%";
		String mopCollection = "%(" + account.getMopId() + ")%";
		java.sql.Timestamp date = new java.sql.Timestamp(vodHistory.getStartTime().getTime());
		
		Map map = new HashMap();
		map.put("productCategoryId", vodHistory.getProductCategoryId());
		map.put("serviceType", vodHistory.getServiceType());
		map.put("customerTypeCollection", customerTypeCollection);
		map.put("customerStatusCollection", customerStatusCollection);
		map.put("districtCollection", districtCollection);
		map.put("accountTypeCollection", accountTypeCollection);
		map.put("accountStatusCollection", accountStatusCollection);
		map.put("mopCollection", mopCollection);
		map.put("validFrom", date);
		map.put("validTo", date);
		List list = baseDAO.queryByNamedQuery("select_CampaignCategories", map);
		return list;
	}
}
