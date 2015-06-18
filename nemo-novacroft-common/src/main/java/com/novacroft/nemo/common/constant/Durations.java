package com.novacroft.nemo.common.constant;

public enum Durations {
    SEVEN_DAYS("7Day", 6), MONTH("1Month", 1), THREE_MONTH("3Month", 3), SIX_MONTH("6Month", 6), ANNUAL("Annual", 12), OTHER("Unknown", 0);
	
	String durationType;
	int durationValue;
	Durations(String durationType, int durationValue) {
		this.durationType = durationType;
		this.durationValue = durationValue;
	}
	
	public String getDurationType() {
		return durationType;
	}
	
	public int getDurationValue() {
		return durationValue;
	}
	
	public static Durations findByType(String text){
	    for(Durations d : values()){
	        if( d.getDurationType().equals(text)){
	            return d;
	        }
	    }
	    return null;
	}
}