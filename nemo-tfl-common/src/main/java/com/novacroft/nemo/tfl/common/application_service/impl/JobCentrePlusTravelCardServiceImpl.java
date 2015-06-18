package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.utils.DateUtil.getDateDiffInMonths;
import static com.novacroft.nemo.common.utils.DateUtil.getDaysDiffExcludingMonths;
import static com.novacroft.nemo.common.utils.DateUtil.getMidnightDay;
import static com.novacroft.nemo.common.utils.DateUtil.isBeforeOrEqual;
import static com.novacroft.nemo.common.utils.DateUtil.parse;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.JOB_CENTRE_PLUS_DISCOUNT_MAXIMUM_ALLOWED_DAYS;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.JOB_CENTRE_PLUS_DISCOUNT_MAXIMUM_ALLOWED_MONTHS;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.JOB_CENTRE_PLUS_DISCOUNT_RATE;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.TRAVEL_CARD_END_DATE_EXCEEDS_JOB_CENTRE_PLUS_EXPIRY_DATE;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.TRAVEL_CARD_LENGTH_GREATER_THAN_THREE_MONTHS_FOR_JOB_CENTRE_PLUS;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.transfer.JobCentrePlusDiscountDTO;
import com.novacroft.nemo.tfl.common.application_service.JobCentrePlusTravelCardService;
import com.novacroft.nemo.tfl.common.application_service.RefundCalculationBasisService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.JobCentrePlusInvestigationDataService;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;
import com.novacroft.nemo.tfl.common.data_service.ZoneIdDescriptionDataService;
import com.novacroft.nemo.tfl.common.transfer.JobCentrePlusInvestigationDTO;

@Service("jobCentrePlusTravelCardService")
public class JobCentrePlusTravelCardServiceImpl implements JobCentrePlusTravelCardService {
    static final Logger logger = LoggerFactory.getLogger(JobCentrePlusTravelCardServiceImpl.class);

    @Autowired
    protected CardDataService cardDataService;

    @Autowired
    protected SystemParameterService systemParameterService;

    @Autowired
    protected ProductDataService productDataService;

    @Autowired
    protected RefundCalculationBasisService refundCalculationBasisService;

    @Autowired
    protected ZoneIdDescriptionDataService zoneIdDescriptionDataService;

    @Autowired
    protected GetCardService getCardService;

    @Autowired
    protected JobCentrePlusInvestigationDataService jobCentrePlusInvestigationDataService;

    @Override
    public Integer checkJobCentrePlusDiscountAndUpdateTicketPrice(CartItemCmdImpl cmd, Integer ticketPrice) {
        double price = ticketPrice != null ? ticketPrice : 0;
        if (ticketPrice != null && cmd != null && cmd.getCardId() != null) {
            cmd.setStatusMessage(getStatusMessageBasedOnJobCentrePlusDiscountAvailable(cmd.getCardId(), cmd.getStartDate(), cmd.getEndDate(),
                            cmd.getStatusMessage()));
            addJobCentrePlusInvestigationRecord(cmd);
            price = getJobCentrePlusDiscountPrice(cmd, price);
        }
        return (int) price;
    }

    protected String getStatusMessageBasedOnJobCentrePlusDiscountAvailable(Long cardId, String startDate, String endDate, String statusMessage) {
        if (isJobCentrePlusDiscountAvailable(cardId, startDate, endDate)) {
            JobCentrePlusDiscountDTO jobCenterPlusDiscount = getCardService.getJobCentrePlusDiscountDetails(cardId);
            if (!isTravelCardEndDateLessThanDiscountExpiryDate(endDate, jobCenterPlusDiscount.getDiscountExpiryDate())) {
                return TRAVEL_CARD_END_DATE_EXCEEDS_JOB_CENTRE_PLUS_EXPIRY_DATE.textCode();
            }
            return TRAVEL_CARD_LENGTH_GREATER_THAN_THREE_MONTHS_FOR_JOB_CENTRE_PLUS.textCode();
        }
        return statusMessage;
    }

    protected boolean isJobCentrePlusDiscountAvailable(Long cardId, String startDate, String endDate) {
        return isJobCenterPlusDiscountAvailableForCardId(cardId)
                        && !isTravelCardLengthGreaterThanJobCentrePlusMaximumAllowedMonths(startDate, endDate);
    }

    protected void addJobCentrePlusInvestigationRecord(CartItemCmdImpl cmd) {
        if (isAddingJobCentrePlusInvestigationRecordRequired(cmd)) {
            JobCentrePlusDiscountDTO jobCenterPlusDiscount = getCardService.getJobCentrePlusDiscountDetails(cmd.getCardId());
            addJobCentrePlusInvestigationRecord(jobCenterPlusDiscount, cmd.getCardId());
        }
    }

