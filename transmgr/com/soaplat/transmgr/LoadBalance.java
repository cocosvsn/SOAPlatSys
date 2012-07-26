package com.soaplat.transmgr;

import java.util.Iterator;
import java.util.List;

import jcifs.smb.SmbFile;

public class LoadBalance {
	/**
	 * @param list
	 * @return Object[]<br/>
	 * AmsStorage.stglobalid
 	 * AmsStorage.storagename
	 * AmsStorage.storageip
	 * AmsStorage.storageaccstype
	 * AmsStorage.loginname
	 * AmsStorage.loginpwd
	 * AmsStorage.mappath
	 * AmsStorage.maploginuid
	 * AmsStorage.maploginpwd
	 * AmsStorage.maplogindisk
	 * AmsStorage.totalcap
	 * AmsStorage.districtid
	 * AmsStorage.storagevalid
	 * AmsStorageDir.storagedirname
	 * AmsStorageDir.dirtotalcap
	 * AmsStorageDir.diralarmcap
	 * AmsStorageDir.dirfreecap
	 */
	public static Object[] rtnStorage(List list) {
		Object[] bestObj = null;
		Iterator<Object[]> it = list.iterator();
		long largeSize = 0L;
		while (it.hasNext()) {
			Object[] tempObj = it.next();
			String smbPath = "smb://";
			if ((tempObj[4] != null)
					&& (!("".equalsIgnoreCase(((String) tempObj[4]).trim())))) {
				smbPath = smbPath + tempObj[4] + ":";
				if (tempObj[5] != null) {
					smbPath += tempObj[5];
				}
				smbPath += "@";
			}
			smbPath = smbPath + tempObj[2] + "/" + tempObj[6];
			long tempSize = returnDiskFreeSpace(smbPath);
			if (tempSize > largeSize || bestObj == null) {
				largeSize = tempSize;
				bestObj = tempObj;
			}
		}
		return bestObj;
	}

	public static long returnDiskFreeSpace(String smbPath) {
		try {
			SmbFile smbFile = new SmbFile(smbPath);
			return smbFile.getDiskFreeSpace();
		} catch (Exception e) {
			return -1L;
		}
	}
}