package com.cbs.cbsmgr.dto;

/**
 * <p>Title: CONVERGENCE BUSINESS SYSTEM</p>
 * <p>Module: CBS CORE MODULE</p>
 * <p>Description: </p>
 * <p>Company: Road To Broadband Co., Ltd.</p>
 * @author Hu Jin
 * <p>2004/1/14 Revised by Hu Jin
 */
import java.io.Serializable;
import java.util.Date;

import com.cbs.cbsmgr.bean.VodFlow;

public class VodFlowDTO
    implements Serializable {
	
	private VodFlow vodFlow;
	
    private Long productCategoryId;			// 计费用
    private Long productId;					// 计费用
    private Double amount;					// 生成的费用
    private String campaignCategoryId;		// 计费用的优惠ID
    private Date vodFlowCreateDate;			// VodFlow的创建时间
//    private Date createDate;				// 
//    private Long dealState;
//    private Date dealDate; 
	
}