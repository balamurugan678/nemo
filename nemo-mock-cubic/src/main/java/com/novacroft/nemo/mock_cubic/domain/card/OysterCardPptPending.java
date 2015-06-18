package com.novacroft.nemo.mock_cubic.domain.card;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.novacroft.nemo.common.domain.AbstractBaseEntity;


/**
* OysterCardPptPending  domain class to hold the domain data.
*/

@Entity
@Table(name = "MOCK_OYSTERCARDPPTPENDING")
public class OysterCardPptPending extends AbstractBaseEntity {

    private static final long serialVersionUID = 1L;
    
    protected String prestigeId;    
    protected Long requestSequenceNumber1;
    protected String realTimeFlag1;
    protected Long productCode1;
    protected Long productPrice1;
    protected Long currency1;
    protected Date startDate1;
    protected Date expiryDate1;
    protected Long pickupLocation1;
    
    protected Long requestSequenceNumber2;
    protected String realTimeFlag2;
    protected Long productCode2;
    protected Long productPrice2;
    protected Long currency2;
    protected Date startDate2;
    protected Date expiryDate2;
    protected Long pickupLocation2;

    protected Long requestSequenceNumber3;
    protected String realTimeFlag3;
    protected Long productCode3;
    protected Long productPrice3;
    protected Long currency3;
    protected Date startDate3;
    protected Date expiryDate3;
    protected Long pickupLocation3;
    
    @SequenceGenerator(name = "OYSTERCARDPPTPENDING_SEQ", sequenceName = "OYSTERCARDPPTPENDING_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OYSTERCARDPPTPENDING_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
      return id;
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

    public Long getPickupLocation1() {
        return pickupLocation1;
    }

    public void setPickupLocation1(Long pickupLocation1) {
        this.pickupLocation1 = pickupLocation1;
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

    public Long getPickupLocation2() {
        return pickupLocation2;
    }

    public void setPickupLocation2(Long pickupLocation2) {
        this.pickupLocation2 = pickupLocation2;
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

    public Long getPickupLocation3() {
        return pickupLocation3;
    }

    public void setPickupLocation3(Long pickupLocation3) {
        this.pickupLocation3 = pickupLocation3;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
