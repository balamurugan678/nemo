package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.common.constant.DateConstant.OFFSETDAY;
import static com.novacroft.nemo.common.constant.DateConstant.SHORT_DATE_PATTERN;
import static com.novacroft.nemo.common.constant.Durations.ANNUAL;
import static com.novacroft.nemo.common.constant.Durations.MONTH;
import static com.novacroft.nemo.common.constant.Durations.OTHER;
import static com.novacroft.nemo.common.constant.Durations.SEVEN_DAYS;
import static com.novacroft.nemo.common.constant.Durations.SIX_MONTH;
import static com.novacroft.nemo.common.constant.Durations.THREE_MONTH;
import static com.novacroft.nemo.common.constant.Durations.findByType;
import static com.novacroft.nemo.common.utils.DateUtil.isStartAndEndMonthCheckForAvoidingOffsetDayAddition;
import static com.novacroft.nemo.common.utils.StringUtil.AND;
import static com.novacroft.nemo.common.utils.StringUtil.EMPTY_STRING;
import static com.novacroft.nemo.common.utils.StringUtil.WHITE_SPACE;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.DAYS_IN_TEMPLATE_MONTH;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.DAYS_IN_WEEK;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.DAYS_IN_YEAR;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.ONE_MONTH;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.ROUND_UP_DAYS_TO_WEEK_THRESHOLD;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.SIX_MONTHS;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.TEN_PRECISION_CONSTANT;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.TFL_ONE_FIFTH_MULTIPLIER;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.TFL_REFUND_MULTIPLIER;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.TFL_THIRTY_MULTIPLIER;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.THREE_MONTHS;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.TWELVE_MONTHS;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.USAGE_DURATION_DAY;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.USAGE_DURATION_DAYS;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.USAGE_DURATION_MONTH;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.USAGE_DURATION_MONTHS;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.USAGE_DURATION_WEEK;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.USAGE_DURATION_WEEKS;

import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.utils.CurrencyUtil;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.constant.CubicConstant;
import com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis;
import com.novacroft.nemo.tfl.common.constant.RefundConstants;
import com.novacroft.nemo.tfl.common.constant.RefundConstants.REFUND_REASON_KEY;
import com.novacroft.nemo.tfl.common.constant.RefundConstants.UsageDuration;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;
import com.novacroft.nemo.tfl.common.data_service.ProductItemDataService;
import com.novacroft.nemo.tfl.common.data_service.RefundEngineService;
import com.novacroft.nemo.tfl.common.domain.Refund;
import com.novacroft.nemo.tfl.common.transfer.DurationPeriodDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;
import com.novacroft.nemo.tfl.common.util.DurationUtil;

@Service("refundEngineService")
public class RefundEngineServiceImpl implements RefundEngineService {

    public static final long PRODUCT_ID = 4L;
    public static final String ROUNDED_UP = "Rounded up";
    public static final String ROUNDED_DOWN = "Rounded Down";
    public static final String ONE_MONTH_OR_LESS = "1 month or less";
    public static final String ORDINARY_AFTER_TRADE_UP = "Ordinary after Trade Up";
    public static final String ORDINARY_AFTER_TRADE_DOWN = "Ordinary after Trade Down";
    public static final String PRO_RATA_REFUND = "Pro rata refund";
    public static final String PRO_RATA_AFTER_TRADE_UP = "Pro rata after Trade Up";
    public static final String ORDINARY_BASIS_REFUND = "Ordinary basis refund";
    public static final String ODD_PERIOD_REFUND = "Odd period refund";
    public static final String GREATER_THAN_A_MONTH = "Greater than a month";
    public static final Boolean ALWAYS_INCLUDE_OFFSET = Boolean.TRUE;

    static final Logger logger = LoggerFactory.getLogger(RefundEngineServiceImpl.class);

    @Autowired
    protected ProductItemDataService productitemDataService;
    @Autowired
    protected ProductDataService productDataService;

    @Override
    public Refund calculateRefund(DateTime ticketStartDate, DateTime ticketEndDate, Integer startZone, Integer endZone, DateTime refundDate,
                    RefundCalculationBasis refundBasis, String prePaidTicketType) {
        ProductDTO product = new ProductDTO();
        Refund refund = new Refund();

        String travelCardDuration = findTravelCardTypeByDuration(ticketStartDate, ticketEndDate);
        if (travelCardDuration.equals(OTHER.getDurationType())) {
            product.setId(PRODUCT_ID);
            product.setStartZone(startZone);
            product.setEndZone(endZone);
        } else {
            //TODO Remove constants for passenger type and discount type
            product = productDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(travelCardDuration, startZone, endZone,
                            ticketStartDate.toDate(), CubicConstant.PASSENGER_TYPE_ADULT_CODE, CubicConstant.NO_DISCOUNT_TYPE_CODE, prePaidTicketType);
            refund.setRefundReasoning(REFUND_REASON_KEY.ORIGINAL_TICKET_PRICE.toString(), product.getTicketPrice().toString());
            refund.setRefundReasoning(REFUND_REASON_KEY.FORMATTED_ORIGINAL_TICKET_PRICE.toString(),
                            CurrencyUtil.formatPenceWithHtmlCurrencySymbol(product.getTicketPrice()));

        }

        refund = refundByRefundBasis(ticketStartDate, ticketEndDate, refundDate, refundBasis, product, refund, startZone, endZone, prePaidTicketType);
        return refund;
    }

    @Override
    public Refund calculateRefund(DateTime ticketStartDate, DateTime ticketEndDate, Long productId, DateTime refundDate,
                    RefundCalculationBasis refundBasis) {
        ProductDTO product = productDataService.findById(productId, ticketStartDate.toDate());

        return calculateRefund(ticketStartDate, ticketEndDate, refundDate, refundBasis, product);
    }

