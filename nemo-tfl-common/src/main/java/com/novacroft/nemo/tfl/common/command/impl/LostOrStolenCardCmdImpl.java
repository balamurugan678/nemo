package com.novacroft.nemo.tfl.common.command.impl;

import com.novacroft.nemo.common.command.OysterCardCmd;

/**
 * Command (MVC model) class for a lost or stolen oyster card
 */
public class LostOrStolenCardCmdImpl implements OysterCardCmd {

    protected Long cardId;
    protected String cardNumber;
    protected Integer hotlistReasonId;
    protected String hotlistDateTime;
    protected String lostStolenOptions;
    protected String hotlistStatus;
    protected String hotlistReasonDescription;
    protected Long hotlistedCardReasonId;

	public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    @Override
    public String getCardNumber() {
        return this.cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getHotlistReasonId() {
        return hotlistReasonId;
    }

    public void setHotlistReasonId(Integer hotlistReasonId) {
        this.hotlistReasonId = hotlistReasonId;
    }

    public String getHotlistDateTime() {
        return hotlistDateTime;
    }

    public void setHotlistDateTime(String hotlistDateTime) {
        this.hotlistDateTime = hotlistDateTime;
    }

    public String getLostStolenOptions() {
        return lostStolenOptions;
    }

    public void setLostStolenOptions(String lostStolenOptions) {
        this.lostStolenOptions = lostStolenOptions;
    }

    public String getHotlistStatus() {
		return hotlistStatus;
	}
	
    public void setHotlistStatus(String hotlistStatus) {
		this.hotlistStatus = hotlistStatus;
	}

	public String getHotlistReasonDescription() {
		return hotlistReasonDescription;
	}

	public void setHotlistReasonDescription(String hotlistReasonDescription) {
		this.hotlistReasonDescription = hotlistReasonDescription;
	}

	public Long getHotlistedCardReasonId() {
		return hotlistedCardReasonId;
	}

	public void setHotlistedCardReasonId(Long hotlistedCardReasonId) {
		this.hotlistedCardReasonId = hotlistedCardReasonId;
	}
   
}
