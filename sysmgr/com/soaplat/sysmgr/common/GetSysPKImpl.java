/*
 * 
 */
package com.soaplat.sysmgr.common;

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
public class GetSysPKImpl implements IGetPK {

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.common.IGetPK#GetTablePK(java.lang.String)
	 */
	public String GetTablePK(String entityname) {
		
		String strmaxPK="";
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmssSSSSSS");
		String strcurtime=df.format(new Date());
		/**Rel Management*/
		if(entityname.toUpperCase().equals("ROLEFUNREL"))
		{
			strmaxPK=strcurtime;				
			return strmaxPK;
		}
		//////Rel Management "RoleOperRel/TSOASYSROLEOPERREL"   ��ɫ��Ա��ϵ��(TSOASYSROLEOPERREL)[[YYYYMMDDHHMMSSFFFFFF]]
		if(entityname.toUpperCase().equals("ROLEOPERREL"))
		{
			strmaxPK=strcurtime;				
			return strmaxPK;
		}
		//////Rel Management "GroupOperRel/TSOASYSGROUPOPERREL"  ��������Ա��ϵ��(TSOASYSGROUPOPERREL)[[YYYYMMDDHHMMSSFFFFFF]]
		if(entityname.toUpperCase().equals("GROUPOPERREL"))
		{
			strmaxPK=strcurtime;				
			return strmaxPK;
		}
		/////"GroupRoleRel/TSOASYSGROUPROLEREL"  (TSOASYSGROUPROLEREL)[[YYYYMMDDHHMMSSFFFFFF]]
		if(entityname.toUpperCase().equals("GROUPROLEREL"))
		{
			strmaxPK=strcurtime;				
			return strmaxPK;
		}
		/////"IconSet/TSOASYSICONSET"  (TSOASYSICONSET)[[YYYYMMDDHHMMSSFFFFFF]]
		if(entityname.toUpperCase().equals("ICONSET"))
		{
			strmaxPK=strcurtime;				
			return strmaxPK;
		}
			
		/**DataDict Management*/
		
		//////"DictType/TSOASYSDICTTYPE"	(TSOASYSDICTTYPE)[[GLOBALID]]
		if(entityname.toUpperCase().equals("DICTTYPE"))
		{
			strmaxPK=strcurtime;				
			return strmaxPK;
		}
		
		//////DataDict Management "Dict/TSOASYSDICT" ҵ������ֵ��TSOASYSDICT��[[GLOBALID]]
		if(entityname.toUpperCase().equals("DICT"))
		{
			strmaxPK=strcurtime;				
			return strmaxPK;
		}
		
		return strcurtime;
		
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.common.IGetPK#GetTablePK(java.lang.String, java.lang.String)
	 */
	public String GetTablePK(String entityname, String currentmax) {

		String strmaxPK="";
		String strPre="";
		long longmaxPK;
		
		
		
		
		/////"Operator/TSOASYSOPERATOR"  ������Ա(TSOASYSOPERATOR)[[OP+NNNNNNNN]]
		if(entityname.toUpperCase().equals("OPERATOR"))
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
			strmaxPK="OP"+String.format("%08d", longmaxPK);
		}
		/////"FunResource/TSOASYSFUNRESOURCE"  ������Դ��(TSOASYSFUNRESOURCE)[[FU+NNNNNNNN]]
		if(entityname.toUpperCase().equals("FUNRESOURCE"))
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
			strmaxPK="FU"+String.format("%08d", longmaxPK);
					
		}
		/////"Menu/TSOASYSMENU"   �˵���(TSOASYSMENU)[[MU+NNNNNNNN]]
		if(entityname.toUpperCase().equals("MENU"))
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
			strmaxPK="MU"+String.format("%08d", longmaxPK);
					
		}
		/////"Role/TSOASYSROLE"   ��ɫ��(TSOASYSROLE)[[RO+NNNNNNNN]]
		if(entityname.toUpperCase().equals("ROLE"))
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
			strmaxPK="RO"+String.format("%08d", longmaxPK);	
		}
		/////"Org/TSOASYSORG"   ���ű�(TSOASYSORG)[[OR+NNNNNNNN]]
		if(entityname.toUpperCase().equals("ORG"))
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
			strmaxPK="OR"+String.format("%08d", longmaxPK);	
		}
		/////"Group/TSOASYSGROUP"	(TSOASYSGROUP)[[GR+NNNNNNNN]]
		if(entityname.toUpperCase().equals("GROUP"))
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
			strmaxPK="GR"+String.format("%08d", longmaxPK);	
		}
		return strmaxPK;
	}

}