    @Override
    public Refund calculateRefund(DateTime ticketStartDate, DateTime ticketEndDate, DateTime refundDate, RefundCalculationBasis refundBasis,
                    ProductDTO product) {
        assert (ticketEndDate.isAfter(ticketStartDate));
        Refund refund = new Refund();
        refund.setRefundReasoning(REFUND_REASON_KEY.ORIGINAL_TICKET_PRICE.toString(), product.getTicketPrice() != null ? product.getTicketPrice()
                        .toString() : "");
        Integer startZone = product.getStartZone();
        Integer endZone = product.getEndZone();

        refund = refundByRefundBasis(ticketStartDate, ticketEndDate, refundDate, refundBasis, product, refund, startZone, endZone, product.getType());

        return refund;

    }

    private Refund refundByRefundBasis(DateTime ticketStartDate, DateTime ticketEndDate, DateTime refundDate, RefundCalculationBasis refundBasis,
                    ProductDTO product, Refund refund, Integer startZone, Integer endZone, String type) {
        switch (refundBasis) {

        case PRO_RATA:
            if (isNotOddPeriodTicket(ticketStartDate, ticketEndDate)) {
                Long productId = getProductIdForNotOddPeriodTicket(ticketStartDate, ticketEndDate, startZone, endZone, type);
                refund = calculateProRataRefund(ticketStartDate, ticketEndDate, productId, startZone, endZone, refundDate);
            } else {
                refund = calculateProRataRefundForOddPeriods(ticketStartDate, ticketEndDate, startZone, endZone, refundDate, product);
            }

            break;
        case ORDINARY:
            calculateRefundableMonthsWeeksAndDays(refund, ticketStartDate, refundDate);
            if (isNotOddPeriodTicket(ticketStartDate, ticketEndDate)) {
                ProductDTO weeklyTicket = findSimilarProductWithDifferentPeriod(startZone, endZone, SEVEN_DAYS.getDurationType(), ticketStartDate, type);
                refund = calculateOrdinaryRefund(ticketStartDate, refundDate, refund, product.getTicketPrice(), weeklyTicket.getTicketPrice());
            } else {
                refund = calculateOrdinaryRefundForOddPeriods(ticketStartDate, refundDate, startZone, endZone, type);
            }
            break;
        default:
            break;
        }
        resetRefundAmountToZeroWhenNegative(refund);
        /*
         * Effective refund date: Thu 6 Mar 2014 Thu 6 Mar 2014 Usage period: 2 days 2 days Chargeable days: 2 2 Charge per day: £6.2800 † £3.4411 §
         * Usage charge (rounded up): £12.60 £6.90 Refund amount: £1243.40 £1249.10 Percentage of ticket remaining: 99.5% 99.5% Percentage of ticket
         * refunded: 99.0% 99.5% Admin fee: £10.00 £10.00 Deposit refund: n/a n/a Customer receives: £1233.40 £1239.10
         */
        DateTimeFormatter dateFormat = DateTimeFormat.forPattern(SHORT_DATE_PATTERN);

        refund.setRefundReasoning(REFUND_REASON_KEY.START_DATE.toString(), dateFormat.print(ticketStartDate));
        refund.setRefundReasoning(REFUND_REASON_KEY.END_DATE.toString(), dateFormat.print(ticketEndDate));
        refund.setRefundReasoning(REFUND_REASON_KEY.EFFECTIVE_REFUND_DATE.toString(), dateFormat.print(refundDate));

        refund.setRefundReasoning(REFUND_REASON_KEY.USAGE_PERIOD.toString(), calculateUsagePeriod(ticketStartDate, refundDate));
        
        return setRefundAmountToZeroIfRefundAmountNegative(refund);

    }
    
    protected String calculateUsagePeriod(DateTime ticketStartDate, DateTime refundDate){
        Integer weeksUsed = 0;
        Integer daysUsed = 0;
        Integer monthsUsed = calculateFullMonthsBetweenDates(ticketStartDate, refundDate);
        if (monthsUsed > 0) {
            daysUsed = calculateNumberOfUsedDays(calculateMonthUsage(ticketStartDate, refundDate), refundDate,
                            monthsUsed > 0);
        } else {
            daysUsed = calculateNumberOfUsedDays(ticketStartDate, refundDate, monthsUsed > 0);
            weeksUsed = calculateFullWeeksInNumberOfDays(daysUsed);
            daysUsed = calculateDaysRemainderInNumberOfDays(daysUsed);
        }
        return formatUsageDuration(monthsUsed, weeksUsed, daysUsed);
    }
    
    protected String formatUsageDuration(Integer monthsUsed, Integer weeksUsed, Integer daysUsed){
        String usageDuration = EMPTY_STRING;
        if(monthsUsed > 0){
            usageDuration = monthsUsed + WHITE_SPACE 
                            + (monthsUsed == 1? USAGE_DURATION_MONTH : USAGE_DURATION_MONTHS);
        } else if(weeksUsed > 0){
            usageDuration = weeksUsed + WHITE_SPACE 
                            + (weeksUsed == 1? USAGE_DURATION_WEEK : USAGE_DURATION_WEEKS);
        }
        if(daysUsed != 0){
            if(monthsUsed > 0 || weeksUsed > 0){
                usageDuration += WHITE_SPACE + AND + WHITE_SPACE;    
            }
            usageDuration += daysUsed + WHITE_SPACE + (daysUsed == 1 ? USAGE_DURATION_DAY : USAGE_DURATION_DAYS);
        }
        return usageDuration;
    }

    protected void resetRefundAmountToZeroWhenNegative(Refund refund) {
        if (refund.getRefundAmount() < 0) {
            refund.setRefundAmount(new Long(0));
        }
    }

