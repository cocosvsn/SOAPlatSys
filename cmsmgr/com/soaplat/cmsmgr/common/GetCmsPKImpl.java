/**
 * 
 */
package com.soaplat.cmsmgr.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.soaplat.sysmgr.common.IGetPK;

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
public class GetCmsPKImpl implements IGetPK {

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.common.IGetPK#GetTablePK(java.lang.String)
	 */
	public String GetTablePK(String entityname) {

		String strmaxPK = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSSSSS");
		String strcurtime = df.format(new Date());
		/** Rel Management */

		if (entityname.toUpperCase().equals("OFFLINEPROG")) {
			strmaxPK = strcurtime;
			return strmaxPK;
		}
		
		if (entityname.toUpperCase().equals("PROGPRACT")) {
			strmaxPK = strcurtime;
			return strmaxPK;
		}
		
		if (entityname.toUpperCase().equals("PROGCOPYRIGHT")) {
			strmaxPK = strcurtime;
			return strmaxPK;
		}
		if (entityname.toUpperCase().equals("PTPMREL")) {
			strmaxPK = strcurtime;
			return strmaxPK;
		}

		/** DataDict Management */

		if (entityname.toUpperCase().equals("PROGPUBLISHER")) {
			strmaxPK = strcurtime;
			return strmaxPK;
		}

		if (entityname.toUpperCase().equals("PROGCREATOR")) {
			strmaxPK = strcurtime;
			return strmaxPK;
		}

		if (entityname.toUpperCase().equals("PROGAWARD")) {
			strmaxPK = strcurtime;
			return strmaxPK;
		}

		if (entityname.toUpperCase().equals("PROGINDEXINFO")) {
			strmaxPK = strcurtime;
			return strmaxPK;
		}

		if (entityname.toUpperCase().equals("PROGLICENCES")) {
			strmaxPK = strcurtime;
			return strmaxPK;
		}
		
		if (entityname.toUpperCase().equals("PROGFRAMEINFO")) {
			strmaxPK = strcurtime;
			return strmaxPK;
		}
		
		if (entityname.toUpperCase().equals("PROGDUCTINFO")) {
			strmaxPK = strcurtime;
			return strmaxPK;
		}

		if (entityname.toUpperCase().equals("PROGLISTMANG")) {
			strmaxPK = strcurtime;
			return strmaxPK;
		}
		
		if (entityname.toUpperCase().equals("AMSSTORAGEPRGREL")) {
			strmaxPK = strcurtime;
			return strmaxPK;
		}
		
		return strcurtime;
	}

