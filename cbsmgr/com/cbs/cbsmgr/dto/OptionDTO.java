package com.cbs.cbsmgr.dto;
import java.io.Serializable;
//import com.rtb.cbs.core.utils.TimeConvertor;
import java.math.BigDecimal;
import java.util.Date;

public class OptionDTO implements Serializable {

    private Long optionId;
    private String option;

    
    public OptionDTO() {

	}


	public Long getOptionId() {
		return optionId;
	}


	public void setOptionId(Long optionId) {
		this.optionId = optionId;
	}


	public String getOption() {
		return option;
	}


	public void setOption(String option) {
		this.option = option;
	}


}