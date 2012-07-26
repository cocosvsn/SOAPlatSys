/**
 * copyright (c) by A-one 2010
 */
package com.soaplat.cmsmgr.bean;

/**
 * Title 		: 节目包文件关系类
 * Description	: 
 * Company		: A-one
 * E-mail		: cocosvsn@yahoo.com.cn, b_huang@sbl.sh.cn
 * @author		: Bunco
 * @version		: 1.0
 * @date		 ：2010年8月27日 10时13分
 */
public class PackageFiles  implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    // Fields    
	private Long cmspackageFilesid;	// 主键ID
    private String productid;		// 节目包 (ProgPackage) ID
    private String programid;		// 
    private String progfileid;		// 文件 (ProgramFiles) ID
    private String figlobalid;

    // Constructors

    /** default constructor */
    public PackageFiles() { }

    
	/** full constructor */
	public PackageFiles(String productid, String programid, String progfileid,
			String figlobalid) {
		this.productid = productid;
		this.programid = programid;
		this.progfileid = progfileid;
		this.figlobalid = figlobalid;
	}

   
    // Property accessors
	/**
	 * 主键ID
	 * @return 
	 */
    public Long getCmspackageFilesid() {
        return this.cmspackageFilesid;
    }
    
    /**
     * 主键ID
     * @param cmspackageFilesid
     */
    public void setCmspackageFilesid(Long cmspackageFilesid) {
        this.cmspackageFilesid = cmspackageFilesid;
    }

    /**
     * 节目包 (ProgPackage) ID
     * @return
     */
    public String getProductid() {
        return this.productid;
    }
    
    /**
     * 节目包 (ProgPackage) ID
     * @param productid
     */
    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProgramid() {
        return this.programid;
    }
    
    public void setProgramid(String programid) {
        this.programid = programid;
    }

    /**
     * 文件 (ProgramFiles) ID
     * @return
     */
    public String getProgfileid() {
        return this.progfileid;
    }
    
    /**
     * 文件 (ProgramFiles) ID
     * @param progfileid
     */
    public void setProgfileid(String progfileid) {
        this.progfileid = progfileid;
    }

    public String getFiglobalid() {
        return this.figlobalid;
    }
    
    public void setFiglobalid(String figlobalid) {
        this.figlobalid = figlobalid;
    }
   








}