/*
 * 
 */
package com.cbs.cbsmgr.common;

import com.soaplat.sysmgr.common.IGetPK;
import java.text.SimpleDateFormat;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * Title 		:the Class GetPKImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class GetCbsPKImpl implements IGetPK {

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.common.IGetPK#GetTablePK(java.lang.String)
	 */
	public String GetTablePK(String entityname) {
		
		// ��ȡ�ַ�������ֵ
		String strmaxPK = "";
		SimpleDateFormat sdfCurTime = new SimpleDateFormat("yyyyMMddHHmmssSSSSSS");
		String strCurTime = sdfCurTime.format(new Date());

//		if(entityname.toUpperCase().equals("VODPACKAGE"))
//		{
//			strmaxPK = strCurTime;
//		}
//		else if(entityname.toUpperCase().equals("VODPRODUCT"))
//		{
//			strmaxPK = strCurTime;
//		}
//		else
//		{
//			strmaxPK = "0";
//		}
		return strmaxPK;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.common.IGetPK#GetTablePK(java.lang.String, java.lang.String)
	 */
	public String GetTablePK(String entityname, String currentmax) {

		// ��ȡ������������ֵ����ǰ���ֵ + 1��
		String strmaxPK = "";
		String strPre = "";
		long longmaxPK = 0;
//		SimpleDateFormat sdfCurTime = new SimpleDateFormat("yyyyMMddHHmmssSSSSSS");
//		String strCurTime = sdfCurTime.format(new Date());
		
		// ����������
		if(entityname.toUpperCase().equals("ACCOUNT")
			|| entityname.toUpperCase().equals("ADDRESS")
			|| entityname.toUpperCase().equals("BILLINGRULE")
			|| entityname.toUpperCase().equals("BRCPRODUCT")
			|| entityname.toUpperCase().equals("CBSGROUP")
			|| entityname.toUpperCase().equals("CBSUSER")
			|| entityname.toUpperCase().equals("CUSTOMER")
			|| entityname.toUpperCase().equals("EVENT")
			|| entityname.toUpperCase().equals("FINANCIALTRANSACTION")
//			|| entityname.toUpperCase().equals("HARDWARE")
			|| entityname.toUpperCase().equals("HISTORY")
			|| entityname.toUpperCase().equals("INVOICE")
			|| entityname.toUpperCase().equals("PACKAGEITEM")
			|| entityname.toUpperCase().equals("PACKAGE")
			|| entityname.toUpperCase().equals("PRODUCTPACKAGE")
			|| entityname.toUpperCase().equals("PRICE")
			|| entityname.toUpperCase().equals("PRODUCTCATEGORY")
			|| entityname.toUpperCase().equals("PRODUCT")
			|| entityname.toUpperCase().equals("USERAUTHENTICATION")
			|| entityname.toUpperCase().equals("CASENDLOG")
			)
		{
			if(currentmax == null || currentmax.equals("") || currentmax.equals("0"))
			{
				longmaxPK = 1;
			}
			else
			{ 
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre);
				longmaxPK = longmaxPK + 1;
			}
			strmaxPK = String.format("%d", longmaxPK);
		}
		// �ַ�������
		else if(entityname.toUpperCase().equals("VODCAMPAIGNCATEGORY"))
		{
			if(currentmax == null || currentmax.equals("") || currentmax.equals("0")) 
			{
				longmaxPK = 1;
			}
			else
			{ 
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre) ;
				longmaxPK = longmaxPK + 1;	
			}
			strmaxPK= "CC" + String.format("%08d", longmaxPK);	
		}
		else if(entityname.toUpperCase().equals("VODDISPLAYCATEGORY"))
		{
			if(currentmax == null || currentmax.equals("") || currentmax.equals("0")) 
			{
				longmaxPK = 1;
			}
			else
			{ 
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre) ;
				longmaxPK = longmaxPK + 1;	
			}
			strmaxPK= "DC" + String.format("%08d", longmaxPK);	
		}
		else if(entityname.toUpperCase().equals("VODHISTORY"))
		{
			if(currentmax == null || currentmax.equals("") || currentmax.equals("0")) 
			{
				longmaxPK = 1;
			}
			else
			{ 
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre) ;
				longmaxPK = longmaxPK + 1;	
			}
			strmaxPK= "HS" + String.format("%08d", longmaxPK);	
		}
		else if(entityname.toUpperCase().equals("VODHISTORYTEMP"))
		{
			if(currentmax == null || currentmax.equals("") || currentmax.equals("0")) 
			{
				longmaxPK = 1;
			}
			else
			{ 
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre) ;
				longmaxPK = longmaxPK + 1;	
			}
			strmaxPK= "HT" + String.format("%08d", longmaxPK);	
		}
		else if(entityname.toUpperCase().equals("VODPROGRAMPACKAGEREL"))
		{
			if(currentmax == null || currentmax.equals("") || currentmax.equals("0")) 
			{
				longmaxPK = 1;
			}
			else
			{ 
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre) ;
				longmaxPK = longmaxPK + 1;	
			}
			strmaxPK= "PP" + String.format("%08d", longmaxPK);	
		}
		else if(entityname.toUpperCase().equals("VODSEND"))
		{
			if(currentmax == null || currentmax.equals("") || currentmax.equals("0")) 
			{
				longmaxPK = 1;
			}
			else
			{ 
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre) ;
				longmaxPK = longmaxPK + 1;	
			}
			strmaxPK= "SN" + String.format("%08d", longmaxPK);	
		}
		else if(entityname.toUpperCase().equals("VODSENDDETAIL"))
		{
			if(currentmax == null || currentmax.equals("") || currentmax.equals("0")) 
			{
				longmaxPK = 1;
			}
			else
			{ 
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre) ;
				longmaxPK = longmaxPK + 1;	
			}
			strmaxPK= "SD" + String.format("%08d", longmaxPK);	
		}
		else if(entityname.toUpperCase().equals("VODPACKAGE"))
		{
			if(currentmax == null || currentmax.equals("") || currentmax.equals("0")) 
			{
				longmaxPK = 1;
			}
			else
			{ 
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre) ;
				longmaxPK = longmaxPK + 1;	
			}
			strmaxPK= "PK" + String.format("%08d", longmaxPK);	
		}
		else if(entityname.toUpperCase().equals("VODPRODUCT"))
		{
			if(currentmax == null || currentmax.equals("") || currentmax.equals("0")) 
			{
				longmaxPK = 1;
			}
			else
			{ 
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre) ;
				longmaxPK = longmaxPK + 1;	
			}
			strmaxPK= "PD" + String.format("%08d", longmaxPK);	
		}
		else if(entityname.toUpperCase().equals("VODCBSPRODUCTREL"))
		{
			if(currentmax == null || currentmax.equals("") || currentmax.equals("0")) 
			{
				longmaxPK = 1;
			}
			else
			{ 
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre) ;
				longmaxPK = longmaxPK + 1;	
			}
			strmaxPK= "PR" + String.format("%08d", longmaxPK);	
		}
		else if(entityname.toUpperCase().equals("HARDWARE"))
		{
			if(currentmax == null || currentmax.equals("") || currentmax.equals("0")) 
			{
				longmaxPK = 1;
			}
			else
			{ 
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre) ;
				longmaxPK = longmaxPK + 1;	
			}
			strmaxPK= "HR" + String.format("%08d", longmaxPK);	
		}
		else if(entityname.toUpperCase().equals("HARDWAREMODEL"))
		{
			if(currentmax == null || currentmax.equals("") || currentmax.equals("0")) 
			{
				longmaxPK = 1;
			}
			else
			{ 
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre) ;
				longmaxPK = longmaxPK + 1;	
			}
			strmaxPK= "HM" + String.format("%08d", longmaxPK);	
		}
		else if(entityname.toUpperCase().equals("SMSDISTRICT"))
		{
			if(currentmax == null || currentmax.equals("") || currentmax.equals("0")) 
			{
				longmaxPK = 1;
			}
			else
			{ 
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre) ;
				longmaxPK = longmaxPK + 1;	
			}
			strmaxPK= "DT" + String.format("%08d", longmaxPK);	
		}
		else if(entityname.toUpperCase().equals("PPSRVPDTREL"))
		{
			if(currentmax == null || currentmax.equals("") || currentmax.equals("0")) 
			{
				longmaxPK = 1;
			}
			else
			{ 
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre) ;
				longmaxPK = longmaxPK + 1;	
			}
			strmaxPK= "PS" + String.format("%08d", longmaxPK);	
		}
		else if(entityname.toUpperCase().equals("CAMPAIGNCATEGORY"))
		{
			if(currentmax == null || currentmax.equals("") || currentmax.equals("0")) 
			{
				longmaxPK = 1;
			}
			else
			{ 
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre) ;
				longmaxPK = longmaxPK + 1;	
			}
			strmaxPK= "CC" + String.format("%08d", longmaxPK);	
		}
		else if(entityname.toUpperCase().equals("CHANNELLIST"))
		{
			if(currentmax == null || currentmax.equals("") || currentmax.equals("0")) 
			{
				longmaxPK = 1;
			}
			else
			{ 
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre) ;
				longmaxPK = longmaxPK + 1;	
			}
			strmaxPK = "CL" + String.format("%08d", longmaxPK);	
		}
		else if(entityname.toUpperCase().equals("VODFLOW"))
		{
			if(currentmax == null || currentmax.equals("") || currentmax.equals("0")) 
			{
				longmaxPK = 1;
			}
			else
			{ 
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre) ;
				longmaxPK = longmaxPK + 1;	
			}
			strmaxPK = String.format("%010d", longmaxPK);	
		}
		// ��ǰ����ʱ��
//		else if(entityname.toUpperCase().equals("VODPACKAGE"))
//		{
//			strmaxPK = strCurTime;
//		}
//		else if(entityname.toUpperCase().equals("VODPRODUCT"))
//		{
//			strmaxPK = strCurTime;
//		}
//		else
//		{
//			longmaxPK = 0;
//		}

		return strmaxPK;
	}
}


