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
* OysterCardPrepayTicket  domain class to hold the domain data.
*/

@Entity
@Table(name = "MOCK_OYSTERCARDPREPAYTICKET")
public class OysterCardPrepayTicket extends AbstractBaseEntity {
    
    private static final long serialVersionUID = 1L;

    protected String prestigeId;    
    protected Long slotNumber1;
    protected String product1;
    protected String zone1;
    protected Date startDate1;
    protected Date expiryDate1;
    protected String passengerType1;
    protected String discount1;
    protected Long state1;

    protected Long slotNumber2;
    protected String product2;
    protected String zone2;
    protected Date startDate2;
    protected Date expiryDate2;
    protected String passengerType2;
    protected String discount2;
    protected Long state2;

    protected Long slotNumber3;
    protected String product3;
    protected String zone3;
    protected Date startDate3;
    protected Date expiryDate3;
    protected String passengerType3;
    protected String discount3;
    protected Long state3;

    @SequenceGenerator(name = "OYSTERCARDPREPAYTICKET_SEQ", sequenceName = "OYSTERCARDPREPAYTICKET_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OYSTERCARDPREPAYTICKET_SEQ")
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

    public Long getSlotNumber1() {
        return slotNumber1;
    }

    public void setSlotNumber1(Long slotNumber1) {
        this.slotNumber1 = slotNumber1;
    }

    public String getProduct1() {
        return product1;
    }

    public void setProduct1(String product1) {
        this.product1 = product1;
    }

    public String getZone1() {
        return zone1;
    }

    public void setZone1(String zone1) {
        this.zone1 = zone1;
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

    public String getPassengerType1() {
        return passengerType1;
    }

    public void setPassengerType1(String passengerType1) {
        this.passengerType1 = passengerType1;
    }

    public String getDiscount1() {
        return discount1;
    }

    public void setDiscount1(String discount1) {
        this.discount1 = discount1;
    }

    public Long getState1() {
        return state1;
    }

    public void setState1(Long state1) {
        this.state1 = state1;
    }

    public Long getSlotNumber2() {
        return slotNumber2;
    }

    public void setSlotNumber2(Long slotNumber2) {
        this.slotNumber2 = slotNumber2;
    }

    public String getProduct2() {
        return product2;
    }

    public void setProduct2(String product2) {
        this.product2 = product2;
    }

    public String getZone2() {
        return zone2;
    }

    public void setZone2(String zone2) {
        this.zone2 = zone2;
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

    public String getPassengerType2() {
        return passengerType2;
    }

    public void setPassengerType2(String passengerType2) {
        this.passengerType2 = passengerType2;
    }

    public String getDiscount2() {
        return discount2;
    }

    public void setDiscount2(String discount2) {
        this.discount2 = discount2;
    }

    public Long getState2() {
        return state2;
    }

    public void setState2(Long state2) {
        this.state2 = state2;
    }

    public Long getSlotNumber3() {
        return slotNumber3;
    }

    public void setSlotNumber3(Long slotNumber3) {
        this.slotNumber3 = slotNumber3;
    }

    public String getProduct3() {
        return product3;
    }

    public void setProduct3(String product3) {
        this.product3 = product3;
    }

    public String getZone3() {
        return zone3;
    }

    public void setZone3(String zone3) {
        this.zone3 = zone3;
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

    public String getPassengerType3() {
        return passengerType3;
    }

    public void setPassengerType3(String passengerType3) {
        this.passengerType3 = passengerType3;
    }

    public String getDiscount3() {
        return discount3;
    }

    public void setDiscount3(String discount3) {
        this.discount3 = discount3;
    }

    public Long getState3() {
        return state3;
    }

    public void setState3(Long state3) {
        this.state3 = state3;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }       
}
