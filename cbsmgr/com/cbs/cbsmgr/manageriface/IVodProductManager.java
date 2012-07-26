package com.cbs.cbsmgr.manageriface;

import java.util.List;

public interface IVodProductManager extends IBaseManager {

	// ���ݽ�ĿID����ѯ��VodProgramPackageRel VodProduct���õ�VodProductId
	public String getVodProductIdByProgramId(String programId);
	
	// ��ѯ�ײ��µĵ㲥��Ʒ
	// ������productCategoryId
	// ��ѯ��vodCbsProductRel��VodProduct,VodPackage,VodDisplayCategory
	// hql:
//	select vpt.vodProductId,vpt.type,vpt.billingType,vpt.price,vpt.validFrom,vpt.validTo,
//	vpt.sendTag,vpt.packagePrice,vpg.vodPackageId,vpg.ptglobalid,vpg.description,vpg.subtitlelanguage,
//	vpg.audiolanguage,vpg.director,vpg.productname,vpg.actors,vpg.shootdate,vpg.issuedate,
//	vpg.countrydist,vpg.inputmanid,vpg.inputtime,vpg.remark,vdc.vodDisplayCategoryId,vdc.title
//	from vodCbsProductRel vcp, VodProduct vpt, VodPackage vpg, VodDisplayCategory vdc
//	where vcp.productCategoryId = :productCategoryId and vcp.vodProductId = vpt.vodProductId and vpt.vodPackageId = vpg.vodPackageId and vpg.vodDisplayCategoryId = vdc.vodDisplayCategoryId
	public List getVodProductsByProductCategoryId(Long productCategoryId);
}
