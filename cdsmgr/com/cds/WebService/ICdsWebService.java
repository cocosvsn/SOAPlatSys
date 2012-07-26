package com.cds.WebService;

import javax.jws.WebService;

import com.cbs.cbsmgr.bean.VodSendDetail;
import com.soaplat.amsmgr.bean.AmsStoragePrgRel;

@WebService
public interface ICdsWebService {
	public void AmsStoragePrgRelServiceSave(AmsStoragePrgRel object);
	public void VodSendDetailServiceUpdate(VodSendDetail object);
}
