package com.novacroft.nemo.mock_cubic.transfer;

import java.util.Date;

import com.novacroft.nemo.common.transfer.AbstractBaseDTO;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * OysterCardPptPendingDTO transfer implementation.
 * 
 */

public class OysterCardPptPendingDTO extends AbstractBaseDTO {

    private static final int HASH_INITIAL = 31;
    private static final int HASH_MULTIPLIER = 37;

    protected String prestigeId;
    protected Long requestSequenceNumber1;
    protected String realTimeFlag1;
    protected Long productCode1;
    protected Long productPrice1;
    protected Long currency1;
    protected Date startDate1;
    protected Date expiryDate1;
    protected Long pickUpLocation1;

    protected Long requestSequenceNumber2;
    protected String realTimeFlag2;
    protected Long productCode2;
    protected Long productPrice2;
    protected Long currency2;
    protected Date startDate2;
    protected Date expiryDate2;
    protected Long pickUpLocation2;

    protected Long requestSequenceNumber3;
    protected String realTimeFlag3;
    protected Long productCode3;
    protected Long productPrice3;
    protected Long currency3;
    protected Date startDate3;
    protected Date expiryDate3;
    protected Long pickUpLocation3;

    public OysterCardPptPendingDTO() {

    }

    public OysterCardPptPendingDTO(String prestigeId, Long requestSequenceNumber1, String realTimeFlag1, Long productCode1, Long productPrice1,
                    Long currency1, Date startDate1, Date expiryDate1, Long pickUpLocation1, Long requestSequenceNumber2, String realTimeFlag2,
                    Long productCode2, Long productPrice2, Long currency2, Date startDate2, Date expiryDate2, Long pickUpLocation2,
                    Long requestSequenceNumber3, String realTimeFlag3, Long productCode3, Long productPrice3, Long currency3, Date startDate3,
                    Date expiryDate3, Long pickUpLocation3) {
        this.prestigeId = prestigeId;

        this.requestSequenceNumber1 = requestSequenceNumber1;
        this.realTimeFlag1 = realTimeFlag1;
        this.productCode1 = productCode1;
        this.productPrice1 = productPrice1;
        this.currency1 = currency1;
        this.startDate1 = startDate1;
        this.expiryDate1 = expiryDate1;
        this.pickUpLocation1 = pickUpLocation1;

        this.requestSequenceNumber2 = requestSequenceNumber2;
        this.realTimeFlag2 = realTimeFlag2;
        this.productCode2 = productCode2;
        this.productPrice2 = productPrice2;
        this.currency2 = currency2;
        this.startDate2 = startDate2;
        this.expiryDate2 = expiryDate2;
        this.pickUpLocation2 = pickUpLocation2;

        this.requestSequenceNumber3 = requestSequenceNumber3;
        this.realTimeFlag3 = realTimeFlag3;
        this.productCode3 = productCode3;
        this.productPrice3 = productPrice3;
        this.currency3 = currency3;
        this.startDate3 = startDate3;
        this.expiryDate3 = expiryDate3;
        this.pickUpLocation3 = pickUpLocation3;
    }

    public String getPrestigeId() {
        return prestigeId;
    }

    public void setPrestigeId(String prestigeId) {
        this.prestigeId = prestigeId;
    }

    public Long getRequestSequenceNumber1() {
        return requestSequenceNumber1;
    }

    public void setRequestSequenceNumber1(Long requestSequenceNumber1) {
        this.requestSequenceNumber1 = requestSequenceNumber1;
    }

    public String getRealTimeFlag1() {
        return realTimeFlag1;
    }

    public void setRealTimeFlag1(String realTimeFlag1) {
        this.realTimeFlag1 = realTimeFlag1;
    }

    public Long getProductCode1() {
        return productCode1;
    }

    public void setProductCode1(Long productCode1) {
        this.productCode1 = productCode1;
    }

    public Long getProductPrice1() {
        return productPrice1;
    }

    public void setProductPrice1(Long productPrice1) {
        this.productPrice1 = productPrice1;
    }

    public Long getCurrency1() {
        return currency1;
    }

    public void setCurrency1(Long currency1) {
        this.currency1 = currency1;
    }

    public Date getStartDate1() {
        return startDate1;
    }

    public void setStartDate1(Date startDate1) {
        this.startDate1 = startDate1;
    }

    public Date getExpiryDate1() {
        return expiryDate1;
    }

    public void setExpiryDate1(Date expiryDate1) {
        this.expiryDate1 = expiryDate1;
    }

    public Long getPickUpLocation1() {
        return pickUpLocation1;
    }

    public void setPickUpLocation1(Long pickUpLocation1) {
        this.pickUpLocation1 = pickUpLocation1;
    }

    public Long getRequestSequenceNumber2() {
        return requestSequenceNumber2;
    }

    public void setRequestSequenceNumber2(Long requestSequenceNumber2) {
        this.requestSequenceNumber2 = requestSequenceNumber2;
    }

    public String getRealTimeFlag2() {
        return realTimeFlag2;
    }

    public void setRealTimeFlag2(String realTimeFlag2) {
        this.realTimeFlag2 = realTimeFlag2;
    }

    public Long getProductCode2() {
        return productCode2;
    }

    public void setProductCode2(Long productCode2) {
        this.productCode2 = productCode2;
    }

    public Long getProductPrice2() {
        return productPrice2;
    }

    public void setProductPrice2(Long productPrice2) {
        this.productPrice2 = productPrice2;
    }

