package com.novacroft.nemo.tfl.common.command;

public interface AddUnattachedCardCmd {

	String getTitle();
	String getCardNumber();
	String getCustomerId();
	
	void setTitle(String title);
	void setCardNumber(String title);
	void setCustomerId(String title);
	
}