    protected boolean isAddingJobCentrePlusInvestigationRecordRequired(CartItemCmdImpl cmd) {
        JobCentrePlusDiscountDTO jobCenterPlusDiscount = getCardService.getJobCentrePlusDiscountDetails(cmd.getCardId());
        if (isJobCentrePlusDiscountAvailable(cmd.getCardId(), cmd.getStartDate(), cmd.getEndDate())
                        && isTravelCardEndDateLessThanDiscountExpiryDate(cmd.getEndDate(), jobCenterPlusDiscount.getDiscountExpiryDate())
                        && isJobCentrePlusDiscountDurationGreaterThanMaximumAllowedMonths(jobCenterPlusDiscount.getDiscountExpiryDate())) {
            return true;
        }
        return false;
    }

    protected double getJobCentrePlusDiscountPrice(CartItemCmdImpl cmd, double price) {
        if (isJobCentrePlusDiscountAvailable(cmd.getCardId(), cmd.getStartDate(), cmd.getEndDate())) {
            return getTicketPriceWithJobCentrePlusDiscountRate(cmd.getEndDate(), price, cmd.getCardId());
        }
        return price;
    }

    protected double getTicketPriceWithJobCentrePlusDiscountRate(String endDate, double ticketPrice, Long cardId) {
        double price = ticketPrice;
        JobCentrePlusDiscountDTO jobCenterPlusDiscount = getCardService.getJobCentrePlusDiscountDetails(cardId);
        if (isTravelCardEndDateLessThanDiscountExpiryDate(endDate, jobCenterPlusDiscount.getDiscountExpiryDate())
                        && !isJobCentrePlusDiscountDurationGreaterThanMaximumAllowedMonths(jobCenterPlusDiscount.getDiscountExpiryDate())) {
            price = ticketPrice - (ticketPrice * systemParameterService.getFloatParameterValue(JOB_CENTRE_PLUS_DISCOUNT_RATE));
        }
        return price;
    }

    protected boolean isTravelCardEndDateLessThanDiscountExpiryDate(String endDate, Date discountExpiryDate) {
        return isBeforeOrEqual(parse(endDate), discountExpiryDate);
    }

    protected boolean isJobCentrePlusDiscountDurationGreaterThanMaximumAllowedMonths(Date jobCentrePlusDiscountExpiryDate) {
        Date todayDate = getMidnightDay(new Date());
        int diffMonths = getDateDiffInMonths(todayDate, jobCentrePlusDiscountExpiryDate);
        int diffDaysExcludingMonths = getDaysDiffExcludingMonths(todayDate, jobCentrePlusDiscountExpiryDate);
        return isDateDifferenceSatisfyingMaximumAllowedMonthsAndDays(diffMonths, diffDaysExcludingMonths,
                        systemParameterService.getIntegerParameterValue(JOB_CENTRE_PLUS_DISCOUNT_MAXIMUM_ALLOWED_MONTHS),
                        systemParameterService.getIntegerParameterValue(JOB_CENTRE_PLUS_DISCOUNT_MAXIMUM_ALLOWED_DAYS));
    }

    protected void addJobCentrePlusInvestigationRecord(JobCentrePlusDiscountDTO jobCenterPlusDiscount, Long cardId) {
        JobCentrePlusInvestigationDTO jobCentrePlusInvestigationDTO = new JobCentrePlusInvestigationDTO(cardId, jobCenterPlusDiscount.getPrestigeId()
                        .toString(), new Date(), jobCenterPlusDiscount.getDiscountExpiryDate());
        jobCentrePlusInvestigationDataService.createOrUpdate(jobCentrePlusInvestigationDTO);
    }

    protected boolean isJobCenterPlusDiscountAvailableForCardId(Long cardId) {
        JobCentrePlusDiscountDTO jobCenterPlusDiscount = getCardService.getJobCentrePlusDiscountDetails(cardId);
        return jobCenterPlusDiscount != null && jobCenterPlusDiscount.getJobCentrePlusDiscountAvailable();
    }

    protected boolean isTravelCardLengthGreaterThanJobCentrePlusMaximumAllowedMonths(String startDate, String endDate) {
        int diffMonths = getDateDiffInMonths(startDate, endDate);
        int diffDaysExcludingMonths = getDaysDiffExcludingMonths(startDate, endDate);
        return isDateDifferenceSatisfyingMaximumAllowedMonthsAndDays(diffMonths, diffDaysExcludingMonths,
                        systemParameterService.getIntegerParameterValue(JOB_CENTRE_PLUS_DISCOUNT_MAXIMUM_ALLOWED_MONTHS),
                        systemParameterService.getIntegerParameterValue(JOB_CENTRE_PLUS_DISCOUNT_MAXIMUM_ALLOWED_DAYS));
    }

    protected boolean isDateDifferenceSatisfyingMaximumAllowedMonthsAndDays(int diffMonths, int diffDaysExcludingMonths, int maximumAllowedMonths,
                    int maximumAllowedDays) {
        if (diffMonths > maximumAllowedMonths || (diffMonths == maximumAllowedMonths && diffDaysExcludingMonths > maximumAllowedDays)) {
            return true;
        }
        return false;
    }
}
