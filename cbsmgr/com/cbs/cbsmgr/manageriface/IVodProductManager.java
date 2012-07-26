package com.cbs.cbsmgr.manageriface;

import java.util.List;

public interface IVodProductManager extends IBaseManager {

	// 根据节目ID，查询表VodProgramPackageRel VodProduct，得到VodProductId
	public String getVodProductIdByProgramId(String programId);
	
	// 查询套餐下的点播产品
	// 条件：productCategoryId
	// 查询表：vodCbsProductRel，VodProduct,VodPackage,VodDisplayCategory
	// hql:
//	select vpt.vodProductId,vpt.type,vpt.billingType,vpt.price,vpt.validFrom,vpt.validTo,
//	vpt.sendTag,vpt.packagePrice,vpg.vodPackageId,vpg.ptglobalid,vpg.description,vpg.subtitlelanguage,
//	vpg.audiolanguage,vpg.director,vpg.productname,vpg.actors,vpg.shootdate,vpg.issuedate,
//	vpg.countrydist,vpg.inputmanid,vpg.inputtime,vpg.remark,vdc.vodDisplayCategoryId,vdc.title
//	from vodCbsProductRel vcp, VodProduct vpt, VodPackage vpg, VodDisplayCategory vdc
//	where vcp.productCategoryId = :productCategoryId and vcp.vodProductId = vpt.vodProductId and vpt.vodPackageId = vpg.vodPackageId and vpg.vodDisplayCategoryId = vdc.vodDisplayCategoryId
	public List getVodProductsByProductCategoryId(Long productCategoryId);
}
