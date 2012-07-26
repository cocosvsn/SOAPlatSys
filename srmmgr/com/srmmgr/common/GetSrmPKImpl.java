package com.srmmgr.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.soaplat.sysmgr.common.IGetPK;

public class GetSrmPKImpl implements IGetPK {

	public String GetTablePK(String entityname) {
		String strmaxPK="";
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmssSSSSSS");
		String strcurtime=df.format(new Date());
		/**Rel Management*/
		//////Rel Management "ConsumeList/TSRMCONSUMELIST"   ��ɫ��Ա��ϵ��(TSRMCONSUMELIST)[[YYYYMMDDHHMMSSFFFFFF]]
		if(entityname.toUpperCase().equals("CONSUMELIST"))
		{
			strmaxPK=strcurtime;				
		}
		//ProgProductRel
		//////Rel Management "ProgProductRel/TSRMPROGPRODUCTREL"   ��ɫ��Ա��ϵ��(TSRMPROGPRODUCTREL)[[YYYYMMDDHHMMSSFFFFFF]]
		if(entityname.toUpperCase().equals("PROGPRODUCTREL"))
		{
			strmaxPK=strcurtime;				
		}
//		//////Rel Management "SrmStoragePrgRel/TSRMSTORAGEPRGREL"   ��ɫ��Ա��ϵ��(TSRMSTORAGEPRGREL)[[YYYYMMDDHHMMSSFFFFFF]]
//		if(entityname.toUpperCase().equals("SRMSTORAGEPRGREL"))
//		{
//			strmaxPK=strcurtime;				
//		}
		
		//////Rel Management "StorageVsRel/TSRMSTORAGEVSREL"   ��ɫ��Ա��ϵ��(TSRMSTORAGEVSREL)[[YYYYMMDDHHMMSSFFFFFF]]
		if(entityname.toUpperCase().equals("STORAGEVSREL"))
		{
			strmaxPK=strcurtime;				
		}
		
		//////Rel Management "WebSVodSRel/TSRMWEBSVODSREL"   ��ɫ��Ա��ϵ��(TSRMWEBSVODSREL)[[YYYYMMDDHHMMSSFFFFFF]]
		if(entityname.toUpperCase().equals("WEBSVODSREL"))
		{
			strmaxPK=strcurtime;				
		}
		
		//////Rel Management "ConsumeDetail/TSRMCONSUMEDETAIL"   ��ɫ��Ա��ϵ��(TSRMCONSUMEDETAIL)[[YYYYMMDDHHMMSSFFFFFF]]
		if(entityname.toUpperCase().equals("CONSUMEDETAIL"))
		{
			strmaxPK=strcurtime;				
		}
		//////Rel Management "ResEpgServiceList/TSRMRESEPGSERVICELIST"   ��ɫ��Ա��ϵ��(TSRMRESEPGSERVICELIST)[[YYYYMMDDHHMMSSFFFFFF]]
		if(entityname.toUpperCase().equals("RESEPGSERVICELIST"))
		{
			strmaxPK=strcurtime;				
		}
		//////Rel Management "CmdConsumeList/TSRMCMDCONSUMELIST"   ��ɫ��Ա��ϵ��(TSRMCMDCONSUMELIST)[[YYYYMMDDHHMMSSFFFFFF]]
		if(entityname.toUpperCase().equals("CMDCONSUMELIST"))
		{
			strmaxPK=strcurtime;				
		}
		//////Rel Management "StbCustomerAuth/TSRMSTBCUSTOMERAUTH"   ��ɫ��Ա��ϵ��(TSRMSTBCUSTOMERAUTH)[[YYYYMMDDHHMMSSFFFFFF]]
		if(entityname.toUpperCase().equals("STBCUSTOMERAUTH"))
		{
			strmaxPK=strcurtime;				
		}
		/////"SrmStoragePrgRel/TSRMSTORAGEPRGREL"  ������Ա(TSRMIPQAMCHANNELPROGNO)[[LC+NNNNNNNN]]
		if(entityname.toUpperCase().equals("SRMSTORAGEPRGREL"))
		{
			strmaxPK=strcurtime;				

		}
		/////"VodServerUsage/TSRMVODSERVERUSAGE"  ������Ա(TSRMVODSERVERUSAGE)[[VS+NNNNNNNN]]
		if(entityname.toUpperCase().equals("VODSERVERUSAGE"))
		{
			//不需要新生成
		}
		/////"IpQamChannelUsage/TSRMIPQAMCHANNELUSAGE"  ������Ա(TSRMIPQAMCHANNELUSAGE)[[IPQAMID+NNNN]]
		if(entityname.toUpperCase().equals("IPQAMCHANNELUSAGE"))
		{
			//不需要新生成
		}
		//////Rel Management "ConsumeListHis/TSRMCONSUMELISTHIS"   ��ɫ��Ա��ϵ��(TSRMCONSUMELISTHIS)[[YYYYMMDDHHMMSSFFFFFF]]
		if(entityname.toUpperCase().equals("CONSUMELISTHIS"))
		{
			//不需要新生成
		}
		//////Rel Management "ConsumeDetailHis/TSRMCONSUMEDETAILHIS"   ��ɫ��Ա��ϵ��(TSRMCONSUMEDETAILHIS)[[YYYYMMDDHHMMSSFFFFFF]]
		if(entityname.toUpperCase().equals("CONSUMEDETAILHIS"))
		{
			//不需要新生成
		}
		return strmaxPK;
	}

