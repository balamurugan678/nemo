package com.novacroft.nemo.common.command.impl;

import com.novacroft.nemo.common.command.AddressCmd;
import com.novacroft.nemo.common.command.CommonCallCmd;
import com.novacroft.nemo.common.command.ContactDetailsCmd;


/**
 *  Command object to hold the Call information.
 * 
 */

public class CallCmd extends CommonPersonalDetailsCmd implements CommonCallCmd, AddressCmd, ContactDetailsCmd {
	
	protected Long id;
	protected Long callTypeId;
	protected String description;
	protected String resolution;
	protected String notes;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCallTypeId() {
		return callTypeId;
	}
	public void setCallTypeId(Long callTypeId) {
		this.callTypeId = callTypeId;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getResolution() {
		return resolution;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
	

}
