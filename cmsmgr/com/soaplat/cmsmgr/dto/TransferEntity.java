package com.soaplat.cmsmgr.dto;

public class TransferEntity {

	public String sourceProgid = null;
	public String sourceProgTitle = null;
	public String sourceFileId = null;
	public String sourceFileName = null;
	public String sourceStorageClass = null;
	public String desStorageClass = null;
	public String sourceFileType = null;
	public String sourceFileCode = null;
	public String priorityDate = null;
	public String isCover = null;
	
	public String mainFileTag = null;
	public String progFileId = null;
	public String productInfoId = null;
	
	public TransferSource transferSource = new TransferSource();
	public TransferDestination transferDestination = new TransferDestination();
	
	
	
	
	
	public String getSourceProgid() {
		return sourceProgid;
	}
	public void setSourceProgid(String sourceProgid) {
		this.sourceProgid = sourceProgid;
	}
	public String getSourceProgTitle() {
		return sourceProgTitle;
	}
	public void setSourceProgTitle(String sourceProgTitle) {
		this.sourceProgTitle = sourceProgTitle;
	}
	public String getSourceFileId() {
		return sourceFileId;
	}
	public void setSourceFileId(String sourceFileId) {
		this.sourceFileId = sourceFileId;
	}
	public String getSourceFileName() {
		return sourceFileName;
	}
	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}
	public String getSourceStorageClass() {
		return sourceStorageClass;
	}
	public void setSourceStorageClass(String sourceStorageClass) {
		this.sourceStorageClass = sourceStorageClass;
	}
	public String getDesStorageClass() {
		return desStorageClass;
	}
	public void setDesStorageClass(String desStorageClass) {
		this.desStorageClass = desStorageClass;
	}
	public String getSourceFileType() {
		return sourceFileType;
	}
	public void setSourceFileType(String sourceFileType) {
		this.sourceFileType = sourceFileType;
	}
	public String getPriorityDate() {
		return priorityDate;
	}
	public void setPriorityDate(String priorityDate) {
		this.priorityDate = priorityDate;
	}
	public String getIsCover() {
		return isCover;
	}
	public void setIsCover(String isCover) {
		this.isCover = isCover;
	}
	public TransferSource getTransferSource() {
		return transferSource;
	}
	public void setTransferSource(TransferSource transferSource) {
		this.transferSource = transferSource;
	}
	public TransferDestination getTransferDestination() {
		return transferDestination;
	}
	public void setTransferDestination(TransferDestination transferDestination) {
		this.transferDestination = transferDestination;
	}
	public String getSourceFileCode() {
		return sourceFileCode;
	}
	public void setSourceFileCode(String sourceFileCode) {
		this.sourceFileCode = sourceFileCode;
	}
	public String getMainFileTag() {
		return mainFileTag;
	}
	public void setMainFileTag(String mainFileTag) {
		this.mainFileTag = mainFileTag;
	}
	public String getProgFileId() {
		return progFileId;
	}
	public void setProgFileId(String progFileId) {
		this.progFileId = progFileId;
	}
	public String getProductInfoId() {
		return productInfoId;
	}
	public void setProductInfoId(String productInfoId) {
		this.productInfoId = productInfoId;
	}
	
	
	
	
	
}
