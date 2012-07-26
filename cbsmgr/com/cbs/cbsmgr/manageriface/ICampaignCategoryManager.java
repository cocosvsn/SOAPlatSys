package com.cbs.cbsmgr.manageriface;

import java.util.List;

import com.cbs.cbsmgr.bean.Account;
import com.cbs.cbsmgr.bean.Customer;
import com.cbs.cbsmgr.bean.VodHistory;

public interface ICampaignCategoryManager extends IBaseManager {

	// ��ѯ��Ʒ���Ż�
	// ������
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
			);
}
