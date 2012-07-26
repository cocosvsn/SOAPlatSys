package com.soaplat.cmsmgr.bean;
// default package



/**
 * ProgramEditRel entity. @author MyEclipse Persistence Tools
 */

public class ProgramEditRel  implements java.io.Serializable {


    // Fields    

     private String relid;
     private String programid;
     private String programiddes;


    // Constructors

    /** default constructor */
    public ProgramEditRel() {
    }

    
    /** full constructor */
    public ProgramEditRel(String relid, String programid, String programiddes) {
        this.relid = relid;
        this.programid = programid;
        this.programiddes = programiddes;
    }

   
    // Property accessors

    public String getRelid() {
        return this.relid;
    }
    
    public void setRelid(String relid) {
        this.relid = relid;
    }

    public String getProgramid() {
        return this.programid;
    }
    
    public void setProgramid(String programid) {
        this.programid = programid;
    }

    public String getProgramiddes() {
        return this.programiddes;
    }
    
    public void setProgramiddes(String programiddes) {
        this.programiddes = programiddes;
    }
   








}