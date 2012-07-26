package com.soaplat.cmsmgr.dto;

import java.util.List;

import com.soaplat.cmsmgr.bean.PackStyle;

public class ProgramTypeDto {

	// ProgType
    private String progtypeid;
    private String classname;
    private Long property;
    
    private List<PackStyle> packStyles;
    private List<String> packStyleXmls;

	public String getProgtypeid() {
		return progtypeid;
	}

	public void setProgtypeid(String progtypeid) {
		this.progtypeid = progtypeid;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public Long getProperty() {
		return property;
	}

	public void setProperty(Long property) {
		this.property = property;
	}

	public List<PackStyle> getPackStyles() {
		return packStyles;
	}

	public void setPackStyles(List<PackStyle> packStyles) {
		this.packStyles = packStyles;
	}

	public List<String> getPackStyleXmls() {
		return packStyleXmls;
	}

	public void setPackStyleXmls(List<String> packStyleXmls) {
		this.packStyleXmls = packStyleXmls;
	}

}
