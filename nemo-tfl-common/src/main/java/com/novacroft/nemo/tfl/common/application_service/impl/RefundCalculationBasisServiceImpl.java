package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.utils.DateUtil.parse;
import static com.novacroft.nemo.common.utils.TravelCardDurationUtil.getEndDate;
import static com.novacroft.nemo.tfl.common.constant.Refund.REFUND_CALCULATION_BASIS_ORDINARY_MORE_THAN_DAYS;
import static com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis.ORDINARY;
import static com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis.PRO_RATA;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.RefundCalculationBasisService;
import com.novacroft.nemo.tfl.common.application_service.ZoneService;
import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

/**
 * Refund calculation basis service implementation
 */
@Service("refundCalculationBasisService")
public class RefundCalculationBasisServiceImpl implements RefundCalculationBasisService {

    @Autowired
    protected ZoneService zoneService;
    
    @Autowired
    protected CartService cartService;
    
    @Override
    public String getRefundCalculationBasis(String refundCalculationBasis, String endDate, String cartType, Long cardId, String travelCardType, String startDate, Integer startZone, Integer endZone, Boolean deceasedCustomer) {
        return (refundCalculationBasis != null && refundCalculationBasis != PRO_RATA.code()) ? refundCalculationBasis : getRefundCalculationBasis(endDate, cartType, cardId, travelCardType, startDate, startZone, endZone, deceasedCustomer, false);
    }
    
    @Override
    public String getRefundCalculationBasis(String expiryDate, String refundCartType, Long cardId, String travelCardType, String inputStartDate, Integer startZone, Integer endZone, Boolean deceased, Boolean skipOverlapCheck) {
        if (null != deceased && deceased) {
            return PRO_RATA.code();
        } else if (CartType.LOST_REFUND.code().equals(refundCartType) || CartType.STOLEN_REFUND.code().equals(refundCartType)) {
            if (!skipOverlapCheck && checkTravelCardOverlapWithAlreadyAddedTravelCardZones(expiryDate, refundCartType, cardId, travelCardType, inputStartDate, startZone, endZone)) {
                return PRO_RATA.code();
            } else {
                return getRefundCalculationBasisIfMoreThanFiveDaysRemainingForTravelCard(DateUtil.formatDate(new Date()), expiryDate);
            }
        } else if (CartType.CANCEL_SURRENDER_REFUND.code().equals(refundCartType)) {
            return ORDINARY.code();
        }
        return PRO_RATA.code();

    }

    protected boolean checkTravelCardOverlapWithAlreadyAddedTravelCardZones(
	    final String inputEndDate, final String refundCartType,
	    final Long cardId, final String travelCardType,
	    final String inputStartDate, final Integer startZone,
	    final Integer endZone) {
        assert (inputStartDate != null && startZone != null && endZone != null);
        Date startDate = parse(inputStartDate);

        Date endDate;
        if (!travelCardType.equals(Durations.OTHER.getDurationType())) {
            endDate = parse(inputEndDate);
        } else {
            endDate = getEndDate(inputStartDate, travelCardType);
        }

        return isTravelCardOverlapWithAlreadyAddedTravelCardZones(cardId, refundCartType, startDate, endDate, startZone, endZone);
    }
    
    protected boolean isTravelCardOverlapWithAlreadyAddedTravelCardZones(Long cardId, String refundCartType, Date startDate, Date endDate, Integer startZone, Integer endZone) {
        CartDTO cartDTO = cartService.findNotInWorkFlowFlightCartByCardId(cardId);
        if (cartDTO != null && cartDTO.getCartItems() != null) {
            for (ItemDTO itemDTO : cartDTO.getCartItems()) {
                if (itemDTO instanceof ProductItemDTO) {
                    ProductItemDTO productItemDTO = (ProductItemDTO) itemDTO;
                    return isProductItemDTOZonesOverlapWithTravelCardZones(productItemDTO, startDate, endDate, startZone, endZone);
                }
            }    
        }

        return false;
    }
    
    protected boolean isProductItemDTOZonesOverlapWithTravelCardZones(ProductItemDTO productItemDTO, Date startDate, Date endDate, Integer startZone, Integer endZone) {
        if ((productItemDTO.getName().contains(CartAttribute.TRAVEL_CARD) || productItemDTO.getName().contains(CartAttribute.BUS_PASS)) && 
                        (zoneService.isZonesOverlapWithProductItemDTOZones(startZone, endZone, productItemDTO, startDate, endDate))) {
            return true;
        }
        return false;
    }
    
    protected String getRefundCalculationBasisIfMoreThanFiveDaysRemainingForTravelCard(String startDate, String endDate) {
        long dateDiff = DateUtil.getDateDiffWithDaylightSavings(DateUtil.parse(startDate), DateUtil.parse(endDate));
        return (dateDiff > REFUND_CALCULATION_BASIS_ORDINARY_MORE_THAN_DAYS) ? ORDINARY.code() : PRO_RATA.code();
    }
    
    @Override
    public String getRefundCalculationBasisForTradedTickets(ProductItemDTO productItem, ProductItemDTO tradedTicket) {
        assert productItem != null;
        assert productItem.getPrice() != null;
        assert tradedTicket != null;
        assert tradedTicket.getPrice() != null;
        
        return getRefundCalculationBasisForTradedTickets(productItem.getPrice() , tradedTicket.getPrice());
   
    }
    
    @Override
    public String getRefundCalculationBasisForTradedTickets(Integer ticketPrice, Integer tradedTicketPrice) {
        
        return(tradedTicketPrice > ticketPrice ? RefundCalculationBasis.TRADE_UP.code(): RefundCalculationBasis.TRADE_DOWN.code() );
    }

}
