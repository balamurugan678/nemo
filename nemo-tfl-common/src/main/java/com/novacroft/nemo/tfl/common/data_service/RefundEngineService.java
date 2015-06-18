package com.novacroft.nemo.tfl.common.data_service;

import org.joda.time.DateTime;

import com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis;
import com.novacroft.nemo.tfl.common.domain.Refund;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;

public interface RefundEngineService {



    Refund calculateRefundTradeUpOrDown(DateTime ticketStartDate, DateTime ticketEndDate, Long existingProductId, Long tradeUpProductId, DateTime tradeUpDate,DateTime dateofRefund, RefundCalculationBasis refundBasis);

    String findTravelCardTypeByDuration(DateTime ticketStartDate, DateTime ticketEndDate);

    Refund calculateRefund(DateTime ticketStartDate, DateTime ticketEndDate, Integer startZone, Integer endZone, DateTime refundDate, RefundCalculationBasis refundBasis, String prePaidTicketType);

    Refund calculateRefund(DateTime ticketStartDate, DateTime ticketEndDate, Long productId, DateTime refundDate, RefundCalculationBasis refundBasis);

    Refund calculateRefund(DateTime ticketStartDate, DateTime ticketEndDate, DateTime refundDate, RefundCalculationBasis refundBasis, ProductDTO product);

    Refund calculateRefundTradeUpOrDown(DateTime ticketStartDate, DateTime ticketEndDate, ProductDTO existingProduct, ProductDTO tradedProduct,
                    DateTime tradeUpDate,DateTime dateofRefund, RefundCalculationBasis refundBasis);



}
