package com.novacroft.nemo.tfl.common.command.impl;

import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import com.novacroft.nemo.tfl.common.command.CancelAndSurrenderCmd;

import static com.novacroft.nemo.common.constant.DateConstant.SHORT_DATE_PATTERN;

public class CancelAndSurrenderCmdImpl implements CancelAndSurrenderCmd {

    protected String magneticTicketNumber;
    protected Boolean previouslyExchanged;
    protected Boolean deceasedCustomer;
    protected Boolean ticketUnused;
    protected String refundType;
    @DateTimeFormat(pattern=SHORT_DATE_PATTERN)
    protected DateTime refundDate;
    @DateTimeFormat(pattern=SHORT_DATE_PATTERN)
    protected DateTime ticketStartDate;
    @DateTimeFormat(pattern=SHORT_DATE_PATTERN)
    protected DateTime ticketEndDate;
    @DateTimeFormat(pattern=SHORT_DATE_PATTERN)
    protected DateTime exchangedDate;
    @DateTimeFormat(pattern=SHORT_DATE_PATTERN)
    protected DateTime tradedTicketStartDate;
    @DateTimeFormat(pattern=SHORT_DATE_PATTERN)
    protected DateTime tradedTicketEndDate;
    protected Long productId;
    protected Long tradedProductId;
    protected String refundTicketStartZone;
    protected String refundTicketEndZone;
    protected String tradedTicketStartZone;
    protected String tradedTicketEndZone;
    protected String purchasedProductDescription;
    protected String tradedProductDescription;
    
    @Override
    public String getMagneticTicketNumber() {
        return magneticTicketNumber;
    }

    @Override
    public void setMagneticTicketNumber(String magneticTicketNumber) {
        this.magneticTicketNumber = magneticTicketNumber;
    }

    @Override
    public Boolean getPreviouslyExchanged() {
        return previouslyExchanged;
    }

    @Override
    public void setPreviouslyExchanged(Boolean previouslyExchanged) {
        this.previouslyExchanged = previouslyExchanged;
    }

    @Override
    public Boolean getTicketUnused() {
        return ticketUnused;
    }

    @Override
    public void setTicketUnused(Boolean ticketUnused) {
        this.ticketUnused = ticketUnused;
    }

    @Override
    public DateTime getRefundDate() {
        return refundDate;
    }

    @Override
    public void setRefundDate(DateTime refundDate) {
        this.refundDate = refundDate;
    }

    @Override
    public Boolean getDeceasedCustomer() {
        return deceasedCustomer;
    }

    @Override
    public void setDeceasedCustomer(Boolean deceasedCustomer) {
        this.deceasedCustomer = deceasedCustomer;
    }
    @Override
    public DateTime getTicketStartDate() {
        return ticketStartDate;
    }
    @Override
    public DateTime getTicketEndDate() {
        return ticketEndDate;
    }
    @Override
    public void setTicketStartDate(DateTime ticketStartDate) {
        this.ticketStartDate = ticketStartDate;
    }
    @Override
    public void setTicketEndDate(DateTime ticketEndDate) {
        this.ticketEndDate = ticketEndDate;
    }
    @Override
    public Long getProductId() {
        return productId;
    }
    @Override
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public DateTime getExchangedDate() {
        return exchangedDate;
    }

    @Override
    public void setExchangedDate(DateTime exchangedDate) {
        this.exchangedDate = exchangedDate;
    }
    @Override
    public String getRefundType() {
        return refundType;
    }
    @Override
    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }
    @Override
    public DateTime getTradedTicketStartDate() {
        return tradedTicketStartDate;
    }
    @Override
    public void setTradedTicketStartDate(DateTime tradedTicketStartDate) {
        this.tradedTicketStartDate = tradedTicketStartDate;
    }
    @Override
    public DateTime getTradedTicketEndDate() {
        return tradedTicketEndDate;
    }
    @Override
    public void setTradedTicketEndDate(DateTime tradedTicketEndDate) {
        this.tradedTicketEndDate = tradedTicketEndDate;
    }
    @Override
    public Long getTradedProductId() {
        return tradedProductId;
    }
    @Override
    public void setTradedProductId(Long tradedProductId) {
        this.tradedProductId = tradedProductId;
    }

    @Override
    public String getRefundTicketStartZone() {
        return refundTicketStartZone;
    }

    @Override
    public String getRefundTicketEndZone() {
        return refundTicketEndZone;
    }

    @Override
    public String getTradedTicketStartZone() {
        return tradedTicketStartZone;
    }

    @Override
    public String getTradedTicketEndZone() {
        return tradedTicketEndZone;
    }

    @Override
    public void setRefundTicketStartZone(String refundTicketStartZone) {
        this.refundTicketStartZone = refundTicketStartZone;
    }

    @Override
    public void setRefundTicketEndZone(String refundTicketEndZone) {
        this.refundTicketEndZone = refundTicketEndZone;
    }

    @Override
    public void setTradedTicketStartZone(String tradedTicketStartZone) {
        this.tradedTicketStartZone = tradedTicketStartZone;
    }

    @Override
    public void setTradedTicketEndZone(String tradedTicketEndZone) {
        this.tradedTicketEndZone = tradedTicketEndZone;
    }

    public String getPurchasedProductDescription() {
        return purchasedProductDescription;
    }

    public String getTradedProductDescription() {
        return tradedProductDescription;
    }

    public void setPurchasedProductDescription(String purchasedProductDescription) {
        this.purchasedProductDescription = purchasedProductDescription;
    }

    public void setTradedProductDescription(String tradedProductDescription) {
        this.tradedProductDescription = tradedProductDescription;
    }
}
