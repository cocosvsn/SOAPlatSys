package com.cbs.cbsmgr.serviceBackground;

import java.util.Date;

import com.cbs.cbsmgr.dto.CbsResultDto;

public interface MonthlyBillingIface {

	// 生成每月套餐费用
	public void generateMonthlyFeeByAccountId(Long accountId);
	
	// 查询每月套餐费用
	
	// 根据点播历史表，汇总每月点播费用，生成点播用财务交易记录
	
	// 每月帐单生成
	
	
	
	
	
	// 新需求，新开发
	// -------------------------------------- 20091223 ---------------------------------------------------------------
	// 汇总状态为未处理的点播历史到财务交易，每月固定时间，执行一次
	public CbsResultDto collectVodHistoriesToFt(
//			Date date
			);
	
	// 生成周期性费用，每客户的计费到期时间，每天只执行一次
	public CbsResultDto generatePeriodFeeToFt(Date date);
	
	// 生成账单，每月固定时间，执行一次
	public CbsResultDto createInvoices(Date date, String dueDate);
	
	// 清理VodHistoryTemp
	
	
	
	public CbsResultDto executePeriodly();
}
