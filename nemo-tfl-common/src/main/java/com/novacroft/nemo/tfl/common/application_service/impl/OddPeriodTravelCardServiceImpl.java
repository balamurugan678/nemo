package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.utils.DateUtil.addMonthsToDate;
import static com.novacroft.nemo.common.utils.DateUtil.formatDate;
import static com.novacroft.nemo.common.utils.DateUtil.getDateDiffIncludingStartDate;
import static com.novacroft.nemo.common.utils.DateUtil.getDaysWithoutMonths;
import static com.novacroft.nemo.common.utils.DateUtil.getDifferenceInMonths;
import static com.novacroft.nemo.common.utils.DateUtil.isAfter;
import static com.novacroft.nemo.common.utils.DateUtil.isBeforeOrEqual;
import static com.novacroft.nemo.common.utils.DateUtil.parse;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.DAYS_BEFORE_EXPIRY;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.OTHER_TRAVELCARD_ANNUAL_ALLOWED_DAYS;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.OTHER_TRAVELCARD_MAXIMUM_ALLOWED_DAYS;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.OTHER_TRAVELCARD_MAXIMUM_ALLOWED_MONTHS;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.OTHER_TRAVELCARD_MONTHLY_MULTIPLAY_RATE;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.OTHER_TRAVEL_CARD;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.OTHER_TRAVEL_CARD_SUBSTITUTION_ANNUAL_TRAVEL_CARD_DURATION_IN_MONTHS;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.ODD_PERIOD_OTHER_TRAVEL_CARD_RESET_ANNUAL;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.TEN;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.THIRTY_FLOAT;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.tfl.common.application_service.OddPeriodTravelCardService;
import com.novacroft.nemo.tfl.common.application_service.ProductService;
import com.novacroft.nemo.tfl.common.application_service.RefundCalculationBasisService;
import com.novacroft.nemo.tfl.common.application_service.TradedTravelCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

@Service("oddPeriodTravelCardService")
public class OddPeriodTravelCardServiceImpl implements OddPeriodTravelCardService {
    static final Logger logger = LoggerFactory.getLogger(OddPeriodTravelCardServiceImpl.class);

    @Autowired
    protected ProductService productService;
    @Autowired
    protected RefundCalculationBasisService refundCalculationBasisService;
    @Autowired
    protected TradedTravelCardService tradedTravelCardService;
    @Autowired
    protected SystemParameterService systemParameterService;

    @Override
    public ProductItemDTO convertCartItemCmdImplToProductItemDTO(CartItemCmdImpl cmd) {
        ProductItemDTO productItemDTO = getOddPeriodProductItemDTO(cmd);
        if (isTradedTicketAvailable(cmd)) {
            return tradedTravelCardService.getTravelCardItemForTradedTicket(cmd, productItemDTO);
        }
        return productItemDTO;
    }

    protected boolean isTradedTicketAvailable(CartItemCmdImpl cmd) {
        return cmd.getTradedTicket() != null && cmd.getTradedTicket().getStartZone() != null && cmd.getTradedTicket().getStartZone() > 0;
    }

    protected ProductItemDTO getOddPeriodProductItemDTO(CartItemCmdImpl cmd) {
        ProductItemDTO productItemDTO = new ProductItemDTO();
        productItemDTO.setPrice(getOddPeriodTicketPrice(cmd));
        productItemDTO.setId(cmd.getId());
        productItemDTO.setCardId(cmd.getCardId());
        productItemDTO.setStartZone(cmd.getStartZone());
        productItemDTO.setEndZone(cmd.getEndZone());
        productItemDTO.setStartDate(parse(cmd.getStartDate()));
        productItemDTO.setEndDate(parse(cmd.getEndDate()));
        productItemDTO.setReminderDate(cmd.getEmailReminder() + DAYS_BEFORE_EXPIRY);
        productItemDTO.setMagneticTicketNumber(cmd.getMagneticTicketNumber());
        productItemDTO.setTicketUnused(cmd.getTicketUnused());
        productItemDTO.setTicketBackdated(cmd.getBackdated());
        productItemDTO.setBackdatedRefundReasonId(cmd.getBackdatedRefundReasonId());
        productItemDTO.setDeceasedCustomer(cmd.getDeceasedCustomer());

        ProductDTO productDTO = productService.getProductByFromAndToDurationAndZonesAndPassengerTypeAndDiscountType(cmd);
        if (productDTO != null) {
            productItemDTO.setProductId(productDTO.getId());
        }

        productItemDTO.setRefundCalculationBasis(getRefundCalculationBasis(cmd));
        productItemDTO.setTradedDate(getExchangeDate(cmd));
        productItemDTO.setDateOfRefund(cmd.getDateOfRefund());
        productItemDTO.setRefundType(cmd.getRefundType());
        productItemDTO.setDateOfCanceAndSurrender(cmd.getDateOfCanceAndSurrender());

        return productItemDTO;
    }

    protected String getRefundCalculationBasis(CartItemCmdImpl cmd) {
        return refundCalculationBasisService.getRefundCalculationBasis(cmd.getRefundCalculationBasis(), cmd.getEndDate(), cmd.getCartType(),
                        cmd.getCardId(), OTHER_TRAVEL_CARD, cmd.getStartDate(), cmd.getStartZone(), cmd.getEndZone(), cmd.getDeceasedCustomer());
    }

