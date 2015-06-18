package com.novacroft.nemo.tfl.common.command;

import org.joda.time.DateTime;

public interface CancelAndSurrenderCmd {

    String getMagneticTicketNumber();

    void setMagneticTicketNumber(String magneticTicketNumber);

    void setRefundDate(DateTime refundDate);

    DateTime getRefundDate();

    void setTicketUnused(Boolean ticketUnused);

    Boolean getTicketUnused();

    void setPreviouslyExchanged(Boolean previouslyExchanged);

    Boolean getPreviouslyExchanged();

    void setDeceasedCustomer(Boolean deceasedCustomer);

    Boolean getDeceasedCustomer();
    
    DateTime getTicketStartDate();
    
    void setTicketStartDate(DateTime ticketStartDate);

    DateTime getTicketEndDate();

    void setTicketEndDate(DateTime ticketEndDate);

    void setProductId(Long productId);
    
    Long getProductId();

    DateTime getExchangedDate();

    void setExchangedDate(DateTime exchangedDate);

    String getRefundType();

    void setRefundType(String refundType);

    void setTradedTicketEndDate(DateTime tradedTicketEndDate);

    DateTime getTradedTicketEndDate();

    void setTradedTicketStartDate(DateTime tradedTicketStartDate);

    DateTime getTradedTicketStartDate();

    void setTradedProductId(Long tradedProductId);

    Long getTradedProductId();

    String getRefundTicketStartZone();

    String getRefundTicketEndZone();

    String getTradedTicketEndZone();

    String getTradedTicketStartZone();

    void setRefundTicketStartZone(String refundTicketStartZone);

    void setTradedTicketStartZone(String tradedTicketStartZone);

    void setRefundTicketEndZone(String refundTicketEndZone);

    void setTradedTicketEndZone(String tradedTicketEndZone);


}
