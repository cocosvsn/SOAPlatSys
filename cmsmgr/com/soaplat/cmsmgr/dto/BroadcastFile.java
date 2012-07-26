package com.soaplat.cmsmgr.dto;

public class BroadcastFile {

	String progfileid = null;
	String filename = null;
	String contentid = null;
	String progrank = null;
	String filecode = null;
	String filetypeid = null;
	String src = null;
	String updateflag = null;
	String updatetime = null;
	
	boolean isFolder = false;
	
	
	
	
	public boolean getIsFolder() {
		return isFolder;
	}
	public void setIsFolder(boolean isFolder) {
		this.isFolder = isFolder;
	}
	public String getProgfileid() {
		return progfileid;
	}
	public void setProgfileid(String progfileid) {
		this.progfileid = progfileid;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getContentid() {
		return contentid;
	}
	public void setContentid(String contentid) {
		this.contentid = contentid;
	}
	public String getProgrank() {
		return progrank;
	}
	public void setProgrank(String progrank) {
		this.progrank = progrank;
	}
	public String getFilecode() {
		return filecode;
	}
	public void setFilecode(String filecode) {
		this.filecode = filecode;
	}
	public String getFiletypeid() {
		return filetypeid;
	}
	public void setFiletypeid(String filetypeid) {
		this.filetypeid = filetypeid;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}

	public String getUpdateflag() {
		return updateflag;
	}
	public void setUpdateflag(String updateflag) {
		this.updateflag = updateflag;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	
	
}
