package com.soaplat.cmsmgr.manageriface;

import java.text.ParseException;
import java.util.List;

import com.soaplat.sysmgr.manageriface.IBaseManager;

public interface IEncryptListManager extends IBaseManager {

	// 20100610 10:43
	// 根据日期查询加扰任务
	public List getEncryptListByDate(
			String datefrom,
			String dateto
			) throws ParseException;
	
	// 20100610 11:10
	// 根据节目包ID、节目包名称和日期查询加扰任务
	public List getEncryptListByProductidProductnameDate(
			String productid,
			String productname,
			String datefrom,
			String dateto
			) throws ParseException;
	
	// 20100610 11:10
	// 根据节目包ID和日期查询加扰任务
	public List getEncryptListByProductidDate(
			String productid,
			String datefrom,
			String dateto
			) throws ParseException;
	
	// 20100610 11:10
	// 根据节目包名称和日期查询加扰任务
	public List getEncryptListByProductnameDate(
			String productname,
			String datefrom,
			String dateto
			) throws ParseException;
}
