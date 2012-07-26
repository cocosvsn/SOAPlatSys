package com.cbs.cbsmgr.serviceBackground;

import java.util.Date;
import java.util.List;

import com.cbs.cbsmgr.dto.CbsResultDto;
import com.cbs.cbsmgr.dto.VodFlowDTO;

public interface VodBillingIface {

	// 查询所有点播流水xml

	// 根据客户ID查询点播流水列表
//	public List getVodFlowDTOsByCustomerId(Long customerId, Date dateFrom, Date dateTo);
		
	// 读取点播流水接口文件，分析点播流水，计算费用，生成费用，迁移点播流水接口文件
//	public void dealVodFlowByVodFlowXml() throws Exception;
	
	// 调用点播流水接口，得到点播流水列表，分析点播流水，计算费用，生成费用，迁移点播流水接口文件
//	public void dealVodFlowByInterface() throws Exception;
	
	
	
	
	
	// 经过讨论，新一轮的修改开始，如下：	20091222
	// 点播流水导入
	// 点播流水筛选
	// 点播费用计算
	// 点播历史生成
	
	// 导入点播流水，文件 --> 数据库记录
	public CbsResultDto importVodFlowsFromFilesToDB() throws Exception;
	
	// 点播流水筛选、费用计算，最后生成点播历史
	public CbsResultDto calculateVodFeeByVodFlow();
}
