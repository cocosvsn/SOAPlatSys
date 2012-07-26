package com.soaplat.amsmgr.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.soaplat.sysmgr.common.IGetPK;

/**
 * Title 		:the Class GetAmsPkImpl.
 * Description	:
 * Copyright	:copyright (c) 2009
 * Company		:SMET
 * Create Date	:2009-06-16
 * 
 * @author		:SOAPlat Group (Fanyanhua)
 * @version		:1.0
 */
public class GetAmsPkImpl implements IGetPK {

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.common.IGetPK#GetTablePK(java.lang.String)
	 */
	public String GetTablePK(String entityname) {
		String strmaxPK="";
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmssSSSSSS");
		String strcurtime=df.format(new Date());
		/**Rel Management*/
		//////Rel Management "AmsStoragePrgRel/StoragePrgRel/TSOAAMSSTORAGEPRGREL"   ��ɫ��Ա��ϵ��(TSOAAMSSTORAGEPRGREL)[[YYYYMMDDHHMMSSFFFFFF]]
		if(entityname.toUpperCase().equals("AMSSTORAGEPRGREL"))
		{
			strmaxPK=strcurtime;				
		}
		
		//////Rel Management "AmsFileSSchedule/TSOAAMSFILESSCHEDULE"   ��ɫ��Ա��ϵ��(TSOAAMSFILESSCHEDULE)[[YYYYMMDDHHMMSSFFFFFF]]
		if(entityname.toUpperCase().equals("AMSFILESSCHEDULE"))
		{
			strmaxPK=strcurtime;				
		}
		
		//////Rel Management "AmsStorageRoute/TSOAAMSSTORAGEROUTE""   ��ɫ��Ա��ϵ��(TSOAAMSSTORAGEROUTE)[[YYYYMMDDHHMMSSFFFFFF]]
		if(entityname.toUpperCase().equals("AMSSTORAGEROUTE"))
		{
			strmaxPK=strcurtime;				
		}
		
		
		
		
		return strmaxPK;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.common.IGetPK#GetTablePK(java.lang.String, java.lang.String)
	 */
	public String GetTablePK(String entityname, String currentmax) {
/////////////////
		String strmaxPK="";
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmssSSSSSS");
		String strcurtime=df.format(new Date());
		//////Rel Management "AmsStorage/TSOAAMSSTORAGE""   ��ɫ��Ա��ϵ��(TSOAAMSSTORAGE)[[YYYYMMDDHHMMSSFFFFFF]]
		if(entityname.toUpperCase().equals("AMSSTORAGE"))
		{
			strmaxPK=strcurtime;				
		}
		/////////////////
		//////Rel Management "AmsStorageClass/TSOAAMSSTORAGECLASS""   ��ɫ��Ա��ϵ��(TSOAAMSSTORAGECLASS)[[YYYYMMDDHHMMSSFFFFFF]]
		if(entityname.toUpperCase().equals("AMSSTORAGECLASS"))
		{
			strmaxPK=strcurtime;				
		}
		//////Rel Management "AmsStorageDir/TSOAAMSSTORAGEDIR""   ��ɫ��Ա��ϵ��(TSOAAMSSTORAGEDIR)[[YYYYMMDDHHMMSSFFFFFF]]
		if(entityname.toUpperCase().equals("AMSSTORAGEDIR"))
		{
			strmaxPK=strcurtime;				
		}
		/////////////////
		//////Rel Management "AmsFileServer/TSOAAMSFILESERVER""   ��ɫ��Ա��ϵ��(TSOAAMSFILESERVER)[[YYYYMMDDHHMMSSFFFFFF]]
		if(entityname.toUpperCase().equals("AMSFILESERVER"))
		{
			strmaxPK=strcurtime;				
		}
		
		
		if ("AMSSTORAGEHISTORY".equalsIgnoreCase(entityname)) {
			strmaxPK= String.format("%010d", Long.valueOf(currentmax) + 1);
		}
//		String strmaxPK="";
//		String strPre="";
//		long longmaxPK;
//		
//		
//		/////"IpQam/TSRMIPQAM"  ������Ա(TSRMIPQAM)[[IQ+NNNNNNNN]]
//		if(entityname.toUpperCase().equals("IPQAM"))
//		{
//			if(currentmax==null||currentmax.equals("")) 
//			{
//				longmaxPK=1;
//			}
//			else
//			{ 
//				strPre=currentmax.substring(2);
//				longmaxPK=Long.parseLong(strPre) ;
//				longmaxPK=longmaxPK+1;	
//			}
//			strmaxPK="IQ"+String.format("%08d", longmaxPK);
//		}
//		
//		/////"IpQamChannel/TSRMIPQAMCHANNEL"  ������Ա(TSRMIPQAMCHANNEL)[[IPQAMID+NNNN]]
//		if(entityname.toUpperCase().equals("IPQAMCHANNEL"))
//		{
//			String ipqamid="";
//			if(currentmax.length()<=10) 
//			{
//				longmaxPK=1;
//				ipqamid=currentmax;
//			}
//			else
//			{ 
//				strPre=currentmax.substring(2);
//				ipqamid=currentmax.substring(0,10);
//				longmaxPK=Long.parseLong(strPre) ;
//				longmaxPK=longmaxPK+1;	
//			}
//			strmaxPK=ipqamid+String.format("%04d", longmaxPK);
//		}
//	
//		/////"IpQamChannelUsage/TSRMIPQAMCHANNELUSAGE"  ������Ա(TSRMIPQAMCHANNELUSAGE)[[IPQAMID+NNNN]]
//		if(entityname.toUpperCase().equals("IPQAMCHANNELUSAGE"))
//		{
//			//不需要新生成
//		}
//		
//		/////"VodServer/TSRMVODSERVER"  ������Ա(TSRMVODSERVER)[[VS+NNNNNNNN]]
//		if(entityname.toUpperCase().equals("VODSERVER"))
//		{
//			if(currentmax==null||currentmax.equals("")) 
//			{
//				longmaxPK=1;
//			}
//			else
//			{ 
//				strPre=currentmax.substring(2);
//				longmaxPK=Long.parseLong(strPre) ;
//				longmaxPK=longmaxPK+1;	
//			}
//			strmaxPK="VS"+String.format("%08d", longmaxPK);
//		}
//		
//		
//		/////"VodServerUsage/TSRMVODSERVERUSAGE"  ������Ա(TSRMVODSERVERUSAGE)[[VS+NNNNNNNN]]
//		if(entityname.toUpperCase().equals("VODSERVERUSAGE"))
//		{
//			//不需要新生成
//		}
//		
//		/////"WebServer/TSRMWEBSERVER"  ������Ա(TSRMWEBSERVER)[[WS+NNNNNNNN]]
//		if(entityname.toUpperCase().equals("WEBSERVER"))
//		{
//			if(currentmax==null||currentmax.equals("")) 
//			{
//				longmaxPK=1;
//			}
//			else
//			{ 
//				strPre=currentmax.substring(2);
//				longmaxPK=Long.parseLong(strPre) ;
//				longmaxPK=longmaxPK+1;	
//			}
//			strmaxPK="WS"+String.format("%08d", longmaxPK);
//		}
//		
//		/////"Storage/TSRMSTORAGE"  ������Ա(TSRMSTORAGE)[[ST+NNNNNNNN]]
//		if(entityname.toUpperCase().equals("STORAGE"))
//		{
//			if(currentmax==null||currentmax.equals("")) 
//			{
//				longmaxPK=1;
//			}
//			else
//			{ 
//				strPre=currentmax.substring(2);
//				longmaxPK=Long.parseLong(strPre) ;
//				longmaxPK=longmaxPK+1;	
//			}
//			strmaxPK="ST"+String.format("%08d", longmaxPK);
//		}
//		
//		/////"StorageDir/TSRMSTORAGEDIR"  ������Ա(TSRMSTORAGEDIR)[[STORAGEID(10位）+NNNN]]
//		if(entityname.toUpperCase().equals("STORAGEDIR"))
//		{
//			String storageid="";
//			if(currentmax.length()<=10) 
//			{
//				longmaxPK=1;
//				storageid=currentmax;
//			}
//			else
//			{ 
//				strPre=currentmax.substring(10);
//				storageid=currentmax.substring(0, 10);
//				longmaxPK=Long.parseLong(strPre) ;
//				longmaxPK=longmaxPK+1;
//				
//			}
//			strmaxPK=storageid+String.format("%04d", longmaxPK);
//		}
//		
//		/////"District/TSRMDISTRICT"  ������Ա(TSRMDISTRICT)[[DI+NNNNNNNN]]
//		if(entityname.toUpperCase().equals("DISTRICT"))
//		{
//			if(currentmax==null||currentmax.equals("")) 
//			{
//				longmaxPK=1;
//			}
//			else
//			{ 
//				strPre=currentmax.substring(2);
//				longmaxPK=Long.parseLong(strPre) ;
//				longmaxPK=longmaxPK+1;	
//			}
//			strmaxPK="DI"+String.format("%08d", longmaxPK);
//		}
//		
//		/////"StbCustomer/TSRMSTBCUSTOMER"  ������Ա(TSRMSTBCUSTOMER)[[SC+NNNNNNNN]]
//		if(entityname.toUpperCase().equals("STBCUSTOMER"))
//		{
//			if(currentmax==null||currentmax.equals("")) 
//			{
//				longmaxPK=1;
//			}
//			else
//			{ 
//				strPre=currentmax.substring(2);
//				longmaxPK=Long.parseLong(strPre) ;
//				longmaxPK=longmaxPK+1;	
//			}
//			strmaxPK="SC"+String.format("%08d", longmaxPK);
//		}
//		
//		/////"LiveChannel/TSRMLIVECHANNEL"  ������Ա(TSRMLIVECHANNEL)[[LC+NNNNNNNN]]
//		if(entityname.toUpperCase().equals("LIVECHANNEL"))
//		{
//			if(currentmax==null||currentmax.equals("")) 
//			{
//				longmaxPK=1;
//			}
//			else
//			{ 
//				strPre=currentmax.substring(2);
//				longmaxPK=Long.parseLong(strPre) ;
//				longmaxPK=longmaxPK+1;	
//			}
//			strmaxPK="LC"+String.format("%08d", longmaxPK);
//		}
//		////
		return strmaxPK;
	
	}

}
