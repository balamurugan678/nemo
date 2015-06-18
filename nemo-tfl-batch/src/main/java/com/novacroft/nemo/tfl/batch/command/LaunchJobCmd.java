package com.novacroft.nemo.tfl.batch.command;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.novacroft.nemo.common.constant.DateConstant;

/**
 * Launch Job command (MVC model) class
 */
public class LaunchJobCmd {
    protected String message;
    
    @DateTimeFormat(pattern=DateConstant.SHORT_DATE_PATTERN)
    protected Date priceEffectiveDate;
    
    public Date getPriceEffectiveDate() {
		return priceEffectiveDate;
	}

	public void setPriceEffectiveDate(Date priceEffectiveDate) {
		this.priceEffectiveDate = priceEffectiveDate;
	}

	public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
