package com.cds.WebService;

import javax.jws.WebService;

import com.soaplat.amsmgr.bean.AmsStoragePrgRel;
import com.soaplat.amsmgr.serviceimpl.*;
import com.cbs.cbsmgr.bean.VodSendDetail;
import com.cbs.cbsmgr.serviceimpl.*;
@WebService
public class CdsWebServiceImpl implements ICdsWebService {
		public void AmsStoragePrgRelServiceSave(AmsStoragePrgRel object) {
			AmsStoragePrgRelService aps = new AmsStoragePrgRelService();
			aps.save(object);			
		}
		public void VodSendDetailServiceUpdate(VodSendDetail object) {
			VodSendDetailService vds = new VodSendDetailService();
			vds.update(object);			
		}
}
