package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.constant.DateConstant.SHORT_DATE_PATTERN;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.application_service.ProductItemRefundCalculationService;
import com.novacroft.nemo.tfl.common.application_service.RefundCalculationBasisService;
import com.novacroft.nemo.tfl.common.constant.CubicConstant;
import com.novacroft.nemo.tfl.common.constant.ProductItemType;
import com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;
import com.novacroft.nemo.tfl.common.data_service.RefundEngineService;
import com.novacroft.nemo.tfl.common.domain.Refund;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

/**
 * Product item refund calculation service implementation
 */
@Service("productItemRefundCalculationService")
public class ProductItemRefundCalculationServiceImpl implements ProductItemRefundCalculationService {
    static final Logger logger = LoggerFactory.getLogger(ProductItemRefundCalculationServiceImpl.class);

    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern(SHORT_DATE_PATTERN);

    @Autowired
    protected RefundEngineService refundEngineService;

    @Autowired
    protected ProductDataService productDataService;

    @Autowired
    protected RefundCalculationBasisService refundCalculationBasisService;

    @Override
    public Refund calculateRefund(ProductItemDTO productItemDTO) {
        Date dateOfRefund = productItemDTO.getDateOfRefund();
        DateTime startDate = new DateTime(productItemDTO.getStartDate());
        DateTime endDate = new DateTime(productItemDTO.getEndDate());
        ProductDTO productDTO = getProductForProductItemDTO(productItemDTO, startDate, endDate);
        if (Durations.OTHER.getDurationType().equals(productDTO.getDuration())) {
            productDTO.setTicketPrice(productItemDTO.getPrice());
        }
        if(productDTO.getTicketPrice() != null){
            Refund calculatedRefund = calculateRefundForTicket(productItemDTO, startDate, endDate, productDTO, dateOfRefund);
            if (DateUtils.isSameDay(dateOfRefund, productItemDTO.getStartDate())){
                calculatedRefund.setRefundAmount(new Long(productDTO.getTicketPrice()));
            }
            return calculatedRefund;
        }
        return new Refund();
    }

    protected ProductDTO getProductForProductItemDTO(ProductItemDTO productItemDTO, DateTime startDate, DateTime endDate) {
        if (productItemDTO.getProductId() != null) {
            return productDataService.findById(productItemDTO.getProductId(), startDate.toDate());
        } else if (isStartZoneAndEndZoneNotZero(productItemDTO.getStartZone(), productItemDTO.getEndZone())) {
            ProductDTO product = findProduct(productItemDTO.getStartZone(), productItemDTO.getEndZone(), startDate, endDate);
            productItemDTO.setName(product.getProductName());
            return product;
        }
        return null;
    }

    protected boolean isStartZoneAndEndZoneNotZero(Integer startZone, Integer endZone) {
        return startZone != 0 && endZone != 0;
    }

    protected Refund calculateRefundForTicket(ProductItemDTO productItemDTO, DateTime startDate, DateTime endDate, ProductDTO product,
                    Date dateOfRefund) {
        if (productItemDTO.getRelatedItem() != null) {
            return calculateRefundForTradedTicket(productItemDTO, startDate, endDate, product, dateOfRefund);
        } else {
            return calculateRefundForNonTradedTicket(productItemDTO, startDate, endDate, product, dateOfRefund);
        }
    }

