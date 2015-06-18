package com.novacroft.nemo.mock_cubic.domain.card;

import java.util.Date;

import com.novacroft.nemo.common.domain.cubic.HolderDetails;
import com.novacroft.nemo.common.domain.cubic.PendingItems;
import com.novacroft.nemo.common.domain.cubic.PrePayTicketDetails;
import com.novacroft.nemo.common.domain.cubic.PrePayValue;
/**
 * Store Card Details for Cubic response.
 */
public class OysterCardDetails {
    protected Integer cardCapability;
    protected Integer cardDeposit;
    protected Integer cardType;
    protected Date cCCLostStolenDateTime;
    protected String hotlistReasonsCodeNumbers;
    protected Integer autoloadState;
    protected String passengerType;
    protected String photocardNumber;
    protected String prestigeId;
    protected Integer registered;
    protected HolderDetails holderDetails;
    protected DiscountEntitlement discounts;
    protected PrePayValue prePayValue;
    protected PrePayTicketDetails prePayTicketDetails;
    protected PendingItems pendingItems;

    public final Integer getCardCapability() {
        return cardCapability;
    }
    public final void setCardCapability(final Integer cardCapability) {
        this.cardCapability = cardCapability;
    }
    public final Integer getCardDeposit() {
        return cardDeposit;
    }
    public final void setCardDeposit(final Integer cardDeposit) {
        this.cardDeposit = cardDeposit;
    }
    public final Integer getCardType() {
        return cardType;
    }
    public final void setCardType(final Integer cardType) {
        this.cardType = cardType;
    }
    public final Date getcCCLostStolenDateTime() {
        return cCCLostStolenDateTime;
    }
    public final void setcCCLostStolenDateTime(final Date cCCLostStolenDateTime) {
        this.cCCLostStolenDateTime = cCCLostStolenDateTime;
    }
    public final String getHotlistReasonsCodeNumbers() {
        return hotlistReasonsCodeNumbers;
    }
    public final void setHotlistReasonsCodeNumbers(final String hotlistReasonsCodeNumbers) {
        this.hotlistReasonsCodeNumbers = hotlistReasonsCodeNumbers;
    }
    public final Integer getAutoloadState() {
        return autoloadState;
    }
    public final void setAutoloadState(final Integer autoloadState) {
        this.autoloadState = autoloadState;
    }
    public final String getPassengerType() {
        return passengerType;
    }
    public final void setPassengerType(final String passengerType) {
        this.passengerType = passengerType;
    }
    public final String getPhotocardNumber() {
        return photocardNumber;
    }
    public final void setPhotocardNumber(final String photocardNumber) {
        this.photocardNumber = photocardNumber;
    }

    public final String getPrestigeId() {
        return prestigeId;
    }
    public final void setPrestigeId(final String prestigeId) {
        this.prestigeId = prestigeId;
    }
    public final Integer getRegistered() {
        return registered;
    }
    public final void setRegistered(final Integer registered) {
        this.registered = registered;
    }
    public final HolderDetails getHolderDetails() {
        return holderDetails;
    }
    public final void setHolderDetails(final HolderDetails holderDetails) {
        this.holderDetails = holderDetails;
    }
    public final DiscountEntitlement getDiscounts() {
        return discounts;
    }
    public final void setDiscounts(final DiscountEntitlement discounts) {
        this.discounts = discounts;
    }
    public final PrePayValue getPrePayValue() {
        return prePayValue;
    }
    public final void setPrePayValue(final PrePayValue prePayValue) {
        this.prePayValue = prePayValue;
    }
    public PrePayTicketDetails getPrePayTicketDetails() {
        return prePayTicketDetails;
    }
    public void setPrePayTicketDetails(PrePayTicketDetails prePayTicketDetails) {
        this.prePayTicketDetails = prePayTicketDetails;
    }
    public PendingItems getPendingItems() {
        return pendingItems;
    }
    public void setPendingItems(PendingItems pendingItems) {
        this.pendingItems = pendingItems;
    }
    
}
