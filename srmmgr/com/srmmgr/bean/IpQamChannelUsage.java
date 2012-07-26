package com.srmmgr.bean;
// default package

import java.util.Date;


/**
 * IpQamChannelUsage entity. @author MyEclipse Persistence Tools
 */

public class IpQamChannelUsage  implements java.io.Serializable {


    // Fields    

     private String ipqamchannelprogid;
     private String ipqamid;
     private String ipqamchannelid;
     private String programcode;
     private String districtid;
     private String clusterid;
     private String moduleid;
     private String unitid;
     private String channelno;
     private String ipaddr;
     private String modulation;
     private String frequency;
     private String symbolrate;
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
     private String scrambled;
     private String casturl;
     private String sessionid;
     private String bitrate;
     private Integer usagevalid;
     private Date usagetime;
     private String inputmanid;
     private Date inputtime;
     private String remark;


    // Constructors

    /** default constructor */
    public IpQamChannelUsage() {
    }

	/** minimal constructor */
    public IpQamChannelUsage(String ipqamchannelprogid) {
        this.ipqamchannelprogid = ipqamchannelprogid;
    }
    
    /** full constructor */
    public IpQamChannelUsage(String ipqamchannelprogid, String ipqamid, String ipqamchannelid, String programcode, String districtid, String clusterid, String moduleid, String unitid, String channelno, String ipaddr, String modulation, String frequency, String symbolrate, String programno, String udp, String video, String audio1, String audio2, String audio3, String audio4, String pcr, String ecm, String emm, String ar, String mpegx, String ac3, String scrambled, String casturl, String sessionid, String bitrate, Integer usagevalid, Date usagetime, String inputmanid, Date inputtime, String remark) {
        this.ipqamchannelprogid = ipqamchannelprogid;
        this.ipqamid = ipqamid;
        this.ipqamchannelid = ipqamchannelid;
        this.programcode = programcode;
        this.districtid = districtid;
        this.clusterid = clusterid;
        this.moduleid = moduleid;
        this.unitid = unitid;
        this.channelno = channelno;
        this.ipaddr = ipaddr;
        this.modulation = modulation;
        this.frequency = frequency;
        this.symbolrate = symbolrate;
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
        this.scrambled = scrambled;
        this.casturl = casturl;
        this.sessionid = sessionid;
        this.bitrate = bitrate;
        this.usagevalid = usagevalid;
        this.usagetime = usagetime;
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

    public String getIpqamid() {
        return this.ipqamid;
    }
    
    public void setIpqamid(String ipqamid) {
        this.ipqamid = ipqamid;
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

    public String getDistrictid() {
        return this.districtid;
    }
    
    public void setDistrictid(String districtid) {
        this.districtid = districtid;
    }

    public String getClusterid() {
        return this.clusterid;
    }
    
    public void setClusterid(String clusterid) {
        this.clusterid = clusterid;
    }

    public String getModuleid() {
        return this.moduleid;
    }
    
    public void setModuleid(String moduleid) {
        this.moduleid = moduleid;
    }

    public String getUnitid() {
        return this.unitid;
    }
    
    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public String getChannelno() {
        return this.channelno;
    }
    
    public void setChannelno(String channelno) {
        this.channelno = channelno;
    }

    public String getIpaddr() {
        return this.ipaddr;
    }
    
    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public String getModulation() {
        return this.modulation;
    }
    
    public void setModulation(String modulation) {
        this.modulation = modulation;
    }

    public String getFrequency() {
        return this.frequency;
    }
    
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getSymbolrate() {
        return this.symbolrate;
    }
    
    public void setSymbolrate(String symbolrate) {
        this.symbolrate = symbolrate;
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

    public String getScrambled() {
        return this.scrambled;
    }
    
    public void setScrambled(String scrambled) {
        this.scrambled = scrambled;
    }

    public String getCasturl() {
        return this.casturl;
    }
    
    public void setCasturl(String casturl) {
        this.casturl = casturl;
    }

    public String getSessionid() {
        return this.sessionid;
    }
    
    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getBitrate() {
        return this.bitrate;
    }
    
    public void setBitrate(String bitrate) {
        this.bitrate = bitrate;
    }

    public Integer getUsagevalid() {
        return this.usagevalid;
    }
    
    public void setUsagevalid(Integer usagevalid) {
        this.usagevalid = usagevalid;
    }

    public Date getUsagetime() {
        return this.usagetime;
    }
    
    public void setUsagetime(Date usagetime) {
        this.usagetime = usagetime;
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