package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;


/**
 * Specification for refund calculation service
 */
public interface RefundCalculationBasisService {
    String getRefundCalculationBasis(String refundCalculationBasis, String endDate, String cartType, Long cardId, String travelCardType, String startDate, Integer startZone, Integer endZone, Boolean deceasedCustomer);

    String getRefundCalculationBasis(String expiryDate, String refundCartType, Long cardId, String travelCardType, String inputStartDate, Integer startZone, Integer endZone, Boolean deceased, Boolean skipOverlapCheck);

    String getRefundCalculationBasisForTradedTickets(ProductItemDTO productItem, ProductItemDTO tradedTicket);
    
    String getRefundCalculationBasisForTradedTickets(Integer ticketPrice, Integer tradedTicketPrice);

    

}
