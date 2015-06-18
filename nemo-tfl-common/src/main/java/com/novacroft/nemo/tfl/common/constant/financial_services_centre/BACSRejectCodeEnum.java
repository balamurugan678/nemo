package com.novacroft.nemo.tfl.common.constant.financial_services_centre;

public enum BACSRejectCodeEnum {
   
	R("BACS Recall"), C("Account closed"), I("Invalid Details");
	
    private String description;
	
	private BACSRejectCodeEnum(String description)
	{
	   this.description = description;
	}
	
	@Override
	public String toString(){
		return description;
	}
	
}
