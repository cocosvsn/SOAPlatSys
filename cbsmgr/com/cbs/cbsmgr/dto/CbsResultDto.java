package com.cbs.cbsmgr.dto;

public class CbsResultDto {

	public Long resultCode;
	public Long errorCode;
	public Object resultObject;
	public String errorMessage;
	public String errorDetail;
	
	public CbsResultDto()
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