    protected Date getExchangeDate(CartItemCmdImpl cmd) {
        if (null != cmd.getTradedTicket() && null != cmd.getTradedTicket().getExchangedDate()) {
            return cmd.getTradedTicket().getExchangedDate().toDate();
        }
        return null;
    }

    @Override
    public Integer getOddPeriodTicketPrice(CartItemCmdImpl cmd) {
        int diffMonths = getDifferenceInMonths(parse(cmd.getStartDate()), parse(cmd.getEndDate()));
        int diffDaysExcludingMonths = getDaysWithoutMonths(parse(cmd.getStartDate()), parse(cmd.getEndDate()));
        long diffDays = getDateDiffIncludingStartDate(cmd.getStartDate(), cmd.getEndDate());
        if (isTicketLongerThanTenMonthsTwelveDays(diffMonths, diffDaysExcludingMonths, diffDays)) {
            cmd.setPrePaidTicketId(null);
            Date annualEndDate = addMonthsToDate(cmd.getStartDate(),
                            systemParameterService.getIntegerParameterValue(OTHER_TRAVEL_CARD_SUBSTITUTION_ANNUAL_TRAVEL_CARD_DURATION_IN_MONTHS));
            if (cmd.getConcessionEndDate() != null && isOddPeriodWithAnnualPrice(cmd,annualEndDate)){
                
                setTicketTypeToOddPeriodWithAnnualPrice(cmd);
            } else {
                cmd.setEndDate(formatDate(annualEndDate));
                resetTicketTypeToAnnual(cmd);
            }
        }
        return calculateOddPeriodTicketPrice(cmd, diffMonths, diffDaysExcludingMonths);
    }
    
    protected boolean isOddPeriodWithAnnualPrice(CartItemCmdImpl cmd, Date annualEndDate){
       return isAfter(cmd.getConcessionEndDate(), cmd.getStartDate())
        && (isBeforeOrEqual(cmd.getConcessionEndDate(), cmd.getEndDate())
        || 
        (isBeforeOrEqual(cmd.getEndDate(), cmd.getConcessionEndDate()) &&
                        isBeforeOrEqual(cmd.getConcessionEndDate(), formatDate(annualEndDate))));
        
    }

    protected void resetTicketTypeToAnnual(CartItemCmdImpl cmd) {
        ProductDTO productDTO = getAnnualTravelcard(cmd);
        cmd.setPrePaidTicketId(productDTO.getId());
        cmd.setProductName(productDTO.getProductName());
        cmd.setPrice(productDTO.getTicketPrice());

    }
    
    protected ProductDTO getAnnualTravelcard(CartItemCmdImpl cmd){
        cmd.setStatusMessage(ODD_PERIOD_OTHER_TRAVEL_CARD_RESET_ANNUAL.textCode());
        cmd.setTravelCardType(Durations.ANNUAL.getDurationType());
        return productService.getProductByFromDurationAndZonesAndPassengerTypeAndDiscountType(cmd);
    }
    
    protected void setTicketTypeToOddPeriodWithAnnualPrice(CartItemCmdImpl cmd){
        ProductDTO productDTO = getAnnualTravelcard(cmd);
        if(productDTO != null){
            cmd.setPrice(productDTO.getTicketPrice());
        }
        cmd.setEndDate(cmd.getConcessionEndDate());
    }

    protected Integer calculateOddPeriodTicketPrice(CartItemCmdImpl cmd, Integer diffMonths, int diffDaysExcludingMonths) {
        if (cmd.getPrice() == null) {
            String substitutionTravelCardType = systemParameterService.getParameterValue(OTHER_TRAVELCARD_SUBSTITUTION_TRAVELCARD_TYPE);
            ProductDTO substituteProduct = productService.getProductByFromAndToDurationAndZonesAndPassengerTypeAndDiscountType(cmd,
                            substitutionTravelCardType);
            if (substituteProduct != null && 
            		substituteProduct.getTicketPrice() != null) {
                double ticketPrice = 0;
                Float monthlyRate = systemParameterService.getFloatParameterValue(OTHER_TRAVELCARD_MONTHLY_MULTIPLAY_RATE);
                double price = monthlyRate * substituteProduct.getTicketPrice() * (diffMonths + diffDaysExcludingMonths / THIRTY_FLOAT);
                price = price / TEN;
                ticketPrice = Math.ceil(price);
                return (int) ticketPrice * TEN;
            }
        }
        return cmd.getPrice();
    }

    protected Boolean isTicketLongerThanTenMonthsTwelveDays(int diffMonths, int diffDaysExcludingMonths, long diffDays) {
        if (isDateDifferenceSatisfyingMaximumAllowedMonthsAndDays(diffMonths, diffDaysExcludingMonths,
                        systemParameterService.getIntegerParameterValue(OTHER_TRAVELCARD_MAXIMUM_ALLOWED_MONTHS),
                        systemParameterService.getIntegerParameterValue(OTHER_TRAVELCARD_MAXIMUM_ALLOWED_DAYS))
                        && (diffDays <= systemParameterService.getIntegerParameterValue(OTHER_TRAVELCARD_ANNUAL_ALLOWED_DAYS))) {
            return true;
        }
        return false;
    }

    protected boolean isDateDifferenceSatisfyingMaximumAllowedMonthsAndDays(int diffMonths, int diffDaysExcludingMonths, int maximumAllowedMonths,
                    int maximumAllowedDays) {
        if (diffMonths > maximumAllowedMonths || (diffMonths == maximumAllowedMonths && diffDaysExcludingMonths > maximumAllowedDays)) {
            return true;
        }
        return false;
    }
}