    protected Refund calculateRefundForTradedTicket(ProductItemDTO productItemDTO, DateTime startDate, DateTime endDate, ProductDTO product,
                    Date dateOfRefund) {
        ProductItemDTO tradedproductItemDTO = null;
        if (null != productItemDTO.getRelatedItem() && productItemDTO.getRelatedItem().getClass().equals(ProductItemDTO.class)) {
            tradedproductItemDTO = (ProductItemDTO) productItemDTO.getRelatedItem();
        }
        DateTime tradedStartDate = DateTime.parse(DateUtil.formatDate(tradedproductItemDTO.getStartDate()), DATE_FORMAT);
        DateTime tradedEndDate = DateTime.parse(DateUtil.formatDate(tradedproductItemDTO.getEndDate()), DATE_FORMAT);
        Date tradedDate = tradedproductItemDTO.getTradedDate();
        DateTime tradedDateTime = null;
        if (null != tradedDate && !"".equals(tradedDate)) {
            tradedDateTime = DateTime.parse(DateUtil.formatDate(tradedproductItemDTO.getTradedDate()), DATE_FORMAT);
        } else {
            tradedDateTime = tradedStartDate;
        }
        DateTime refundDate = DateTime.parse(DateUtil.formatDate(dateOfRefund), DATE_FORMAT);
        ProductDTO tradedProduct = findProduct(tradedproductItemDTO.getStartZone(), tradedproductItemDTO.getEndZone(), tradedStartDate, tradedEndDate);
        if (Durations.OTHER.getDurationType().equals(tradedProduct.getDuration())) {
            tradedProduct.setTicketPrice(productItemDTO.getRelatedItem().getPrice());
        }

        productItemDTO.getRelatedItem().setName(tradedProduct.getProductName());
        RefundCalculationBasis refundBasis = RefundCalculationBasis.find(refundCalculationBasisService.getRefundCalculationBasisForTradedTickets(
                        tradedProduct.getTicketPrice(), product.getTicketPrice()));
        switch (refundBasis) {
        case TRADE_UP:
            refundBasis = updateRefundCalculationBasisAfterTradeUp(refundBasis, productItemDTO);
            break;
        case TRADE_DOWN:
            refundBasis = updateRefundCalculationBasisAfterTradeDown(refundBasis, productItemDTO);
            break;
        default:
            break;
        }
        return refundEngineService.calculateRefundTradeUpOrDown(tradedStartDate, tradedEndDate, product, tradedProduct, tradedDateTime, refundDate,
                        refundBasis);
    }

    protected Refund calculateRefundForNonTradedTicket(ProductItemDTO productItemDTO, DateTime startDate, DateTime endDate, ProductDTO product,
                    Date dateOfRefund) {
        return refundEngineService.calculateRefund(startDate, endDate, new DateTime(dateOfRefund),
                        RefundCalculationBasis.find(productItemDTO.getRefundCalculationBasis()), product);
    }

    protected ProductDTO findProduct(Integer startZone, Integer endZone, DateTime startDate, DateTime endDate) {
        String travelCardType = refundEngineService.findTravelCardTypeByDuration(startDate, endDate);
        if (Durations.OTHER.getDurationType().equals(travelCardType)) {
            return new ProductDTO(startZone, endZone, startDate.toDateMidnight().toDate(), endDate.toDateMidnight().toDate(),
                            Durations.OTHER.getDurationType(), "Odd Period Ticket Zones " + startZone + " to " + endZone);
        } else {
            //TODO remove constants for passenger type, discount type and ticket type
            return productDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(travelCardType, startZone, endZone,
                            startDate.toDate(), CubicConstant.PASSENGER_TYPE_ADULT_CODE, CubicConstant.NO_DISCOUNT_TYPE_CODE, ProductItemType.TRAVEL_CARD.databaseCode());
        }
    }

    protected RefundCalculationBasis updateRefundCalculationBasisAfterTradeUp(RefundCalculationBasis refundBasis, ProductItemDTO productItemDTO) {
        if (productItemDTO.getRefundCalculationBasis().equals(RefundCalculationBasis.ORDINARY.code())) {
            refundBasis = RefundCalculationBasis.ORDINARY_AFTER_TRADE_UP;
        } else if (productItemDTO.getRefundCalculationBasis().equals(RefundCalculationBasis.PRO_RATA.code())) {
            refundBasis = RefundCalculationBasis.TRADE_UP;
        }
        return refundBasis;
    }

    protected RefundCalculationBasis updateRefundCalculationBasisAfterTradeDown(RefundCalculationBasis refundBasis, ProductItemDTO productItemDTO) {
        if (productItemDTO.getRefundCalculationBasis().equals(RefundCalculationBasis.ORDINARY.code())) {
            refundBasis = RefundCalculationBasis.ORDINARY_AFTER_TRADE_DOWN;
        } else if (productItemDTO.getRefundCalculationBasis().equals(RefundCalculationBasis.PRO_RATA.code())) {
            refundBasis = RefundCalculationBasis.TRADE_DOWN;
        }
        return refundBasis;
    }
}
