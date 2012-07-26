package com.srmmgr.bean;
// default package

import java.util.Date;


/**
 * IpQamChannelProgNo entity. @author MyEclipse Persistence Tools
 */

public class IpQamChannelProgNo  implements java.io.Serializable {


    // Fields    

     private String ipqamchannelprogid;
     private String ipqamchannelid;
     private String programcode;
     private String programno;
     private String udp;
     private String video;
     private String audio1;
     private String audio2;
     private String audio3;
     private String audio4;
     private String pcr;
     private String ecm;
     private String emm;
     private String ar;
     private String mpegx;
     private String ac3;
     private String channelvalid;
     private String defaulticonpath;
     private String activeiconpath;
     private String inactiveiconpath;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public IpQamChannelProgNo() {
    }

	/** minimal constructor */
    public IpQamChannelProgNo(String ipqamchannelprogid) {
        this.ipqamchannelprogid = ipqamchannelprogid;
    }
    
    /** full constructor */
    public IpQamChannelProgNo(String ipqamchannelprogid, String ipqamchannelid, String programcode, String programno, String udp, String video, String audio1, String audio2, String audio3, String audio4, String pcr, String ecm, String emm, String ar, String mpegx, String ac3, String channelvalid, String defaulticonpath, String activeiconpath, String inactiveiconpath, String inputmanid, Date inputtime, String remark) {
        this.ipqamchannelprogid = ipqamchannelprogid;
        this.ipqamchannelid = ipqamchannelid;
        this.programcode = programcode;
        this.programno = programno;
        this.udp = udp;
        this.video = video;
        this.audio1 = audio1;
        this.audio2 = audio2;
        this.audio3 = audio3;
        this.audio4 = audio4;
        this.pcr = pcr;
        this.ecm = ecm;
        this.emm = emm;
        this.ar = ar;
        this.mpegx = mpegx;
        this.ac3 = ac3;
        this.channelvalid = channelvalid;
        this.defaulticonpath = defaulticonpath;
        this.activeiconpath = activeiconpath;
        this.inactiveiconpath = inactiveiconpath;
        this.inputmanid = inputmanid;
        this.inputtime = inputtime;
        this.remark = remark;
    }

   
    // Property accessors

    public String getIpqamchannelprogid() {
        return this.ipqamchannelprogid;
    }
    
    public void setIpqamchannelprogid(String ipqamchannelprogid) {
        this.ipqamchannelprogid = ipqamchannelprogid;
    }

    public String getIpqamchannelid() {
        return this.ipqamchannelid;
    }
    
    public void setIpqamchannelid(String ipqamchannelid) {
        this.ipqamchannelid = ipqamchannelid;
    }

    public String getProgramcode() {
        return this.programcode;
    }
    
    public void setProgramcode(String programcode) {
        this.programcode = programcode;
    }

    public String getProgramno() {
        return this.programno;
    }
    
    public void setProgramno(String programno) {
        this.programno = programno;
    }

    public String getUdp() {
        return this.udp;
    }
    
    public void setUdp(String udp) {
        this.udp = udp;
    }

    public String getVideo() {
        return this.video;
    }
    
    public void setVideo(String video) {
        this.video = video;
    }

    public String getAudio1() {
        return this.audio1;
    }
    
    public void setAudio1(String audio1) {
        this.audio1 = audio1;
    }

    public String getAudio2() {
        return this.audio2;
    }
    
    public void setAudio2(String audio2) {
        this.audio2 = audio2;
    }

    public String getAudio3() {
        return this.audio3;
    }
    
    public void setAudio3(String audio3) {
        this.audio3 = audio3;
    }

    public String getAudio4() {
        return this.audio4;
    }
    
    public void setAudio4(String audio4) {
        this.audio4 = audio4;
    }

    public String getPcr() {
        return this.pcr;
    }
    
    public void setPcr(String pcr) {
        this.pcr = pcr;
    }

    public String getEcm() {
        return this.ecm;
    }
    
    public void setEcm(String ecm) {
        this.ecm = ecm;
    }

    public String getEmm() {
        return this.emm;
    }
    
    public void setEmm(String emm) {
        this.emm = emm;
    }

    public String getAr() {
        return this.ar;
    }
    
    public void setAr(String ar) {
        this.ar = ar;
    }

    public String getMpegx() {
        return this.mpegx;
    }
    
    public void setMpegx(String mpegx) {
        this.mpegx = mpegx;
    }

    public String getAc3() {
        return this.ac3;
    }
    
    public void setAc3(String ac3) {
        this.ac3 = ac3;
    }

    public String getChannelvalid() {
        return this.channelvalid;
    }
    
    public void setChannelvalid(String channelvalid) {
        this.channelvalid = channelvalid;
    }

    public String getDefaulticonpath() {
        return this.defaulticonpath;
    }
    
    public void setDefaulticonpath(String defaulticonpath) {
        this.defaulticonpath = defaulticonpath;
    }

    public String getActiveiconpath() {
        return this.activeiconpath;
    }
    
    public void setActiveiconpath(String activeiconpath) {
        this.activeiconpath = activeiconpath;
    }

    public String getInactiveiconpath() {
        return this.inactiveiconpath;
    }
    
    public void setInactiveiconpath(String inactiveiconpath) {
        this.inactiveiconpath = inactiveiconpath;
    }

    public String getInputmanid() {
        return this.inputmanid;
    }
    
    public void setInputmanid(String inputmanid) {
        this.inputmanid = inputmanid;
    }

    public Date getInputtime() {
        return this.inputtime;
    }
    
    public void setInputtime(Date inputtime) {
        this.inputtime = inputtime;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
   








}