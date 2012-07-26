package com.sbl.common;

public class RefVo {
	private String name;
	private String springRel;
	private String theClass;
	private String method;
	private Class<?>[] cls;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSpringRel() {
		return springRel;
	}
	
	public void setSpringRel(String springRel) {
		this.springRel = springRel;
	}

	public String getTheClass() {
		return theClass;
	}

	public void setTheClass(String theClass) {
		this.theClass = theClass;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Class<?>[] getCls() {
		return cls;
	}

	public void setCls(Class<?>[] cls) {
		this.cls = cls;
	}

}