	/* (non-Javadoc)
	 * @see com.soaplat.sysmgr.common.IGetPK#GetTablePK(java.lang.String, java.lang.String)
	 */
	public String GetTablePK(String entityname, String currentmax) {

		String strmaxPK = "";
		String strPre = "";
		long longmaxPK;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSSSSS");
		String strcurtime = df.format(new Date());

		// Edited by Andy at 20091029
		if (entityname.toUpperCase().equals("PACKAGEFILES")
				|| entityname.toUpperCase().equals("PACKSTYLE")
				|| entityname.toUpperCase().equals("PACKSTYLEFILETYPE")
				|| entityname.toUpperCase().equals("BRCPRODUCT")) {
			if (currentmax == null || currentmax.equals("")
					|| currentmax.equals("0")) {
				longmaxPK = 1;
			} else {
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre);
				longmaxPK = longmaxPK + 1;
			}
			strmaxPK = String.format("%d", longmaxPK);
		} else if (entityname.toUpperCase().equals("PPCOLUMNREL")
				|| entityname.toUpperCase().equals("PROGPPREL")) {
			if (currentmax == null || currentmax.equals("")
					|| currentmax.equals("0")) {
				longmaxPK = 1;
			} else {
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre);
				longmaxPK = longmaxPK + 1;
			}
			strmaxPK = String.format("%010d", longmaxPK);
		} else if (entityname.toUpperCase().equals("PROGSRVREL")) {
			if (currentmax == null || currentmax.equals("")
					|| currentmax.equals("0")) {
				longmaxPK = 1;
			} else {
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre);
				longmaxPK = longmaxPK + 1;
			}
			strmaxPK = "PS" + String.format("%08d", longmaxPK);
		} else if (entityname.toUpperCase().equals("SRVCOLUMN")) {
			if (currentmax == null || currentmax.equals("")
					|| currentmax.equals("0")) {
				longmaxPK = 1;
			} else {
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre);
				longmaxPK = longmaxPK + 1;
			}
			strmaxPK = "SC" + String.format("%08d", longmaxPK);
		} else if (entityname.toUpperCase().equals("SRVPRODUCT")
				|| entityname.toUpperCase().equals("SRVPROGCLASS")) {
			if (currentmax == null || currentmax.equals("")
					|| currentmax.equals("0")) {
				longmaxPK = 1;
			} else {
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre);
				longmaxPK = longmaxPK + 1;
			}
			strmaxPK = "SP" + String.format("%08d", longmaxPK);
		} else if (entityname.toUpperCase().equals("CMSSERVICE")) {
			if (currentmax == null || currentmax.equals("")
					|| currentmax.equals("0")) {
				longmaxPK = 1;
			} else {
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre);
				longmaxPK = longmaxPK + 1;
			}
			strmaxPK = "SV" + String.format("%08d", longmaxPK);
		} else if (entityname.toUpperCase().equals("PROGLIST")) {
			if (currentmax == null || currentmax.equals("")
					|| currentmax.equals("0")) {
				longmaxPK = 1;
			} else {
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre);
				longmaxPK = longmaxPK + 1;
			}
			strmaxPK = "PL" + String.format("%08d", longmaxPK);
		} else if (entityname.toUpperCase().equals("PROGLISTDETAIL")) {
			if (currentmax == null || currentmax.equals("")
					|| currentmax.equals("0")) {
				longmaxPK = 1;
			} else {
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre);
				longmaxPK = longmaxPK + 1;
			}
			strmaxPK = "PD" + String.format("%08d", longmaxPK);
		} else if (entityname.toUpperCase().equals("PORTALCOLUMN")) {
			if (currentmax == null || currentmax.equals("")
					|| currentmax.equals("0")) {
				longmaxPK = 1;
			} else {
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre);
				longmaxPK = longmaxPK + 1;
			}
			strmaxPK = "PC" + String.format("%08d", longmaxPK);
		} else if (entityname.toUpperCase().equals("CMSSITE")) {
			if (currentmax == null || currentmax.equals("")
					|| currentmax.equals("0")) {
				longmaxPK = 1;
			} else {
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre);
				longmaxPK = longmaxPK + 1;
			}
			strmaxPK = "CS" + String.format("%08d", longmaxPK);
		} else if (entityname.toUpperCase().equals("PORTALMOD")) {
			if (currentmax == null || currentmax.equals("")
					|| currentmax.equals("0")) {
				longmaxPK = 1;
			} else {
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre);
				longmaxPK = longmaxPK + 1;
			}
			strmaxPK = "PM" + String.format("%08d", longmaxPK);
		} else if (entityname.toUpperCase().equals("PORTALMODTYPE")) {
			if (currentmax == null || currentmax.equals("")
					|| currentmax.equals("0")) {
				longmaxPK = 1;
			} else {
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre);
				longmaxPK = longmaxPK + 1;
			}
			strmaxPK = "PM" + String.format("%08d", longmaxPK);
		} else if (entityname.toUpperCase().equals("PORTALROLEOPERREL")) {
			if (currentmax == null || currentmax.equals("")
					|| currentmax.equals("0")) {
				longmaxPK = 1;
			} else {
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre);
				longmaxPK = longmaxPK + 1;
			}
			strmaxPK = "PR" + String.format("%08d", longmaxPK);
		} else if (entityname.toUpperCase().equals("PROGLISTMANGDETAIL")) {
			// �������
			if (currentmax == null || currentmax.equals("")
					|| currentmax.equals("0")) {
				longmaxPK = 1;
			} else {
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre);
				longmaxPK = longmaxPK + 1;
			}
			strmaxPK = "PL" + String.format("%08d", longmaxPK);
		} else if (entityname.toUpperCase().equals("PROGLISTFILE")) {
			if (currentmax == null || currentmax.equals("")
					|| currentmax.equals("0")) {
				longmaxPK = 1;
			} else {
				strPre = currentmax.replaceAll("PL", "");
				longmaxPK = Long.parseLong(strPre);
				longmaxPK = longmaxPK + 1;
			}
			strmaxPK = "PL" + String.format("%010d", longmaxPK);
		} else if (entityname.toUpperCase().equals("ENCRYPTLIST")) {
			if (currentmax == null || currentmax.equals("")
					|| currentmax.equals("0")) {
				longmaxPK = 1;
			} else {
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre);
				longmaxPK = longmaxPK + 1;
			}
			strmaxPK = "00" + String.format("%08d", longmaxPK);
		} else if (entityname.toUpperCase().equals("STYLEREL")) {
			if (currentmax == null || currentmax.equals("")
					|| currentmax.equals("0")) {
				longmaxPK = 1;
			} else {
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre);
				longmaxPK = longmaxPK + 1;
			}
			strmaxPK = "SR" + String.format("%08d", longmaxPK);
		} else if (entityname.toUpperCase().equals("PROGPACKAGE")) {
			int pos = currentmax.indexOf("|");
			String progtype = currentmax.substring(0, pos);
			String progproperty = currentmax.substring(pos + 1);
			strmaxPK = "PP" + progtype + progproperty + strcurtime;

			return strmaxPK;
		}

		if (entityname.toUpperCase().equals("OFFLINESTORAGE")) {
			if (currentmax == null || currentmax.equals("")) {
				longmaxPK = 1;
			} else {
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre);
				longmaxPK = longmaxPK + 1;
			}
			strmaxPK = "BR" + String.format("%08d", longmaxPK);
		}
		
		if (entityname.toUpperCase().equals("SYSDEFCAT")) {
			if (currentmax == null || currentmax.equals("")) {
				longmaxPK = 1;
			} else {
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre);
				longmaxPK = longmaxPK + 1;
			}
			strmaxPK = "FU" + String.format("%08d", longmaxPK);

		}
		
		if (entityname.toUpperCase().equals("PROGRAMINFO")) {
			int currentmaxlength = currentmax.length();
			int pos = currentmax.indexOf("|");
			String progtype = currentmax.substring(0, pos);
			String progproperty = currentmax.substring(pos + 1);
			strmaxPK = "PR" + progtype + progproperty + strcurtime;
		}
		
		if (entityname.toUpperCase().equals("PROGRAMFILES")) {
			// 这种写法好像有问题，当表为空的时候，currentmax取出来是null
			if (currentmax == null || currentmax.length() <= 27) {
				longmaxPK = 1;
			} else {
				strPre = currentmax.substring(27);
				longmaxPK = Long.parseLong(strPre);
				longmaxPK = longmaxPK + 1;
			}
			strmaxPK = currentmax.substring(0, 27)
					+ String.format("%03d", longmaxPK);
		}
		
		if (entityname.toUpperCase().equals("STYLEREL")
				|| entityname.toUpperCase().equals("PACKSTYLEPORTALCOLUMN")) {
			if (currentmax == null || currentmax.equals("")) {
				longmaxPK = 1;
			} else {
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre);
				longmaxPK = longmaxPK + 1;
			}
			strmaxPK = "00" + String.format("%08d", longmaxPK);
		}
		
		if (entityname.toUpperCase().equals("PORTALJSRULES")) {
			if (currentmax == null || currentmax.equals("")) {
				longmaxPK = 1;
			} else {
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre);
				longmaxPK = longmaxPK + 1;
			}
			strmaxPK = "00" + String.format("%08d", longmaxPK);
		}
		
		if (entityname.toUpperCase().equals("PORTALPACKAGE")) {
			if (currentmax == null || currentmax.equals("")) {
				longmaxPK = 1;
			} else {
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre);
				longmaxPK = longmaxPK + 1;
			}
			strmaxPK = "00" + String.format("%08d", longmaxPK);
		}
		
		if (entityname.toUpperCase().equals("PTPPGPREL")) {
			if (currentmax == null || currentmax.equals("")) {
				longmaxPK = 1;
			} else {
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre);
				longmaxPK = longmaxPK + 1;
			}
			strmaxPK = "00" + String.format("%08d", longmaxPK);
		}
		
		if (entityname.equalsIgnoreCase("ProgListDeleteDetail")) {
			if (currentmax == null || currentmax.equals("")) {
				longmaxPK = 1;
			} else {
				strPre = currentmax.substring(2);
				longmaxPK = Long.parseLong(strPre);
				longmaxPK = longmaxPK + 1;
			}
			strmaxPK = "PD" + String.format("%08d", longmaxPK);
		}
		return strmaxPK;
	}
}