    protected Long getProductIdForNotOddPeriodTicket(DateTime ticketStartDate, DateTime ticketEndDate, Integer startZone, Integer endZone, String type) {
        if (ticketStartDate.plusDays(DAYS_IN_WEEK - OFFSETDAY).equals(ticketEndDate)) {
          //TODO Remove constants for passenger type and discount type
            ProductDTO productDTO = productDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(SEVEN_DAYS.getDurationType(),
                            startZone, endZone, ticketStartDate.toDate(), CubicConstant.PASSENGER_TYPE_ADULT_CODE,
                            CubicConstant.NO_DISCOUNT_TYPE_CODE, type);
            return productDTO.getId();
        }

        if (isMonthlyTicket(ticketStartDate, ticketEndDate)) {
          //TODO Remove constants for passenger type and discount type
            ProductDTO productDTO = productDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(MONTH.getDurationType(), startZone,
                            endZone, ticketStartDate.toDate(), CubicConstant.PASSENGER_TYPE_ADULT_CODE, CubicConstant.NO_DISCOUNT_TYPE_CODE, type);
            return productDTO.getId();
        }

        if (ticketStartDate.plusMonths(THREE_MONTHS).minusDays(OFFSETDAY).equals(ticketEndDate)) {
          //TODO Remove constants for passenger type and discount type
            ProductDTO productDTO = productDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(THREE_MONTH.getDurationType(),
                            startZone, endZone, ticketStartDate.toDate(), CubicConstant.PASSENGER_TYPE_ADULT_CODE,
                            CubicConstant.NO_DISCOUNT_TYPE_CODE, type);
            return productDTO.getId();
        }

        if (ticketStartDate.plusMonths(SIX_MONTHS).minusDays(OFFSETDAY).equals(ticketEndDate)) {
          //TODO Remove constants for passenger type and discount type
            ProductDTO productDTO = productDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(SIX_MONTH.getDurationType(),
                            startZone, endZone, ticketStartDate.toDate(), CubicConstant.PASSENGER_TYPE_ADULT_CODE,
                            CubicConstant.NO_DISCOUNT_TYPE_CODE, type);
            return productDTO.getId();
        }

        if (ticketStartDate.plusMonths(TWELVE_MONTHS).minusDays(OFFSETDAY).equals(ticketEndDate)) {
          //TODO Remove constants for passenger type and discount type
            ProductDTO productDTO = productDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(ANNUAL.getDurationType(), startZone,
                            endZone, ticketStartDate.toDate(), CubicConstant.PASSENGER_TYPE_ADULT_CODE, CubicConstant.NO_DISCOUNT_TYPE_CODE, type);
            return productDTO.getId();
        }
        DurationPeriodDTO durationPeriod = DurationUtil.getDurationForOddPeriod(ticketStartDate.toDate(), ticketEndDate.toDate());
      //TODO Remove constants for passenger type and discount type
        ProductDTO productDTO = productDataService.findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(
                        durationPeriod.getFromDurationCode(), durationPeriod.getToDurationCode(), startZone, endZone, ticketStartDate.toDate(),
                        CubicConstant.PASSENGER_TYPE_ADULT_CODE, CubicConstant.NO_DISCOUNT_TYPE_CODE, type);
        return productDTO.getId();
    }

    protected Refund setRefundAmountToZeroIfRefundAmountNegative(Refund refund) {
        Refund updatedRefund = refund;
        if (updatedRefund.getRefundAmount() <= 0) {
            updatedRefund.setRefundAmount(0L);
        }
        return updatedRefund;
    }

    private Refund calculateOrdinaryRefund(DateTime ticketStartDate, DateTime refundDate, Refund refund, Integer ticketPrice,
                    Integer weeklyTicketPrice) {
        refund.setRefundReasoning(REFUND_REASON_KEY.REFUND_BASIS.toString(), ORDINARY_BASIS_REFUND);
        switch (calculateUsageDuration(ticketStartDate, refundDate)) {
        case GREATERTHANAMONTH:
            calculateOrdinaryBasisRefundWhenPeriodIsGreaterThanOneMonth(refund, ticketPrice, weeklyTicketPrice, refund.getRefundableWeeks(),
                            refund.getRefundableDays());
            refund.setRefundReasoning(REFUND_REASON_KEY.USAGE_DURATION.toString(), GREATER_THAN_A_MONTH);

            return refund;

        case ONEMONTHORLESS:
            calculateOrdinaryBasisRefundWhenPeriodisOneMonthOrLess(refund, ticketPrice, weeklyTicketPrice, refund.getRefundableWeeks(),
                            refund.getRefundableDays());
            refund.setRefundReasoning(REFUND_REASON_KEY.USAGE_DURATION.toString(), ONE_MONTH_OR_LESS);
            return refund;

        default:
            break;
        }
        return null;
    }

