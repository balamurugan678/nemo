package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.utils.DateUtil.formatDate;
import static com.novacroft.nemo.common.utils.DateUtil.parse;
import static com.novacroft.nemo.common.utils.TravelCardDurationUtil.getEndDate;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.DAYS_BEFORE_EXPIRY;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.application_service.AddUnlistedProductService;
import com.novacroft.nemo.tfl.common.application_service.OddPeriodTravelCardService;
import com.novacroft.nemo.tfl.common.application_service.RefundCalculationBasisService;
import com.novacroft.nemo.tfl.common.application_service.TradedTravelCardService;
import com.novacroft.nemo.tfl.common.application_service.ZoneService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.ProductItemType;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;
import com.novacroft.nemo.tfl.common.transfer.DurationPeriodDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.common.util.DurationUtil;

@Service("tradedTravelCardService")
public class TradedTravelCardServiceImpl implements TradedTravelCardService {
    protected static final Logger logger = LoggerFactory.getLogger(TradedTravelCardServiceImpl.class);

    @Autowired
    protected ProductDataService productDataService;

    @Autowired
    protected RefundCalculationBasisService refundCalculationBasisService;

    @Autowired
    protected OddPeriodTravelCardService oddPeriodTravelCardService;

    @Autowired
    protected AddUnlistedProductService addUnlistedProductService;

    @Autowired
    protected ZoneService zoneService;

    public ProductItemDTO getTravelCardItemForTradedTicket(CartItemCmdImpl cmd, ProductItemDTO productItemDTO) {
        CartItemCmdImpl tradedTicketCartItemCmdImpl = cmd.getTradedTicket();
        updateTradedTicketCartItemCmdImpl(tradedTicketCartItemCmdImpl);
        tradedTicketCartItemCmdImpl.setCartType(cmd.getRefundType());
        tradedTicketCartItemCmdImpl.setCardId(cmd.getCardId());
        ProductItemDTO tradedTicketProductItemDTO = getProductItemDTOFromTradedTicketCartItemCmdImpl(tradedTicketCartItemCmdImpl);
        updateTradedTicketProductItemDTO(tradedTicketCartItemCmdImpl, tradedTicketProductItemDTO, cmd.getDeceasedCustomer());
        tradedTicketProductItemDTO.setDateOfRefund(productItemDTO.getDateOfRefund());
        ProductItemDTO updateProductItemDTO = productItemDTO;
        updateProductItemDTO(updateProductItemDTO, tradedTicketProductItemDTO, cmd.getTradedTicket().getExchangedDate());
        tradedTicketProductItemDTO.setRelatedItem(updateProductItemDTO);
        updateTicketOverlappedForTradedTradedTicket(updateProductItemDTO, tradedTicketProductItemDTO);
        return tradedTicketProductItemDTO;
    }

    protected void updateTradedTicketCartItemCmdImpl(CartItemCmdImpl traded) {
        updateTravelCardTypeInTradedTicketCartItemCmdImpl(traded);
        if (!isTravelcardTypeOther(traded.getTravelCardType())) {
            updateEndDateInTradedTicketCartItemCmdImpl(traded);
        }

    }

    protected ProductItemDTO getProductItemDTOFromTradedTicketCartItemCmdImpl(CartItemCmdImpl traded) {
        ProductDTO tradedProduct = null;
        if (isTravelcardTypeOther(traded.getTravelCardType())) {
            DurationPeriodDTO duration = DurationUtil.getDurationForOddPeriod(DateUtil.parse(traded.getStartDate()),
                            DateUtil.parse(traded.getEndDate()));
            tradedProduct = productDataService.findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(duration.getFromDurationCode(),
                            duration.getToDurationCode(), traded.getStartZone(), traded.getEndZone(), DateUtil.parse(traded.getStartDate()),
                            traded.getPassengerType(), traded.getDiscountType(), traded.getTicketType());
            Integer ticketPrice = oddPeriodTravelCardService.getOddPeriodTicketPrice(traded);
            tradedProduct.setTicketPrice(ticketPrice);
        } else {
        	if (traded.getTicketType() == null) {
        		traded.setTicketType(ProductItemType.TRAVEL_CARD.databaseCode());
        	}
            tradedProduct = getTradedProductDTOFromTradedTicketCartItemCmdImpl(traded);
        }
        return new ProductItemDTO(traded.getId(), traded.getCardId(), tradedProduct.getTicketPrice(), tradedProduct.getId(),
                        parse(traded.getStartDate()), parse(traded.getEndDate()), Integer.valueOf(traded.getStartZone()), Integer.valueOf(traded
                                        .getEndZone()), traded.getEmailReminder() + DAYS_BEFORE_EXPIRY, "", traded.getMagneticTicketNumber(),
                        traded.getTicketUnused(), traded.getBackdated(), traded.getBackdatedRefundReasonId(), traded.getDeceasedCustomer(), null,
                        traded.getDateOfRefund(), traded.getCartType(), traded.getDateOfCanceAndSurrender());
    }

