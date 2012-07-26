/**
 * copyright (c) by A-one 2010
 */
package com.soaplat.cmsmgr.bean;

import java.util.Date;

/**
 * Title 		: 品牌类
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn, b_huang@sbl.sh.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		 ：2010年9月8日 17时25分
 */
public class CmsSite  implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    // Fields    
	private String	siteid;			// 主键ID
	private String	sitename;		// 品牌名称
	private String	siteCode;		// 品牌Code
	private String	epgpath;			// 节目预告文件名
    private String	epgip;			// 节目预告JS变量
    private Date	updateTime;
    private String	remark;
//     private String filepath;
//     private Date validFrom;
//     private Date validTo;


    // Constructors

    /** default constructor */
    public CmsSite() {
    }

	/** minimal constructor */
    public CmsSite(String siteid) {
        this.siteid = siteid;
    }
    
    /** full constructor */
    public CmsSite(String siteid, String sitename, String siteCode) {
        this.siteid = siteid;
        this.sitename = sitename;
        this.siteCode = siteCode;
    }

   
    // Property accessors

    /**
     * 主键ID
     * @return
     */
    public String getSiteid() {
        return this.siteid;
    }
    
    /**
     * 主键ID
     * @param siteid
     */
    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    /**
     * 品牌名称
     * @return
     */
    public String getSitename() {
        return this.sitename;
    }
    
    /**
     * 品牌名称
     * @param sitename
     */
    public void setSitename(String sitename) {
        this.sitename = sitename;
    }
	
    /**
     * 品牌Code
     * @return
     */
    public String getSiteCode() {
		return siteCode;
	}
	
    /**
     * 品牌Code
     * @param siteCode
     */

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	/**
	 * 节目预告文件名
	 * @return
	 */
	public String getEpgpath() {
		return epgpath;
	}
	
	/**
	 * 节目预告文件名
	 * @param epgpath
	 */
	public void setEpgpath(String epgpath) {
		this.epgpath = epgpath;
	}

	/**
	 * 节目预告JS变量
	 * @return
	 */
	public String getEpgip() {
		return epgip;
	}
	
	/**
	 * 节目预告JS变量
	 * @param epgip
	 */
	public void setEpgip(String epgip) {
		this.epgip = epgip;
	}

	/**
	 * 更新时间
	 * @return
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	
	/**
	 * 更新时间
	 * @param updateTime
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	/**
	 * 品牌描述
	 * @return
	 */
	public String getRemark() {
		return remark;
	}
	
	/**
	 * 品牌描述
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
}