    @Override
    public Refund calculateRefundTradeUpOrDown(DateTime ticketStartDate, DateTime ticketEndDate, Long existingProductId, Long tradedProductId,
                    DateTime tradeUpDate,DateTime dateofRefund, RefundCalculationBasis refundBasis) {

        Refund refund = new Refund();
        ProductDTO existingProduct = productDataService.findById(existingProductId, tradeUpDate.toDate());
        ProductDTO tradedProduct = productDataService.findById(tradedProductId, tradeUpDate.toDate());
        refund.setRefundableDays(calculateNumberOfUsedDays(ticketStartDate,tradeUpDate,  false));
        Integer ticketFactor = calculateTicketFactor(existingProduct.getDuration(), ticketStartDate, ticketEndDate);
        Double result;
        switch (refundBasis) {
        case TRADE_UP:
            result =  calculateTradeRefund(refund, tradedProduct.getTicketPrice(), existingProduct.getTicketPrice(), ticketFactor);
            roundBasedOnType(refund, result, PRO_RATA_AFTER_TRADE_UP);
            break;
        case TRADE_DOWN:
            result = calculateTradeRefund(refund, existingProduct.getTicketPrice(), tradedProduct.getTicketPrice(), ticketFactor);
            roundBasedOnType(refund, result, PRO_RATA_REFUND);
            break;
            
        case ORDINARY_AFTER_TRADE_UP:
            calculateOrdinaryBasisRefundAfterTradeUp(refund,ticketStartDate, ticketEndDate, tradedProduct, tradedProduct, existingProduct, tradeUpDate, dateofRefund);
            break;
        default:
            break;
        }

        refund.setRefundReasoning(REFUND_REASON_KEY.CHARGEABLE_DAYS.toString(), refund.getRefundableDays().toString());
        refund.setRefundReasoning(REFUND_REASON_KEY.TRADED_TICKET_PRICE.toString(), tradedProduct.getTicketPrice().toString());
        refund.setRefundReasoning(REFUND_REASON_KEY.FORMATTED_TRADED_TICKET_PRICE.toString(),
                        CurrencyUtil.formatPenceWithHtmlCurrencySymbol(tradedProduct.getTicketPrice()));

        return setRefundAmountToZeroIfRefundAmountNegative(refund);

    }
    @Override
    public Refund calculateRefundTradeUpOrDown(DateTime ticketStartDate, DateTime ticketEndDate, ProductDTO existingProduct, ProductDTO tradedProduct,
                    DateTime tradeUpDate,DateTime dateofRefund, RefundCalculationBasis refundBasis) {

        Refund refund = new Refund();
        refund.setRefundableDays(calculateNumberOfUsedDays(ticketStartDate,tradeUpDate,  false));
        Integer ticketFactor = calculateTicketFactor(existingProduct.getDuration(), ticketStartDate, ticketEndDate);
        Double result;
        switch (refundBasis) {
        case TRADE_UP:
            result =  calculateTradeRefund(refund, tradedProduct.getTicketPrice(), existingProduct.getTicketPrice(), ticketFactor);
            roundBasedOnType(refund, result, PRO_RATA_AFTER_TRADE_UP);
            break;
        case TRADE_DOWN:
            result = calculateTradeRefund(refund, existingProduct.getTicketPrice(), tradedProduct.getTicketPrice(), ticketFactor);
            roundBasedOnType(refund, result, PRO_RATA_REFUND);
            break;
            
        case ORDINARY_AFTER_TRADE_UP:
            calculateOrdinaryBasisRefundAfterTradeUp(refund,ticketStartDate, ticketEndDate, tradedProduct, tradedProduct, existingProduct, tradeUpDate, dateofRefund);
            break;
        default:
            break;
        }

        refund.setRefundReasoning(REFUND_REASON_KEY.CHARGEABLE_DAYS.toString(), refund.getRefundableDays().toString());
        refund.setRefundReasoning(REFUND_REASON_KEY.TRADED_TICKET_PRICE.toString(), tradedProduct.getTicketPrice().toString());
        refund.setRefundReasoning(REFUND_REASON_KEY.FORMATTED_TRADED_TICKET_PRICE.toString(),
                        CurrencyUtil.formatPenceWithHtmlCurrencySymbol(tradedProduct.getTicketPrice()));

        return setRefundAmountToZeroIfRefundAmountNegative(refund);

    }

    private double calculateTradeRefund(Refund refund, Integer tradedInTicketprice, Integer newTicketprice, Integer ticketFactor) {
        Double result = (newTicketprice - tradedInTicketprice)
                        - ((newTicketprice - tradedInTicketprice) * ((double) refund.getRefundableDays() / ticketFactor));
        return result;
    }

    protected Refund calculateProRataRefundForOddPeriods(DateTime ticketStartDate, DateTime ticketEndDate, Integer startZone, Integer endZone,
                    DateTime refundDate, ProductDTO product) {
        Refund refund = new Refund();
        refund.setRefundReasoning(REFUND_REASON_KEY.REFUND_BASIS.toString(), PRO_RATA_REFUND);

        Integer sevenDayProductPrice = 0;

      //TODO Remove constants for passenger type and discount type
        ProductDTO productDTO = productDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(SEVEN_DAYS.getDurationType(), startZone, endZone,
                        ticketStartDate.toDate(), CubicConstant.PASSENGER_TYPE_ADULT_CODE, CubicConstant.NO_DISCOUNT_TYPE_CODE, product.getType());
        if (null != productDTO.getDuration()) {
            sevenDayProductPrice = productDTO.getTicketPrice();
        }

        calculateRefundableMonthsWeeksAndDays(refund, ticketStartDate, ticketEndDate);
        Double oddPeriodTicketPriceDbl = roundUpToNearestTenPence(refund, sevenDayProductPrice);
        Integer totalDays = calculateTicketFactor(OTHER.getDurationType(), ticketStartDate, ticketEndDate);
        Integer usedDays = calculateNumberOfUsedDays(ticketStartDate, refundDate, ALWAYS_INCLUDE_OFFSET) + OFFSETDAY;
        Double result = oddPeriodTicketPriceDbl - ((oddPeriodTicketPriceDbl / totalDays) * usedDays);

        roundBasedOnType(refund, result, PRO_RATA_REFUND);

        refund.setRefundReasoning(REFUND_REASON_KEY.ORIGINAL_TICKET_PRICE.toString(), String.valueOf(oddPeriodTicketPriceDbl));
        refund.setRefundReasoning(REFUND_REASON_KEY.USAGE_PERIOD.toString(), usedDays + " days");

        return refund;
    }

