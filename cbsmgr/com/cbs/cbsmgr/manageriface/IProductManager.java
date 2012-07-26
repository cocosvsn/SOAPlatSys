package com.cbs.cbsmgr.manageriface;

import java.util.Date;
import java.util.List;

public interface IProductManager extends IBaseManager {

	// �õ�Product������accountId productCategoryId
	public List getProductByAccountIdAndProductCategoryId(Long accountId, Long productCategoryId);
	
	// �õ�����״̬ ��product
	public List getNormalStatusProductsByCustomerId(Long customerId);
	
	// �õ��ͻ�id�����е�Ӳ��product
	public List getHardwareProductsByCustomerId(Long customerId);
	
	// �õ�����״̬ ��product
	public List getNormalProductsByCustomerId(Long customerId);
	
	// 20091224 15:36
	// ��ҳ��ѯProduct���м�¼������������״̬�������Բ�Ʒ���Ʒ���Ч��=date�����򣺿ͻ�����������
	public List getProductsForGeneratingPeriodFee(Date date, int firstResult, int maxResults);
	
	// 20091225 14:55
	// ����username����ѯHardware��Product���õ�Product
	public List getProductsByUsername(String userName);
}
