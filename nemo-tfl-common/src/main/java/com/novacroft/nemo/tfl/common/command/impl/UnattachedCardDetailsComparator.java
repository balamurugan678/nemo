package com.novacroft.nemo.tfl.common.command.impl;

public class UnattachedCardDetailsComparator {
	protected String title;
	protected String cubicValue;
	protected String oysterValue;
	protected Boolean comparison;
	
	public UnattachedCardDetailsComparator(String title, String cubicValue,String oysterValue){
		init(title, cubicValue, oysterValue);		
	}

    private void init(String title, String cubicValue, String oysterValue) {
        this.title= title;
		this.cubicValue = cubicValue;
		this.oysterValue = oysterValue;
		setComparison(cubicValue,oysterValue);
    }
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCubicValue() {
		return cubicValue;
	}
	public void setCubicValue(String cubicValue) {
		this.cubicValue = cubicValue;
	}
	public String getOysterValue() {
		return oysterValue;
	}
	public void setOysterValue(String oysterValue) {
		this.oysterValue = oysterValue;
	}
	public Boolean getComparison() {
		return this.comparison;
		
	}
	public void setComparison(Boolean comparison) {
		//not used
		
	}
	
	public final void setComparison(String cubicValue, String oysterValue) {
		String trimCubicValue = cubicValue.trim();
		String trimOysterValue = oysterValue.trim();

		if (trimCubicValue.equalsIgnoreCase(trimOysterValue)){
		    if ("".equals(trimOysterValue)){
		        //both items are empty
		        this.comparison = null;
		    }
		    
			this.comparison =  true;
			
		}else{
			this.comparison =  false;
		}
	}
}