    protected void updateTradedTicketProductItemDTO(CartItemCmdImpl tradedTicketCartItemCmdImpl, ProductItemDTO tradedTicketProductItemDTO,
                    Boolean deceasedCustomer) {
        updateRefundCalculationBasisForTradedTicketProductItemDTO(tradedTicketCartItemCmdImpl, tradedTicketProductItemDTO, deceasedCustomer);
    }

    protected void updateProductItemDTO(ProductItemDTO productItem, ProductItemDTO tradedTicketProductItemDTO, DateTime exchangedDate) {
        updateRefundCalculationBasisForProductItemDTO(productItem, tradedTicketProductItemDTO);
        updateExchangedDateForProductItemDTO(productItem, exchangedDate);
    }

    protected void updateTravelCardTypeInTradedTicketCartItemCmdImpl(CartItemCmdImpl traded) {
        addUnlistedProductService.setTravelcardTypeByFormTravelCardType(traded);
    }

    protected void updateEndDateInTradedTicketCartItemCmdImpl(CartItemCmdImpl traded) {
        traded.setEndDate(formatDate(getEndDate(traded.getStartDate(), traded.getTravelCardType())));
    }

    protected ProductDTO getTradedProductDTOFromTradedTicketCartItemCmdImpl(CartItemCmdImpl traded) {
        return productDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(traded.getTravelCardType(), traded.getStartZone(),
                        traded.getEndZone(), DateUtil.parse(traded.getStartDate()), traded.getPassengerType(), traded.getDiscountType(), traded.getTicketType());
    }

    protected void updateRefundCalculationBasisForTradedTicketProductItemDTO(CartItemCmdImpl traded, ProductItemDTO tradedTicket,
                    Boolean deceasedCustomer) {
        tradedTicket.setRefundCalculationBasis(getRefundCalculationBasisForTradedTicket(traded, deceasedCustomer));
    }

    protected void updateRefundCalculationBasisForProductItemDTO(ProductItemDTO productItem, ProductItemDTO tradedTicket) {
        productItem.setRefundCalculationBasis(tradedTicket.getRefundCalculationBasis());
    }

    protected void updateExchangedDateForProductItemDTO(ProductItemDTO productItem, DateTime exchangedDate) {
        if (null != exchangedDate) {
            productItem.setTradedDate(exchangedDate.toDate());
        }
    }

    protected String getRefundCalculationBasisForTradedTicket(CartItemCmdImpl traded, Boolean deceasedCustomer) {
        return refundCalculationBasisService.getRefundCalculationBasis(traded.getRefundCalculationBasis(), traded.getEndDate(), traded.getCartType(),
                        traded.getCardId(), traded.getTravelCardType(), traded.getStartDate(), traded.getStartZone(), traded.getEndZone(),
                        deceasedCustomer);
    }

    protected boolean isTravelcardTypeOther(String travelCardType) {
        return Durations.OTHER.getDurationType().equals(travelCardType);
    }
    
    protected void updateTicketOverlappedForTradedTradedTicket(ProductItemDTO productItem, ProductItemDTO tradedTicket) {
        Boolean isOverlapped = zoneService.isZonesOverlapWithProductItemDTOZones(tradedTicket.getStartZone(), tradedTicket.getEndZone(), productItem,
                        tradedTicket.getStartDate(), tradedTicket.getEndDate());
        tradedTicket.setTicketOverlapped(isOverlapped);
    }
}
