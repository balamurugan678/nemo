package com.novacroft.nemo.tfl.services.transfer;

import java.util.Date;
import java.util.List;


public class Card {

    private Long id;
	private String prestigeId;
	private String photoCardNumber;
	private Integer registered;
	private String passengerType;
	private Integer autoLoadState;
	private Integer cardCapability;
	private Integer cardType;
	private Date cccLostStolenDateTime;
	private Integer cardDeposit;
	private List<CardDiscount> discounts;
	private Integer hotListReason;
	private PrePayValue prePayValue;
	private List<Ticket> tickets;
	private PendingItems pendingItems;
	private String errorCode;
	private String errorDescription;
	private boolean autoTopUpEnabled;
	
	public Card() {
        pendingItems = new PendingItems();
    }
	
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrestigeId() {
		return prestigeId;
	}

	public void setPrestigeId(String prestigeId) {
		this.prestigeId = prestigeId;
	}

	public String getPhotoCardNumber() {
		return photoCardNumber;
	}

	public void setPhotoCardNumber(String photoCardNumber) {
		this.photoCardNumber = photoCardNumber;
	}

	public Integer getRegistered() {
		return registered;
	}

	public void setRegistered(Integer registered) {
		this.registered = registered;
	}

	public String getPassengerType() {
		return passengerType;
	}

	public void setPassengerType(String passengerType) {
		this.passengerType = passengerType;
	}

	public Integer getAutoLoadState() {
		return autoLoadState;
	}

	public void setAutoLoadState(Integer autoLoadState) {
		this.autoLoadState = autoLoadState;
	}

	public Integer getCardCapability() {
		return cardCapability;
	}

	public void setCardCapability(Integer cardCapability) {
		this.cardCapability = cardCapability;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public Date getCccLostStolenDateTime() {
		return cccLostStolenDateTime;
	}

	public void setCccLostStolenDateTime(Date cccLostStolenDateTime) {
		this.cccLostStolenDateTime = cccLostStolenDateTime;
	}

	public Integer getCardDeposit() {
		return cardDeposit;
	}

	public void setCardDeposit(Integer cardDeposit) {
		this.cardDeposit = cardDeposit;
	}

	public List<CardDiscount> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(List<CardDiscount> discounts) {
		this.discounts = discounts;
	}

	public Integer getHotListReason() {
		return hotListReason;
	}

	public void setHotListReason(Integer hotListReason) {
		this.hotListReason = hotListReason;
	}

	public PrePayValue getPrePayValue() {
		return prePayValue;
	}

	public void setPrePayValue(PrePayValue prePayValue) {
		this.prePayValue = prePayValue;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public PendingItems getPendingItems() {
		return pendingItems;
	}

	public void setPendingItems(PendingItems pendingItems) {
		this.pendingItems = pendingItems;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public boolean isAutoTopUpEnabled() {
		return autoTopUpEnabled;
	}

	public void setAutoTopUpEnabled(boolean autoTopUpEnabled) {
		this.autoTopUpEnabled = autoTopUpEnabled;
	}

}
