package com.cbs.cbsmgr.serviceSimple;

import java.util.Date;
import java.util.List;

import com.cbs.cbsmgr.bean.Hardware;
import com.cbs.cbsmgr.bean.Product;

public interface SessionProductIface {

	// 捕获产品包
	public Long capturePackage(Long accountId, Long packageId);
	
	// 取消产品
	public Long cancelProduct(Long productId);
	
	// 用户暂停产品
	public Long suspendProduct(Long productId);
	
	// 系统暂停产品
	public Long disconnectProduct(Long productId);
	
	// 恢复产品
	public Long reconnectProduct(Long productId);

	
	// 查询
	// 根据客户ID得到客户产品列表
	public List getProductsByCustomerId(Long customerId);
	
	// 根据customerId，得到ProductDisplayDTO列表
	public List getProductDisplayDTOsByPackageId(Long customerId);
	
	// 根据ProductID得到客户产品
	public Product getProductByProductId(Long productId);
	
	// 得到所有产品包列表
	public List getAllPackages();
	
	// 得到允许捕获的产品包列表
	public List getAllowCapturePackages();
	
	// 根据Package_Id，得到PackageItem列表
	public List getPackageItemByPackageId(Long packageId);
	
	
	
	//************************************************************************************************
	
//	// 创建产品包
//	public Long createPackage(String description, String allowCaptureTag, 
//			Date allowCaptureDateFrom, Date allowCaptureDateTo);
//	
//	// 修改产品
//	public void modifyPackage(Long packageId, String description, String allowCaptureTag, 
//			Date allowCaptureDateFrom, Date allowCaptureDateTo);
//	
//	// 创建产品包单项
//	public Long createPackageItem(Long packageId, Long sequence, Long productCategoryId);
//	
//	// 修改产品包单项
//	//public void modifyPackageItem(Long packageItemId);
//	
//	// 删除产品包单项
//	public void deletePackageItem(Long packageItemId);
	
	
	
	// ---------------- 客户的硬件信息 -------------------------------------------------------------------------
	// 查询
	// 测试通过
	// 根据客户Id查询硬件product
	public List getHardwareProductsByCustomerId(Long customerId);
	
	// 操作
	// 测试通过
	// 添加硬件到客户下
	public void captureHardwareProductToAccountId(Long accountId, Hardware hardware);
	
	// 操作
	// 
	// 添加硬件到客户下
	public void captureHardwareProductToCustomerId(Long customerId, Hardware hardware);
	

}
