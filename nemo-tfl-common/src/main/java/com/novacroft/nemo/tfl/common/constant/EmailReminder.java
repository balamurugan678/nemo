package com.novacroft.nemo.tfl.common.constant;

public enum EmailReminder {
	FOUR_DAYS_PRIOR("4"), FIVE_DAYS_PRIOR("5"), SIX_DAYS_PRIOR("6"), SEVEN_DAYS_PRIOR("7"), 
	EIGHT_DAYS_PRIOR("8"), NINE_DAYS_PRIOR("9"), TEN_DAYS_PRIOR("10"), FOURTEEN_DAYS_PRIOR("14"),
	TWENTY_ONE_DAYS_PRIOR("21"), TWENTY_EIGHT_DAYS_PRIOR("28"), NO_REMINDER("0");
	
	private String code;
	
	EmailReminder(String code) {
		this.code = code;
	}
	
	public String code() {
		return code;
	}
	
}