	public String GetTablePK(String entityname, String currentmax) {
		String strmaxPK="";
		String strPre="";
		long longmaxPK;
		
		
		/////"IpQam/TSRMIPQAM"  ������Ա(TSRMIPQAM)[[IQ+NNNNNNNN]]
		if(entityname.toUpperCase().equals("IPQAM"))
		{
			if(currentmax==null||currentmax.equals("")) 
			{
				longmaxPK=1;
			}
			else
			{ 
				strPre=currentmax.substring(2);
				longmaxPK=Long.parseLong(strPre) ;
				longmaxPK=longmaxPK+1;	
			}
			strmaxPK="IQ"+String.format("%08d", longmaxPK);
		}
		/////"ChannelList/TSRMCHANNELLIST"  ������Ա(TSRMCHANNELLIST)[[CH+NNNNNNNN]]
		if(entityname.toUpperCase().equals("CHANNELLIST"))
		{
			if(currentmax==null||currentmax.equals("")) 
			{
				longmaxPK=1;
			}
			else
			{ 
				strPre=currentmax.substring(2);
				longmaxPK=Long.parseLong(strPre) ;
				longmaxPK=longmaxPK+1;	
			}
			strmaxPK="CH"+String.format("%08d", longmaxPK);
		}
		/////"IpQamChannel/TSRMIPQAMCHANNEL"  ������Ա(TSRMIPQAMCHANNEL)[[IPQAMID+NNNN]]
		if(entityname.toUpperCase().equals("IPQAMCHANNEL"))
		{
			String ipqamid="";
			if(currentmax.length()<=10) 
			{
				longmaxPK=1;
				ipqamid=currentmax;
			}
			else
			{ 
				strPre=currentmax.substring(2);
				ipqamid=currentmax.substring(0,10);
				longmaxPK=Long.parseLong(strPre) ;
				longmaxPK=longmaxPK+1;	
			}
			strmaxPK=ipqamid+String.format("%04d", longmaxPK);
		}
	
		
		
		/////"VodServer/TSRMVODSERVER"  ������Ա(TSRMVODSERVER)[[VS+NNNNNNNN]]
		if(entityname.toUpperCase().equals("VODSERVER"))
		{
			if(currentmax==null||currentmax.equals("")) 
			{
				longmaxPK=1;
			}
			else
			{ 
				strPre=currentmax.substring(2);
				longmaxPK=Long.parseLong(strPre) ;
				longmaxPK=longmaxPK+1;	
			}
			strmaxPK="VS"+String.format("%08d", longmaxPK);
		}
		
		
		
		
		/////"WebServer/TSRMWEBSERVER"  ������Ա(TSRMWEBSERVER)[[WS+NNNNNNNN]]
		if(entityname.toUpperCase().equals("WEBSERVER"))
		{
			if(currentmax==null||currentmax.equals("")) 
			{
				longmaxPK=1;
			}
			else
			{ 
				strPre=currentmax.substring(2);
				longmaxPK=Long.parseLong(strPre) ;
				longmaxPK=longmaxPK+1;	
			}
			strmaxPK="WS"+String.format("%08d", longmaxPK);
		}
		
		/////"SrmStorage/TSRMSTORAGE"  ������Ա(TSRMSTORAGE)[[ST+NNNNNNNN]]
		if(entityname.toUpperCase().equals("SRMSTORAGE"))
		{
			if(currentmax==null||currentmax.equals("")) 
			{
				longmaxPK=1;
			}
			else
			{ 
				strPre=currentmax.substring(2);
				longmaxPK=Long.parseLong(strPre) ;
				longmaxPK=longmaxPK+1;	
			}
			strmaxPK="ST"+String.format("%08d", longmaxPK);
		}
		
		/////"SrmStorageDir/TSRMSTORAGEDIR"  ������Ա(TSRMSTORAGEDIR)[[STORAGEID(10位）+NNNN]]
		if(entityname.toUpperCase().equals("SRMSTORAGEDIR"))
		{
			String storageid="";
			if(currentmax.length()<=10) 
			{
				longmaxPK=1;
				storageid=currentmax;
			}
			else
			{ 
				strPre=currentmax.substring(10);
				storageid=currentmax.substring(0, 10);
				longmaxPK=Long.parseLong(strPre) ;
				longmaxPK=longmaxPK+1;
				
			}
			strmaxPK=storageid+String.format("%04d", longmaxPK);
		}
		
		/////"District/TSRMDISTRICT"  ������Ա(TSRMDISTRICT)[[DI+NNNNNNNN]]
		if(entityname.toUpperCase().equals("DISTRICT"))
		{
			if(currentmax==null||currentmax.equals("")) 
			{
				longmaxPK=1;
			}
			else
			{ 
				strPre=currentmax.substring(2);
				longmaxPK=Long.parseLong(strPre) ;
				longmaxPK=longmaxPK+1;	
			}
			strmaxPK="DI"+String.format("%08d", longmaxPK);
		}
		
		/////"StbCustomer/TSRMSTBCUSTOMER"  ������Ա(TSRMSTBCUSTOMER)[[SC+NNNNNNNN]]
		if(entityname.toUpperCase().equals("STBCUSTOMER"))
		{
			if(currentmax==null||currentmax.equals("")) 
			{
				longmaxPK=1;
			}
			else
			{ 
				strPre=currentmax.substring(2);
				longmaxPK=Long.parseLong(strPre) ;
				longmaxPK=longmaxPK+1;	
			}
			strmaxPK="SC"+String.format("%08d", longmaxPK);
		}
		
		/////"ResServerInfo/TSRMRESSERVERINFO"  ������Ա(TSRMRESSERVERINFO)[[LC+NNNNNNNN]]
		if(entityname.toUpperCase().equals("RESSERVERINFO"))
		{
			if(currentmax==null||currentmax.equals("")) 
			{
				longmaxPK=1;
			}
			else
			{ 
				strPre=currentmax.substring(2);
				longmaxPK=Long.parseLong(strPre) ;
				longmaxPK=longmaxPK+1;	
			}
			strmaxPK="RS"+String.format("%08d", longmaxPK);
		}
		
		/////"IpQqmChannelProgNo/TSRMIPQAMCHANNELPROGNO"  ������Ա(TSRMIPQAMCHANNELPROGNO)[[LC+NNNNNNNN]]
		if(entityname.toUpperCase().equals("IPQAMCHANNELPROGNO"))
		{
			if(currentmax==null||currentmax.equals("")) 
			{
				longmaxPK=1;
			}
			else
			{ 
				strPre=currentmax.substring(2);
				longmaxPK=Long.parseLong(strPre) ;
				longmaxPK=longmaxPK+1;	
			}
			strmaxPK="IP"+String.format("%08d", longmaxPK);
		}
		//////////
		
		//////////
		return strmaxPK;
	
	}

}