    public Long getCurrency2() {
        return currency2;
    }

    public void setCurrency2(Long currency2) {
        this.currency2 = currency2;
    }

    public Date getStartDate2() {
        return startDate2;
    }

    public void setStartDate2(Date startDate2) {
        this.startDate2 = startDate2;
    }

    public Date getExpiryDate2() {
        return expiryDate2;
    }

    public void setExpiryDate2(Date expiryDate2) {
        this.expiryDate2 = expiryDate2;
    }

    public Long getPickUpLocation2() {
        return pickUpLocation2;
    }

    public void setPickUpLocation2(Long pickUpLocation2) {
        this.pickUpLocation2 = pickUpLocation2;
    }

    public Long getRequestSequenceNumber3() {
        return requestSequenceNumber3;
    }

    public void setRequestSequenceNumber3(Long requestSequenceNumber3) {
        this.requestSequenceNumber3 = requestSequenceNumber3;
    }

    public String getRealTimeFlag3() {
        return realTimeFlag3;
    }

    public void setRealTimeFlag3(String realTimeFlag3) {
        this.realTimeFlag3 = realTimeFlag3;
    }

    public Long getProductCode3() {
        return productCode3;
    }

    public void setProductCode3(Long productCode3) {
        this.productCode3 = productCode3;
    }

    public Long getProductPrice3() {
        return productPrice3;
    }

    public void setProductPrice3(Long productPrice3) {
        this.productPrice3 = productPrice3;
    }

    public Long getCurrency3() {
        return currency3;
    }

    public void setCurrency3(Long currency3) {
        this.currency3 = currency3;
    }

    public Date getStartDate3() {
        return startDate3;
    }

    public void setStartDate3(Date startDate3) {
        this.startDate3 = startDate3;
    }

    public Date getExpiryDate3() {
        return expiryDate3;
    }

    public void setExpiryDate3(Date expiryDate3) {
        this.expiryDate3 = expiryDate3;
    }

    public Long getPickUpLocation3() {
        return pickUpLocation3;
    }

    public void setPickUpLocation3(Long pickUpLocation3) {
        this.pickUpLocation3 = pickUpLocation3;
    }

    public OysterCardPptPendingDTO copy() {
        return new OysterCardPptPendingDTO(prestigeId, requestSequenceNumber1, realTimeFlag1, productCode1, productPrice1, currency1, startDate1,
                        expiryDate1, pickUpLocation1, requestSequenceNumber2, realTimeFlag2, productCode2, productPrice2, currency2, startDate2,
                        expiryDate2, pickUpLocation2, requestSequenceNumber3, realTimeFlag3, productCode3, productPrice3, currency3, startDate3,
                        expiryDate3, pickUpLocation3);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        OysterCardPptPendingDTO that = (OysterCardPptPendingDTO) object;

        return new EqualsBuilder().append(prestigeId, that.prestigeId)
                        .append(requestSequenceNumber1, that.requestSequenceNumber1).append(realTimeFlag1, that.realTimeFlag1)
                        .append(productCode1, that.productCode1).append(productPrice1, that.productPrice1).append(currency1, that.currency1)
                        .append(startDate1, that.startDate1).append(expiryDate1, that.expiryDate1).append(pickUpLocation1, that.pickUpLocation1)
                        .append(requestSequenceNumber2, that.requestSequenceNumber2).append(realTimeFlag2, that.realTimeFlag2)
                        .append(productCode2, that.productCode2).append(productPrice2, that.productPrice2).append(currency2, that.currency2)
                        .append(startDate2, that.startDate2).append(expiryDate2, that.expiryDate2).append(pickUpLocation2, that.pickUpLocation2)
                        .append(requestSequenceNumber3, that.requestSequenceNumber3).append(realTimeFlag3, that.realTimeFlag3)
                        .append(productCode3, that.productCode3).append(productPrice3, that.productPrice3).append(currency3, that.currency3)
                        .append(startDate3, that.startDate3).append(expiryDate3, that.expiryDate3).append(pickUpLocation3, that.pickUpLocation3)
                        .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HASH_INITIAL, HASH_MULTIPLIER).append(prestigeId)
                        .append(requestSequenceNumber1).append(realTimeFlag1).append(productCode1).append(productPrice1).append(currency1)
                        .append(startDate1).append(expiryDate1).append(pickUpLocation1)
                        .append(requestSequenceNumber2).append(realTimeFlag2).append(productCode2).append(productPrice2).append(currency2)
                        .append(startDate2).append(expiryDate2).append(pickUpLocation2)
                        .append(requestSequenceNumber3).append(realTimeFlag3).append(productCode3).append(productPrice3).append(currency3)
                        .append(startDate3).append(expiryDate3).append(pickUpLocation3).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(prestigeId)
                        .append(requestSequenceNumber1).append(realTimeFlag1).append(productCode1).append(productPrice1).append(currency1)
                        .append(startDate1).append(expiryDate1).append(pickUpLocation1)
                        .append(requestSequenceNumber2).append(realTimeFlag2).append(productCode2).append(productPrice2).append(currency2)
                        .append(startDate2).append(expiryDate2).append(pickUpLocation2)
                        .append(requestSequenceNumber3).append(realTimeFlag3)
                        .append(productCode3).append(productPrice3).append(currency3).append(startDate3).append(expiryDate3)
                        .append(pickUpLocation3).toString();
    }

}
