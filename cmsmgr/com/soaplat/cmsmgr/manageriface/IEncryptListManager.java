package com.soaplat.cmsmgr.manageriface;

import java.text.ParseException;
import java.util.List;

import com.soaplat.sysmgr.manageriface.IBaseManager;

public interface IEncryptListManager extends IBaseManager {

	// 20100610 10:43
	// �������ڲ�ѯ��������
	public List getEncryptListByDate(
			String datefrom,
			String dateto
			) throws ParseException;
	
	// 20100610 11:10
	// ���ݽ�Ŀ��ID����Ŀ�����ƺ����ڲ�ѯ��������
	public List getEncryptListByProductidProductnameDate(
			String productid,
			String productname,
			String datefrom,
			String dateto
			) throws ParseException;
	
	// 20100610 11:10
	// ���ݽ�Ŀ��ID�����ڲ�ѯ��������
	public List getEncryptListByProductidDate(
			String productid,
			String datefrom,
			String dateto
			) throws ParseException;
	
	// 20100610 11:10
	// ���ݽ�Ŀ�����ƺ����ڲ�ѯ��������
	public List getEncryptListByProductnameDate(
			String productname,
			String datefrom,
			String dateto
			) throws ParseException;
}
