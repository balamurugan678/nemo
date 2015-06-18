package com.novacroft.nemo.tfl.common.transfer;

public class DurationPeriodDTO {

	protected String fromDurationCode;
	protected String toDurationCode;

	public String getFromDurationCode() {
		return fromDurationCode;
	}

	public void setFromDurationCode(String fromDuration) {
		this.fromDurationCode = fromDuration;
	}

	public String getToDurationCode() {
		return toDurationCode;
	}

	public void setToDurationCode(String toDuration) {
		this.toDurationCode = toDuration;
	}

	public DurationPeriodDTO(String fromDuration, String toDuration) {
		super();
		this.fromDurationCode = fromDuration;
		this.toDurationCode = toDuration;
	}
}
