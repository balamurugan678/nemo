package com.novacroft.nemo.tfl.common.domain.cubic;

import com.novacroft.nemo.common.domain.cubic.*;
import com.novacroft.nemo.tfl.common.constant.HashCodeSeed;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.List;

/**
 * Get Card Information Response Version 2
 */
public class CardInfoResponseV2 {
    protected String prestigeId;
    protected String photoCardNumber;
    protected Integer registered;
    protected String passengerType;
    protected Integer autoLoadState;
    protected Integer cardCapability;
    protected Integer cardType;
    protected Date cccLostStolenDateTime;
    protected Integer cardDeposit;
    protected String discountEntitlement1;
    protected String discountEntitlement2;
    protected String discountEntitlement3;
    protected String discountExpiry1;
    protected String discountExpiry2;
    protected String discountExpiry3;
    protected HotListReasons hotListReasons;
    protected HolderDetails holderDetails;
    protected PrePayValue ppvDetails;
    protected PrePayTicketDetails pptDetails;
    protected PendingItems pendingItems;
    protected List<String> hotlist;

    public CardInfoResponseV2() {
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

    public String getDiscountEntitlement1() {
        return discountEntitlement1;
    }

    public void setDiscountEntitlement1(String discountEntitlement1) {
        this.discountEntitlement1 = discountEntitlement1;
    }

    public String getDiscountEntitlement2() {
        return discountEntitlement2;
    }

    public void setDiscountEntitlement2(String discountEntitlement2) {
        this.discountEntitlement2 = discountEntitlement2;
    }

    public String getDiscountEntitlement3() {
        return discountEntitlement3;
    }

    public void setDiscountEntitlement3(String discountEntitlement3) {
        this.discountEntitlement3 = discountEntitlement3;
    }

    public String getDiscountExpiry1() {
        return discountExpiry1;
    }

    public void setDiscountExpiry1(String discountExpiry1) {
        this.discountExpiry1 = discountExpiry1;
    }

    public String getDiscountExpiry2() {
        return discountExpiry2;
    }

    public void setDiscountExpiry2(String discountExpiry2) {
        this.discountExpiry2 = discountExpiry2;
    }

    public String getDiscountExpiry3() {
        return discountExpiry3;
    }

    public void setDiscountExpiry3(String discountExpiry3) {
        this.discountExpiry3 = discountExpiry3;
    }

    public HotListReasons getHotListReasons() {
        return hotListReasons;
    }

    public void setHotListReasons(HotListReasons hotListReasons) {
        this.hotListReasons = hotListReasons;
    }

    public HolderDetails getHolderDetails() {
        return holderDetails;
    }

    public void setHolderDetails(HolderDetails holderDetails) {
        this.holderDetails = holderDetails;
    }

    public PrePayValue getPpvDetails() {
        return ppvDetails;
    }

    public void setPpvDetails(PrePayValue ppvDetails) {
        this.ppvDetails = ppvDetails;
    }

    public PrePayTicketDetails getPptDetails() {
        return pptDetails;
    }

    public void setPptDetails(PrePayTicketDetails pptDetails) {
        this.pptDetails = pptDetails;
    }

    public PendingItems getPendingItems() {
        return pendingItems;
    }

    public void setPendingItems(PendingItems pendingItems) {
        this.pendingItems = pendingItems;
    }

    public List<String> getHotlist() {
        return hotlist;
    }

    public void setHotlist(List<String> hotlist) {
        this.hotlist = hotlist;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        CardInfoResponseV2 that = (CardInfoResponseV2) object;

        return new EqualsBuilder().append(prestigeId, that.prestigeId).append(passengerType, that.passengerType)
                .append(autoLoadState, that.autoLoadState).append(discountEntitlement1, that.discountEntitlement1)
                .append(discountEntitlement2, that.discountEntitlement2).append(discountEntitlement3, that.discountEntitlement3)
                .append(discountExpiry1, that.discountExpiry1).append(discountExpiry2, that.discountExpiry2)
                .append(discountExpiry3, that.discountExpiry3).append(photoCardNumber, that.photoCardNumber)
                .append(registered, that.registered).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HashCodeSeed.CARD_INFO_RESPONSE_V2.initialiser(),
                HashCodeSeed.CARD_INFO_RESPONSE_V2.multiplier()).append(prestigeId).append(passengerType).append(autoLoadState)
                .append(discountEntitlement1).append(discountEntitlement2).append(discountEntitlement3).append(discountExpiry1)
                .append(discountExpiry2).append(discountExpiry3).append(photoCardNumber).append(registered).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(prestigeId).append(passengerType).append(autoLoadState)
                .append(discountEntitlement1).append(discountEntitlement2).append(discountEntitlement3).append(discountExpiry1)
                .append(discountExpiry2).append(discountExpiry3).append(photoCardNumber).append(registered).toString();
    }

}
