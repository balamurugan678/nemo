package com.novacroft.nemo.tfl.common.transfer;

import com.novacroft.nemo.common.transfer.AbstractBaseDTO;

/**
 * TfL alternative zone mapping transfer implementation
 */

public class AlternativeZoneMappingDTO extends AbstractBaseDTO {
	protected Integer startZone;
	protected Integer endZone;
	protected Integer alternativeStartZone;
	protected Integer alternativeEndZone;
	
	public Integer getStartZone() {
		return startZone;
	}
	public void setStartZone(Integer startZone) {
		this.startZone = startZone;
	}
	public Integer getEndZone() {
		return endZone;
	}
	public void setEndZone(Integer endZone) {
		this.endZone = endZone;
	}
	public Integer getAlternativeStartZone() {
		return alternativeStartZone;
	}
	public void setAlternativeStartZone(Integer alternativeStartZone) {
		this.alternativeStartZone = alternativeStartZone;
	}
	public Integer getAlternativeEndZone() {
		return alternativeEndZone;
	}
	public void setAlternativeEndZone(Integer alternativeEndZone) {
		this.alternativeEndZone = alternativeEndZone;
	}
	
}
