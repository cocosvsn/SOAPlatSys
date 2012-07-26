package com.soaplat.cmsmgr.dto;

import java.util.Date;

import com.soaplat.cmsmgr.bean.CmsService;
import com.soaplat.cmsmgr.bean.PortalColumn;

public class PortalColumnDto {

	// PortalColumn
//	private String columnclassid;
//    private String siteid;
//    private String columnname;
//    private String defcatcode;
//    private String isleaf;
//    private Long defcatlevel;
//    private String rootid;
//    private String parentsid;
//    private Long displayorder;
//    private String defcatseq;
//    private String inputmanid;
//    private Date inputtime;
//    private String remark;
//    private Long ispublish;
//    private String publishfilename;
//    private String parentdir;
//    private String currentdir;
//    private String domainname;
//    private Long isdomainsub;
//    private Long countnumb;
//    private Long countonepage;
//    private String listmodeid;
//    private String covermodeid;
//    private String contentmodeid;
//    private Long isallchange;
//    private Date updatedate;
//    private Long archivedays;
    
	private PortalColumn portalColumn;
    private String columnNameOrder;
    
    public PortalColumnDto()
    {
    	
    }
    
    public PortalColumnDto(PortalColumn portalColumn)
    {
    	this.portalColumn = portalColumn;
//    	this.columnclassid = portalColumn.getColumnclassid();
//    	this.siteid = portalColumn.getSiteid();
//    	this.columnname = portalColumn.getColumnname();
//    	this.defcatcode = portalColumn.getDefcatcode();
//    	this.isleaf = portalColumn.getIsleaf();
//    	this.defcatlevel = portalColumn.getDefcatlevel();
//    	this.rootid = portalColumn.getRootid();
//    	this.parentsid = portalColumn.getParentsid();
//    	this.displayorder = portalColumn.getDisplayorder();
//    	this.defcatseq = portalColumn.getDefcatseq();
//    	this.inputmanid = portalColumn.getInputmanid();
//    	this.inputtime = portalColumn.getInputtime();
//    	this.remark = portalColumn.getRemark();
//    	this.ispublish = portalColumn.getIspublish();
//    	this.publishfilename = portalColumn.getPublishfilename();
//    	this.parentdir = portalColumn.getParentdir();
//    	this.currentdir = portalColumn.getCurrentdir();
//    	this.domainname = portalColumn.getDomainname();
//    	this.isdomainsub = portalColumn.getIsdomainsub();
//    	this.countnumb = portalColumn.getCountnumb();
//    	this.countonepage = portalColumn.getCountonepage();
//    	this.listmodeid = portalColumn.getListmodeid();
//    	this.covermodeid = portalColumn.getCovermodeid();
//    	this.contentmodeid = portalColumn.getContentmodeid();
//    	this.isallchange = portalColumn.getIsallchange();
//    	this.updatedate = portalColumn.getUpdatedate();
//    	this.archivedays = portalColumn.getArchivedays();
        
    	this.columnNameOrder = portalColumn.getColumnname() + "[" + portalColumn.getDisplayorder() + "]";
    }

	public String getColumnNameOrder() {
		return columnNameOrder;
	}

	public void setColumnNameOrder(String columnNameOrder) {
		this.columnNameOrder = columnNameOrder;
	}

	public PortalColumn getPortalColumn() {
		return portalColumn;
	}

	public void setPortalColumn(PortalColumn portalColumn) {
		this.portalColumn = portalColumn;
	}
    

    

}