    protected Double roundUpToNearestTenPence(Refund refund, Integer sevenDayProductPrice) {
        Double oddPeriodTicketPriceDbl = (((TFL_REFUND_MULTIPLIER * Double.valueOf(sevenDayProductPrice) * (refund.getRefundableMonths() + (Double
                        .valueOf(refund.getRefundableDays()) / TFL_THIRTY_MULTIPLIER))))) / RefundConstants.HUNDRED_PRECISION_CONSTANT;
        oddPeriodTicketPriceDbl = Math.ceil((oddPeriodTicketPriceDbl * RefundConstants.TEN_PRECISION_CONSTANT)) / RefundConstants.TEN_PRECISION_CONSTANT
                        * RefundConstants.HUNDRED_PRECISION_CONSTANT;
        return oddPeriodTicketPriceDbl;
    }

    /**
     * Round to the nearest whole 10 pence based on refund type
     * 
     * @param refund
     * @param unroundedTicketPrice
     * @param type
     * @return
     */
    protected Refund roundBasedOnType(Refund refund, Double unroundedTicketPrice, String type) {
        if (ORDINARY_AFTER_TRADE_DOWN.equalsIgnoreCase(type) || ORDINARY_AFTER_TRADE_UP.equalsIgnoreCase(type)
                        || ORDINARY_BASIS_REFUND.equalsIgnoreCase(type)|| PRO_RATA_REFUND.equalsIgnoreCase(type)) {
            refund.setRefundReasoning(REFUND_REASON_KEY.ROUNDING.toString(), ROUNDED_DOWN);
            long longValue = ((Double) (Math.floor(unroundedTicketPrice.intValue() / TEN_PRECISION_CONSTANT) * TEN_PRECISION_CONSTANT)).intValue();
            refund.setRefundAmount(longValue);
        } else if (ODD_PERIOD_REFUND.equalsIgnoreCase(type)|| PRO_RATA_AFTER_TRADE_UP.equalsIgnoreCase(type)) {
            refund.setRefundReasoning(REFUND_REASON_KEY.ROUNDING.toString(), ROUNDED_UP);
            long longValue = ((Double) (Math.ceil(unroundedTicketPrice / TEN_PRECISION_CONSTANT) * TEN_PRECISION_CONSTANT)).intValue();
            refund.setRefundAmount(longValue);
        }
        return refund;
    }

    protected Refund calculateOrdinaryRefundForOddPeriods(DateTime ticketStartDate, DateTime ticketEndDate, Integer startZone, Integer endZone, String type) {
        Refund refund = new Refund();
        refund.setRefundReasoning(REFUND_REASON_KEY.REFUND_BASIS.toString(), ORDINARY_BASIS_REFUND);

        Integer sevenDayProductPrice = 0;

      //TODO Remove constants for passenger type and discount type
        ProductDTO productDTO = productDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(SEVEN_DAYS.getDurationType(), startZone,
                        endZone, ticketStartDate.toDate(), CubicConstant.PASSENGER_TYPE_ADULT_CODE, CubicConstant.NO_DISCOUNT_TYPE_CODE, type);
        if (null != productDTO.getDuration()) {
            sevenDayProductPrice = productDTO.getTicketPrice();
        }

        calculateRefundableMonthsWeeksAndDays(refund, ticketStartDate, ticketEndDate);
        Double result = TFL_REFUND_MULTIPLIER * sevenDayProductPrice
                        * (refund.getRefundableMonths() + (Double.valueOf(refund.getUsedDays()) / TFL_THIRTY_MULTIPLIER));
        roundBasedOnType(refund, result, ODD_PERIOD_REFUND);
        refund.setRefundReasoning(REFUND_REASON_KEY.ORIGINAL_TICKET_PRICE.toString(), String.valueOf(result.longValue()));
        return refund;
    }

    /**
     * Applicable to ordinary basis refunds greater than one month and OddPeriodRefunds
     * 
     * Formula = TP - (3.84 x 7D x (M + (D/30))) • Where TP is the original ticket price • 7D is the price of the equivalent weekly ticket • M is the
     * number of full month(s) used. • D is the number of days remaining after the identification of the full month(s).
     * 
     * @param refund
     *            holds associated details for a single refund
     * @param ticketPrice
     *            the ticket price at time of purchase
     * @param equivalentWeeklyTicketPrice
     *            ticket price at time if purchase for equivalent weekly ticket
     * @param refundableWeeks
     *            the amount of full weeks to be refunded to the customer
     * @param refundableDays
     *            the amount of full days to be refunded to the customer
     * @return {@link Refund} holds associated details for a single refund
     */
    protected Refund calculateOrdinaryBasisRefundWhenPeriodIsGreaterThanOneMonth(Refund refund, Integer ticketPrice,
                    Integer equivalentWeeklyTicketPrice, Integer refundableWeeks, Integer refundableDays) {
        Map<String, String> refundReasonings = refund.getRefundReasonings();
        refundReasonings.put(REFUND_REASON_KEY.REFUND_BASIS.toString(), ORDINARY_BASIS_REFUND);

        Double result = ticketPrice
                        - (TFL_REFUND_MULTIPLIER * equivalentWeeklyTicketPrice * (refund.getRefundableMonths() + (Double.valueOf(refund
                                        .getRefundableDays()) / TFL_THIRTY_MULTIPLIER)));
        roundBasedOnType(refund, result, ORDINARY_BASIS_REFUND);
        return refund;

    }

