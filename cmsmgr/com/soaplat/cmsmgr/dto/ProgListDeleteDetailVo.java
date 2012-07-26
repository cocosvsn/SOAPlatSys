/**
* copyright (c) by A-one 2010
*/
package com.soaplat.cmsmgr.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author：Bunco
 * E-mail: cocosvsn@yahoo.com.cn
 * @date：2011-1-21 下午02:58:49
 */
public class ProgListDeleteDetailVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String	id;
	private String	progPackageId;
	private String	progPackageName;
	private String	siteName;
	private String	siteCode;
	private Date	inputTime;
	
	public ProgListDeleteDetailVo() { }
	
	public ProgListDeleteDetailVo(String id, String progPackageId,
			String progPackageName, String siteName, String siteCode,
			Date inputTime) {
		super();
		this.id = id;
		this.progPackageId = progPackageId;
		this.progPackageName = progPackageName;
		this.siteName = siteName;
		this.siteCode = siteCode;
		this.inputTime = inputTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
}
