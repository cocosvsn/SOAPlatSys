package com.soaplat.cmsmgr.dto;

public class CmsResultDto {

	public Long resultCode;					// 返回结果代码：0 - successful ; 1 - failed
	public Long errorCode;					// 错误代码
	public Object resultObject;				// 返回结果实体
	public String errorMessage;				// 错误消息
	public String errorDetail;				// 错误详细说明
	
	public CmsResultDto()
	{
		// resultCode : 0 - successful ; 1 - failed
		
		resultCode = (long)0;
		errorCode = (long)0;
		resultObject = null;
		errorMessage = "";
		errorDetail = "";
	}

	public Long getResultCode() {
		return resultCode;
	}

	public void setResultCode(Long resultCode) {
		this.resultCode = resultCode;
	}

	public Long getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Long errorCode) {
		this.errorCode = errorCode;
	}

	public Object getResultObject() {
		return resultObject;
	}

	public void setResultObject(Object resultObject) {
		this.resultObject = resultObject;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorDetail() {
		return errorDetail;
	}

	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}

}
