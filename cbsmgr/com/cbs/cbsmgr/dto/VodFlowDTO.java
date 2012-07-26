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
	
    private Long productCategoryId;			// �Ʒ���
    private Long productId;					// �Ʒ���
    private Double amount;					// ���ɵķ���
    private String campaignCategoryId;		// �Ʒ��õ��Ż�ID
    private Date vodFlowCreateDate;			// VodFlow�Ĵ���ʱ��
//    private Date createDate;				// 
//    private Long dealState;
//    private Date dealDate; 
	
}