package com.novacroft.nemo.tfl.common.command.impl;

import java.io.Serializable;

import org.springframework.validation.BindingResult;

import com.novacroft.nemo.common.command.OysterCardCmd;
import com.novacroft.nemo.tfl.common.command.AddUnattachedCardCmd;

public class AddUnattachedCardCmdImpl implements AddUnattachedCardCmd , OysterCardCmd, Serializable{

    private static final long serialVersionUID = 7287857378296479491L;
    private String title;
	private String cardNumber;
	private String customerId;
	private BindingResult errors;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public BindingResult getErrors() {
		return errors;
	}
	public void setErrors(BindingResult errors) {
		this.errors = errors;
	}
	

	
	
	

}
