/**
* copyright (c) by A-one 2010
*/
package com.soaplat.cmsmgr.dto;

import java.io.Serializable;

import com.soaplat.cmsmgr.common.FileOperationImpl;

/**
 * @author：Bunco
 * E-mail: cocosvsn@yahoo.com.cn
 * @date：2011-1-18 下午05:22:12
 */
public class ProgListDetailVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String	progPackageId;
	private String	progPackageName;
	private String	columnId;
	private String	columnName;
	private long	attachmentStatusCode;
	private String	attachmentStatusName;
	private long	mainFileStatusCode;
	private String	mainFileStatusName;
	private String	productInfoId;
	private String	productInfoName;
	
	public ProgListDetailVO() { }
	
	public ProgListDetailVO(String progPackageId, String progPackageName,
			String columnId, String columnName, long attachmentStatusCode, 
			long mainFileStatusCode, String productInfoId, String productInfoName) {
		super();
		this.progPackageId = progPackageId;
		this.progPackageName = progPackageName;
		this.columnId = columnId;
		this.columnName = columnName;
		this.attachmentStatusCode = attachmentStatusCode;
		this.attachmentStatusName = FileOperationImpl.getState(
				attachmentStatusCode);
		this.mainFileStatusCode = mainFileStatusCode;
		this.mainFileStatusName = (0 == mainFileStatusCode && -1 ==  attachmentStatusCode 
				? "未导入" 
						: FileOperationImpl.getMainFileState(
				mainFileStatusCode));
		this.productInfoId = productInfoId;
		this.productInfoName = productInfoName;
	}

	public String getProgPackageId() {
		return progPackageId;
	}

	public void setProgPackageId(String progPackageId) {
		this.progPackageId = progPackageId;
	}

	public String getProgPackageName() {
		return progPackageName;
	}

	public void setProgPackageName(String progPackageName) {
		this.progPackageName = progPackageName;
	}

	public String getColumnId() {
		return columnId;
	}
	
	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}
	
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public long getAttachmentStatusCode() {
		return attachmentStatusCode;
	}
	
	public void setAttachmentStatusCode(long attachmentStatusCode) {
		this.attachmentStatusCode = attachmentStatusCode;
		this.attachmentStatusName = FileOperationImpl.getState(
				attachmentStatusCode);
	}
	
	public String getAttachmentStatusName() {
		return attachmentStatusName;
	}

	public void setAttachmentStatusName(String attachmentStatusName) {
		this.attachmentStatusName = attachmentStatusName;
	}
	
	public long getMainFileStatusCode() {
		return mainFileStatusCode;
	}
	
	public void setMainFileStatusCode(long mainFileStatusCode) {
		this.mainFileStatusCode = mainFileStatusCode;
		this.mainFileStatusName = (0 == mainFileStatusCode && -1 ==  attachmentStatusCode 
				? "未导入" 
						: FileOperationImpl.getMainFileState(
				mainFileStatusCode));
	}

	public String getMainFileStatusName() {
		return mainFileStatusName;
	}

	public void setMainFileStatusName(String mainFileStatusName) {
		this.mainFileStatusName = mainFileStatusName;
	}

	public String getProductInfoId() {
		return productInfoId;
	}
	
	public void setProductInfoId(String productInfoId) {
		this.productInfoId = productInfoId;
	}
	
	public String getProductInfoName() {
		return productInfoName;
	}

	public void setProductInfoName(String productInfoName) {
		this.productInfoName = productInfoName;
	}
}
