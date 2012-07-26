package com.cbs.cbsmgr.webService;

import java.util.List;

import javax.jws.WebService;

import com.srmmgr.webservice.ProgProductRel;
import com.srmmgr.webservice.StbCustomer;


public interface WebServiceCallIface {

	// ����webService
	
	// ���棨�������ͻ�
	public int saveStbCustomers(List<StbCustomer> stbCustomers);
	
	// �޸Ŀͻ�
	public int updateStbCustomers(List<StbCustomer> stbCustomers);
	
	// ɾ���ͻ�
	public int deleteStbCustomers(List<StbCustomer> stbCustomers);
	
	// ��ѯ�ͻ�
	public List<StbCustomer> getAllStbCustomer();
	
	
	// ���棨��������Ŀ��Ʒ��ϵ
	public int saveProgProductRels(List<ProgProductRel> progProductRels);
	
	// �޸Ľ�Ŀ��Ʒ��ϵ
	public int updateProgProductRels(List<ProgProductRel> progProductRels);
	
	// ɾ����Ŀ��Ʒ��ϵ
	public int deleteProgProductRels(List<ProgProductRel> progProductRels);
	
	// ��ѯ��Ŀ��Ʒ��ϵ
	public List<ProgProductRel> getAllProgProductRels();
}
