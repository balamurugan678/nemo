package com.novacroft.nemo.tfl.services.transfer;

public class TravelCard {
    private String duration;
    private String startZone;
    private String endZone;
    private String startDate;
    private String endDate;
    private String emailReminder;
    private String passengerType; 
    private String discountType;
    private ErrorResult errors;

    public TravelCard(String duration, String startZone, String endZone, String startDate, String endDate, String emailReminder) {
        this.duration = duration;
        this.startZone = startZone;
        this.endZone = endZone;
        this.startDate = startDate;
        this.endDate = endDate;
        this.emailReminder = emailReminder;
    }
    
    public TravelCard(String duration, String startZone, String endZone) {
        this.duration = duration;
        this.startZone = startZone;
        this.endZone = endZone;
    }

    public TravelCard() {
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStartZone() {
        return startZone;
    }

    public void setStartZone(String startZone) {
        this.startZone = startZone;
    }

    public String getEndZone() {
        return endZone;
    }

    public void setEndZone(String endZone) {
        this.endZone = endZone;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEmailReminder() {
        return emailReminder;
    }

    public void setEmailReminder(String emailReminder) {
        this.emailReminder = emailReminder;
    }

    public ErrorResult getErrors() {
        return errors;
    }

    public void setErrors(ErrorResult errors) {
        this.errors = errors;
    }

	public String getPassengerType() {
		return passengerType;
	}

	public void setPassengerType(String passengerType) {
		this.passengerType = passengerType;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}
    
}
