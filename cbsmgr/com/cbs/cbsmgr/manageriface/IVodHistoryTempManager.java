package com.cbs.cbsmgr.manageriface;

import java.util.List;

public interface IVodHistoryTempManager extends IBaseManager {

	// ���ݿͻ�Id��vodProductId����ѯ��vodHistoryTemp���õ����е㲥��¼��amount�ܺ�
	public Double getTotalAmountByCustomerIdAndVodProductId(Long customerId, String vodProductId);
	
	// 20091224 13:12
	// ��ҳ��ѯVodHistory���м�¼��������δ����״̬�����򣺿ͻ����㲥����
	public List getNotDealedVodHistoryTempsWithCount(int firstResult, int maxResults);
}