    /**
     * Formula = A - ((B * C) + (D * E)) • Where A is the original ticket price • B is the price of the equivalent weekly ticket • C is the number
     * full weeks used (7 days, Note the last week will be considered a full week if the days is 5 or 6) • E is the number of days remaining after the
     * identification of the number of the full weeks (Note number of days will be between1 to 4, anything above 4 will be considered as a week.) • D
     * is the daily cost of when less than one month is used by the Customer = B * 0.2
     * 
     * @param refund
     *            holds associated details for a single refund
     * @param originalTicketPrice
     *            the ticket price at time of purchase
     * @param equivalentWeeklyTicketPrice
     *            ticket price at time if purchase for equivalent weekly ticket
     * @param numberOfFullWeeksUsed
     *            the number of full weeks from date of purchase to refund request date
     * @param numberOfFullDaysRemaining
     *            the number of days remaining on the ticket to be refunded
     * @return {@link Refund} holds associated details for a single refund
     */
    protected Refund calculateOrdinaryBasisRefundWhenPeriodisOneMonthOrLess(Refund refund, Integer originalTicketPrice,
                    Integer equivalentWeeklyTicketPrice, Integer numberOfFullWeeksUsed, Integer numberOfFullDaysRemaining) {

        Double result = originalTicketPrice
                        - ((equivalentWeeklyTicketPrice * numberOfFullWeeksUsed) + ((TFL_ONE_FIFTH_MULTIPLIER * equivalentWeeklyTicketPrice) * numberOfFullDaysRemaining));
        roundBasedOnType(refund, result, ORDINARY_BASIS_REFUND);
        return refund;
    }

    /**
     * Formula = A - ((B * C) + (D * E)) • Where A is the original ticket price • B is the price of the equivalent weekly ticket • C is the number
     * full weeks used (7 days, Note the last week will be considered a full week if the days is 5 or 6) • E is the number of days remaining after the
     * identification of the number of the full weeks (Note number of days will be between1 to 4, anything above 4 will be considered as a week.) • D
     * is the daily cost of when less than one month is used by the Customer = B * 0.2
     * 
     * @param refund
     *            holds associated details for a single refund
     * @param originalTicketPrice
     *            the ticket price at time of purchase
     * @param equivalentWeeklyTicketPrice
     *            ticket price at time if purchase for equivalent weekly ticket
     * @param numberOfFullWeeksUsed
     *            the number of full weeks from date of purchase to refund request date
     * @param numberOfFullDaysRemaining
     *            the number of days remaining on the ticket to be refunded
     * @return {@link Refund} holds associated details for a single refund
     */
    protected Refund calculateProRataRefund(DateTime ticketStartDate, DateTime ticketEndDate, Long productId, Integer startZone, Integer endZone,
                    DateTime refundDate) {

        Refund refund = new Refund();
        Map<String, String> refundReasonings = refund.getRefundReasonings();
        refundReasonings.put(REFUND_REASON_KEY.REFUND_BASIS.toString(), PRO_RATA_REFUND);

        ProductDTO product = productDataService.findById(productId, ticketStartDate.toDate());
        Integer originalTicketPrice = product.getTicketPrice();
        refund.setRefundableDays(calculateNumberOfUsedDays(ticketStartDate, refundDate, false));
        Integer ticketFactor = calculateTicketFactor(product.getDuration(), ticketStartDate, ticketEndDate);

        Double result = (double) (originalTicketPrice - ((originalTicketPrice / (double) ticketFactor) * refund.getRefundableDays()));
        roundBasedOnType(refund, result, PRO_RATA_REFUND);

        return refund;
    }

    /**
     * Formula = TTP – (OTC + ATC), Where • TTP = Total Ticket Price after Trade up • OTC = Original Ticket Cost from start date to refund date • ATC
     * = Additional Ticket Cost added from trade date to refund date
     * 
     * TTP = TP0 + (TP2 - TP1) - ((TP2 -TP1) *(DU/X)), where • TP0 is the price of the original ticket at the time of initial purchase. • TP1 is the
     * price of the original ticket at the exchange date. • TP2 is the price of the trade up ticket at the exchange date. • DU is the number of days
     * used on the original ticket before exchange. • X is the factor of days the product represents (e.g. 7 days, 30 days, 365 days and odd days.)
     * 
     * OTC = 3.84 x 7D1 x (M1 + D1/30), where • 7D1 is the cost of the 7 day ticket of the original ticket at the time of purchase • M1 is number of
     * whole months between the original ticket’s start date and refund date. • D1 is the number of remaining odd days after the whole months have
     * been identified above.
     * 
     * ATC = 3.84 x (7D2 - 7D3) x (M2 + D2/30), where • 7D2 is the cost of the 7 day ticket of the trade up ticket at the time of exchange • 7D3 is
     * the cost of the 7 day ticket of the original ticket at the time of exchange • M2 is number of whole months between the exchange date and refund
     * date. • D2 is the number of remaining odd days after the whole months have been identified above.
     * 
     * @param ticketStartDate
     *            The date the ticket was purchased
     * @param ticketEndDate
     *            The ticket expiry date
     * @param historicalProductId
     *            The ticket id at the time it was purchased
     * @param existingProductId
     *            the product id of the equivalent product at the time of refund
     * @param tradeUpProductId
     *            the product id of the product that was traded up to.
     * @param tradeUpDate
     *            the date the trade up was requested
     * @param refundDate
     *            the date the refund was requested
     * @return {@link Refund} holds associated details for a single refund
     */
    public Refund calculateOrdinaryBasisRefundAfterTradeUp(Refund refund,DateTime ticketStartDate, DateTime ticketEndDate, ProductDTO historicalProduct,
                    ProductDTO existingProduct,ProductDTO tradeUpProduct, DateTime tradeUpDate, DateTime refundDate) {
        Map<String, String> refundReasonings = refund.getRefundReasonings();
        refundReasonings.put(REFUND_REASON_KEY.REFUND_BASIS.toString(), ORDINARY_AFTER_TRADE_UP);

        Integer historicalTicketprice = historicalProduct.getTicketPrice();

        Integer tradedInTicketprice = existingProduct.getTicketPrice();

        Integer newTicketprice = tradeUpProduct.getTicketPrice();

        ProductDTO equivalentSevenDayHistoricalProduct = findSimilarProductWithDifferentPeriod(historicalProduct, SEVEN_DAYS.getDurationType(),
                        ticketStartDate);
        Long sevenD1 = equivalentSevenDayHistoricalProduct.getTicketPrice().longValue();

        ProductDTO equivalentSevenDayexistingProduct = findSimilarProductWithDifferentPeriod(existingProduct, SEVEN_DAYS.getDurationType(),
                        tradeUpDate);
        Long sevenD2 = equivalentSevenDayexistingProduct.getTicketPrice().longValue();

        ProductDTO equivalentSevenDaytradeUpProduct = findSimilarProductWithDifferentPeriod(tradeUpProduct, SEVEN_DAYS.getDurationType(), tradeUpDate);
        Long sevenD3 = equivalentSevenDaytradeUpProduct.getTicketPrice().longValue();

        Integer usedDays = calculateNumberOfUsedDays(ticketStartDate, tradeUpDate, Boolean.FALSE);
        Integer usedMonths = calculateFullMonthsBetweenDates(ticketStartDate, refundDate);
        Integer ticketFactor = calculateTicketFactor(historicalProduct.getDuration(), ticketStartDate, ticketEndDate);

        calculateRefundableMonthsWeeksAndDays(refund, ticketStartDate, refundDate);
        refund.setRefundableMonths(calculateFullMonthsBetweenDates(ticketStartDate, refundDate));

        Integer fullMonthsBetweenExchangeAndRefund = calculateFullMonthsBetweenDates(tradeUpDate, refundDate);
        double remainingDaysBetweenExchangeAndRefund = calculateNumberOfUsedDays(tradeUpDate.plusMonths(fullMonthsBetweenExchangeAndRefund),
                        refundDate, fullMonthsBetweenExchangeAndRefund > 0);

        Double totalTicketprice = historicalTicketprice + (newTicketprice - tradedInTicketprice)
                        - ((newTicketprice - tradedInTicketprice) * ((double) usedDays / ticketFactor));
        Double originalTicketCost = TFL_REFUND_MULTIPLIER * sevenD1 * (usedMonths + (((double) refund.getRefundableDays() / TFL_THIRTY_MULTIPLIER)));
        Double additionalTicketCost = TFL_REFUND_MULTIPLIER * (sevenD2 - sevenD3)
                        * (fullMonthsBetweenExchangeAndRefund + (remainingDaysBetweenExchangeAndRefund / TFL_THIRTY_MULTIPLIER));

        Double result = totalTicketprice - (originalTicketCost + additionalTicketCost);
        roundBasedOnType(refund, result, ORDINARY_AFTER_TRADE_UP);

        return refund;

    }

    protected Boolean isMonthlyTicket(DateTime ticketStartDate, DateTime ticketEndDate) {
        if(isStartAndEndMonthCheckForAvoidingOffsetDayAddition(ticketStartDate.toDate())) {
            return ticketStartDate.plusMonths(ONE_MONTH).equals(ticketEndDate);
        } else {
            return ticketStartDate.plusMonths(ONE_MONTH).minusDays(OFFSETDAY).equals(ticketEndDate);
        }
    }

    protected Boolean isNotOddPeriodTicket(DateTime ticketStartDate, DateTime ticketEndDate) {

        // test for 7 day ticket
        if (ticketStartDate.plusDays(DAYS_IN_WEEK - OFFSETDAY).equals(ticketEndDate)) {
            return Boolean.TRUE;
        }

        // test for monthly ticket
        if (isMonthlyTicket(ticketStartDate, ticketEndDate)) {
            return Boolean.TRUE;
        }
        // test for 3 month ticket
        if (ticketStartDate.plusMonths(THREE_MONTHS).minusDays(OFFSETDAY).equals(ticketEndDate)) {
            return Boolean.TRUE;
        }
        // test for 6 month ticket
        if (ticketStartDate.plusMonths(SIX_MONTHS).minusDays(OFFSETDAY).equals(ticketEndDate)) {
            return Boolean.TRUE;
        }
        // test for annual ticket
        if (ticketStartDate.plusMonths(TWELVE_MONTHS).minusDays(OFFSETDAY).equals(ticketEndDate)) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    protected Integer calculateTicketFactor(String productDuration, DateTime ticketStartDate, DateTime ticketEndDate) {
        switch (findByType(productDuration)) {
        case SEVEN_DAYS:
            return DAYS_IN_WEEK;
        case ANNUAL:
            return DAYS_IN_YEAR;
        case MONTH:
            return DAYS_IN_TEMPLATE_MONTH;
        case THREE_MONTH:
            return calculateDaysBetweenTicketStartDateAndEndDate(ticketStartDate, ticketEndDate);
        case SIX_MONTH:
            return calculateDaysBetweenTicketStartDateAndEndDate(ticketStartDate, ticketEndDate);
        case OTHER:
            return calculateDaysBetweenTicketStartDateAndEndDate(ticketStartDate, ticketEndDate);
        default:
            return null;
        }
    }

    protected UsageDuration calculateUsageDuration(DateTime ticketStartDate, DateTime refundDate) {

        DateTime dayThresholdForMonth = calculateMonthUsage(ticketStartDate);

        if (dayThresholdForMonth.isBefore(refundDate)) {
            return UsageDuration.GREATERTHANAMONTH;
        } else {
            return UsageDuration.ONEMONTHORLESS;
        }

    }

    protected DateTime calculateMonthUsage(DateTime ticketStartDate) {

        int lastDayOfMonth = (ticketStartDate.dayOfMonth().withMaximumValue()).getDayOfMonth();
        int dayOfMonth = ticketStartDate.getDayOfMonth();

        DateTime calculatedDate;
        calculatedDate = ticketStartDate.plusMonths(ONE_MONTH);
        if (dayOfMonth != lastDayOfMonth) {
            calculatedDate = calculatedDate.minusDays(OFFSETDAY);
        }
        return calculatedDate;
    }

    protected DateTime calculateMonthUsage(DateTime ticketStartDate, DateTime refundDate) {

        int lastDayOfMonth = (refundDate.dayOfMonth().withMaximumValue()).getDayOfMonth();
        int dayOfMonth = ticketStartDate.getDayOfMonth();

        DateTime calculatedDate;
        calculatedDate = ticketStartDate.plusMonths(calculateFullMonthsBetweenDates(ticketStartDate, refundDate));
        if (dayOfMonth != lastDayOfMonth) {
            calculatedDate = calculatedDate.minusDays(OFFSETDAY);
        }
        return calculatedDate;
    }

    protected Integer calculateNumberOfUsedDays(DateTime ticketStartDate, DateTime refundDate, Boolean spansMonth) {
        int days = Days.daysBetween(ticketStartDate, refundDate).getDays();
        Boolean refundDtIsBeforeStartDtCheckFlag = refundDate.isBefore(ticketStartDate);
        if ((!spansMonth) && (!refundDtIsBeforeStartDtCheckFlag)) {
            days = days + OFFSETDAY;
        }
        return days < 0 ? 0 : days;
    }

    protected Integer calculateDaysRemainderInNumberOfDays(Integer numberOfDays) {
        return numberOfDays % DAYS_IN_WEEK;
    }

    protected Integer calculateFullMonthsBetweenDates(DateTime ticketStartDate, DateTime refundDate) {
        return Months.monthsBetween(ticketStartDate, refundDate).getMonths();
    }

    protected Integer calculateFullWeeksInNumberOfDays(Integer numberOfDays) {
        return numberOfDays / DAYS_IN_WEEK;
    }

    protected Integer calculateDaysBetweenTicketStartDateAndEndDate(DateTime ticketStartDate, DateTime ticketEndDate) {
        return Days.daysBetween(ticketStartDate, ticketEndDate).getDays() + OFFSETDAY;
    }

    protected void calculateRefundableMonthsWeeksAndDays(Refund refundparams, DateTime ticketStartDate, DateTime refundDate) {
        refundparams.setRefundableMonths(calculateFullMonthsBetweenDates(ticketStartDate, refundDate));

        if (refundparams.getRefundableMonths() > 0) {
            refundparams.setRefundableDays(calculateNumberOfUsedDays(calculateMonthUsage(ticketStartDate, refundDate), refundDate,
                            refundparams.getRefundableMonths() > 0));
            refundparams.setUsedDays(refundparams.getRefundableDays());
        } else {

            Integer usedDays = calculateNumberOfUsedDays(ticketStartDate, refundDate, refundparams.getRefundableMonths() > 0);
            refundparams.setRefundableWeeks(calculateFullWeeksInNumberOfDays(usedDays));
            refundparams.setRefundableDays(calculateDaysRemainderInNumberOfDays(usedDays));
            refundparams.setUsedDays(refundparams.getRefundableDays());
            if (refundparams.getRefundableDays() > ROUND_UP_DAYS_TO_WEEK_THRESHOLD) {
                refundparams.setRefundableDays(0);
                refundparams.setRefundableWeeks(refundparams.getRefundableWeeks() + 1);
            }
        }
    }

    protected ProductDTO findSimilarProductWithDifferentPeriod(ProductDTO product, String duration, DateTime ticketStartDate) {
      //TODO Remove constants for passenger type and discount type
        return productDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(duration, product.getStartZone(), product.getEndZone(),
                        ticketStartDate.toDate(), CubicConstant.PASSENGER_TYPE_ADULT_CODE, CubicConstant.NO_DISCOUNT_TYPE_CODE, product.getType());
    }

    protected ProductDTO findSimilarProductWithDifferentPeriod(Integer startZone, Integer endZone, String duration, DateTime ticketStartDate, String type) {
      //TODO Remove constants for passenger type and discount type
        return productDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(duration, startZone, endZone, ticketStartDate.toDate(),
                        CubicConstant.PASSENGER_TYPE_ADULT_CODE, CubicConstant.NO_DISCOUNT_TYPE_CODE, type);
    }

    @Override
    public String findTravelCardTypeByDuration(DateTime ticketStartDate, DateTime ticketEndDate) {

        if (ticketStartDate.plusDays(DAYS_IN_WEEK - OFFSETDAY).equals(ticketEndDate)) {
            return SEVEN_DAYS.getDurationType();
        }
        if(findTravelCardTypeByMonthDuration(ticketStartDate, ticketEndDate)){
            return MONTH.getDurationType();
        }
        if (ticketStartDate.plusMonths(THREE_MONTHS).minusDays(OFFSETDAY).equals(ticketEndDate)) {
            return THREE_MONTH.getDurationType();
        }
        if (ticketStartDate.plusMonths(SIX_MONTHS).minusDays(OFFSETDAY).equals(ticketEndDate)) {
            return SIX_MONTH.getDurationType();
        }
        if (ticketStartDate.plusMonths(TWELVE_MONTHS).minusDays(OFFSETDAY).equals(ticketEndDate)) {
            return ANNUAL.getDurationType();
        }
        return OTHER.getDurationType();
    }
    
    private boolean findTravelCardTypeByMonthDuration(DateTime ticketStartDate, DateTime ticketEndDate) {
		if(DateUtil.isStartAndEndMonthCheckForAvoidingOffsetDayAddition(ticketStartDate.toDate())){
	    	return ticketStartDate.plusMonths(ONE_MONTH).equals(ticketEndDate);
		}else{
	    	return ticketStartDate.plusMonths(ONE_MONTH).minusDays(OFFSETDAY).equals(ticketEndDate);
		}
    }
}
