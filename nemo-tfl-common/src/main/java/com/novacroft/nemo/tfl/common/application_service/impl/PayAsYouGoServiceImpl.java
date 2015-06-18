package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.utils.DateUtil.addDaysToDate;
import static com.novacroft.nemo.common.utils.DateUtil.formatDate;
import static com.novacroft.nemo.common.utils.DateUtil.getBusinessDay;
import static com.novacroft.nemo.common.utils.DateUtil.getDateDiffInMonths;
import static com.novacroft.nemo.common.utils.DateUtil.getDaysDiffExcludingMonths;
import static com.novacroft.nemo.common.utils.DateUtil.getMidnightDay;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.JOB_CENTRE_PLUS_DISCOUNT_MAXIMUM_ALLOWED_DAYS;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.JOB_CENTRE_PLUS_DISCOUNT_MAXIMUM_ALLOWED_MONTHS;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.PAY_AS_YOU_GO_COLLECTION_DAYS;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.PRODUCT_START_AFTER_DAYS;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.REMINDER_DATE_NONE;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.USER_PRODUCT_START_AFTER_DAYS;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.transfer.JobCentrePlusDiscountDTO;
import com.novacroft.nemo.tfl.common.application_service.AutoTopUpConfigurationService;
import com.novacroft.nemo.tfl.common.application_service.CardUpdateService;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.PayAsYouGoService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.constant.SettlementStatus;
import com.novacroft.nemo.tfl.common.data_service.AdHocLoadSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.JobCentrePlusInvestigationDataService;
import com.novacroft.nemo.tfl.common.transfer.AdHocLoadSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.JobCentrePlusInvestigationDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;
import com.novacroft.nemo.tfl.common.util.CartUtil;

@Service("payAsYouGoService")
public class PayAsYouGoServiceImpl implements PayAsYouGoService {
    static final Logger logger = LoggerFactory.getLogger(PayAsYouGoServiceImpl.class);

    @Autowired
    protected CartService cartService;
    @Autowired
    protected AutoTopUpConfigurationService autoTopUpConfigurationService;
    @Autowired
    protected SystemParameterService systemParameterService;
    @Autowired
    protected CartAdministrationService cartAdminService;
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected GetCardService getCardService;
    @Autowired
    protected JobCentrePlusInvestigationDataService jobCentrePlusInvestigationDataService;
    @Autowired
    protected CardUpdateService cardUpdateService;
    @Autowired
    protected AdHocLoadSettlementDataService adHocLoadSettlementDataService;

    @Override
    @Transactional
    public CartDTO addCartItemForExistingCard(CartDTO cartDTO, CartItemCmdImpl cartItemCmd) {
        assert (cartDTO != null);
        assert (cartItemCmd != null);

        cartItemCmd.setCartId(cartDTO.getId());
        checkJobCentrePlusDiscountForAllowedMonths(cartItemCmd);
        cartItemCmd = setCartItemForExistingCardDefaults(cartItemCmd);
        cartDTO = cartService.addUpdateItem(cartDTO, cartItemCmd, PayAsYouGoItemDTO.class);
        if (cartItemCmd.getAutoTopUpAmt() > 0) {
            cartDTO = addAutoTopUpCartItem(cartDTO, cartItemCmd);
        } else {
            cartDTO = removeAutoTopUpCartItem(cartDTO);
        }
        return cartDTO;
    }

    @Override
    @Transactional
    public CartDTO addCartItemForNewCard(CartDTO cartDTO, CartItemCmdImpl cartItemCmd) {
        assert (cartDTO != null);
        assert (cartItemCmd != null);

        cartItemCmd = setCartItemForNewCardDefaults(cartItemCmd);
        cartItemCmd.setCardId(cartDTO.getCardId());
        cartDTO = cartService.addUpdateItem(cartDTO, cartItemCmd, PayAsYouGoItemDTO.class);
        cartDTO = cartAdminService.applyRefundableDeposit(cartDTO, cartItemCmd);
        cartDTO = cartAdminService.applyShippingCost(cartDTO, cartItemCmd);
        if (cartItemCmd.getAutoTopUpAmt() > 0) {
            cartDTO = addAutoTopUpCartItem(cartDTO, cartItemCmd);
        } else {
            cartDTO = removeAutoTopUpCartItem(cartDTO);
        }

        return cartDTO;
    }

    @Override
    public CartDTO addAutoTopUpCartItem(CartDTO cartDTO, CartItemCmdImpl cartItemCmd) {
        cartDTO = cartService.addUpdateItem(cartDTO, cartItemCmd, AutoTopUpConfigurationItemDTO.class);
        return cartDTO;
    }

    @Override
    public CartDTO removeNonApplicableAutoTopUpCartItem(CartDTO cartDTO) {
        ItemDTO itemDTO = getPayAsYouGoFromCart(cartDTO);
        if (null == itemDTO) {
            cartDTO = removeAutoTopUpCartItem(cartDTO);
        }
        return cartDTO;
    }

    protected CartDTO removeAutoTopUpCartItem(CartDTO cartDTO) {
        ItemDTO itemDTO = getAutoTopUpFromCart(cartDTO);
        if (null != itemDTO) {
            cartDTO = cartService.deleteItem(cartDTO, itemDTO.getId());
        }
        return cartDTO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean updatePrePayValueToCubic(CartCmdImpl cmd) {
        assert (cmd != null);
        assert (cmd.getCartDTO() != null);
        try {
            Integer cubicRequestNumber = updatePrePayValueToCubic(cmd.getCartDTO(), cmd.getStationId());
            if (cubicRequestNumber != null) {
                persistRequestedAdHocLoadSettlementRecord(cmd, cubicRequestNumber);
            }
        } catch (Exception cubicAddPPTException) {
            logger.error(PrivateError.UNEXPECTED.message(), cubicAddPPTException);
            persistFailedAdHocLoadSettlementRecord(cmd);
            return true;
        }
        return false;
    }

    private Integer updatePrePayValueToCubic(CartDTO cartDTO, Long stationId) {
        assert (stationId != null);
        PayAsYouGoItemDTO payAsYouGoItem = cartDTO.getPayAsYouGoItem();
        Integer cubicRequestNumber = null;
        if (null != payAsYouGoItem) {
            CardDTO cardDTO = cardDataService.findById(cartDTO.getCardId());
            if (cardDTO != null && cardDTO.getCardNumber() != null) {
                CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardService.getCard(cardDTO.getCardNumber());
                if (hasPrePayValueDetailsGotBalanceAndCurrency(cardInfoResponseV2DTO)) {
                    cubicRequestNumber = cardUpdateService.requestCardUpdatePrePayValue(cardDTO.getCardNumber(), stationId,
                                    payAsYouGoItem.getPrice(), cardInfoResponseV2DTO.getPpvDetails().getCurrency(), cartDTO.getCartType());
                }
            }
        }
        return cubicRequestNumber;
    }

    protected Boolean hasPrePayValueDetailsGotBalanceAndCurrency(CardInfoResponseV2DTO cardInfoResponseV2DTO) {
        return cardInfoResponseV2DTO != null && cardInfoResponseV2DTO.getPpvDetails().getBalance() != null
                        && cardInfoResponseV2DTO.getPpvDetails().getCurrency() != null;
    }

    protected void persistRequestedAdHocLoadSettlementRecord(CartCmdImpl cmd, Integer cubicRequestNumber) {
        CartDTO cartDTO = cmd.getCartDTO();
        AdHocLoadSettlementDTO settlementDTO = getExistingAdhocLoadSettlement(cubicRequestNumber, cartDTO);
        if (settlementDTO == null) {
            settlementDTO = new AdHocLoadSettlementDTO();
        }
        updateAdhocLoadSettlementDTO(settlementDTO, cartDTO, cmd.getStationId());
        settlementDTO.setStatus(SettlementStatus.REQUESTED.code());
        settlementDTO.setRequestSequenceNumber(cubicRequestNumber);
        adHocLoadSettlementDataService.createOrUpdate(settlementDTO);
    }

    protected void persistFailedAdHocLoadSettlementRecord(CartCmdImpl cmd) {
        CartDTO cartDTO = cmd.getCartDTO();
        AdHocLoadSettlementDTO settlementDTO = new AdHocLoadSettlementDTO();
        updateAdhocLoadSettlementDTO(settlementDTO, cartDTO, cmd.getStationId());
        settlementDTO.setStatus(SettlementStatus.FAILED.code());
        adHocLoadSettlementDataService.createOrUpdate(settlementDTO);
    }

    protected AdHocLoadSettlementDTO getExistingAdhocLoadSettlement(Integer cubicRequestNumber, CartDTO cartDTO) {
        return adHocLoadSettlementDataService.findByRequestSequenceNumberAndCardNumber(cubicRequestNumber,
                        cardDataService.findById(cartDTO.getCardId()).getCardNumber());
    }

    protected void updateAdhocLoadSettlementDTO(AdHocLoadSettlementDTO settlementDTO, CartDTO cartDTO, Long stationId) {
        OrderDTO orderDTO = cartDTO.getOrder();
        settlementDTO.setOrderId(orderDTO != null ? orderDTO.getId() : null);
        if (null != cartDTO.getPaymentCardSettlement()) {
            settlementDTO.setSettlementDate(cartDTO.getPaymentCardSettlement().getSettlementDate());
        } else {
            settlementDTO.setSettlementDate(new Date());
        }
        PayAsYouGoItemDTO payAsYouGoItemDTO = CartUtil.getPayAsYouGoItem(cartDTO.getOrder().getOrderItems());
        settlementDTO.setItem(payAsYouGoItemDTO);

        settlementDTO.setAmount(payAsYouGoItemDTO.getPrice());
        settlementDTO.setCardId(payAsYouGoItemDTO.getCardId());
        settlementDTO.setExpiresOn(payAsYouGoItemDTO.getEndDate());
        settlementDTO.setPickUpNationalLocationCode(stationId.intValue());
    }

    @Override
    public void updateSettledAutoTopUpCartItem(CartDTO cartDTO, Long orderId, Long stationId, AutoTopUpConfigurationItemDTO existingItemDTO) {
        Integer autoTopup = existingItemDTO.getAutoTopUpAmount();
        if (isExistingOysterCard(stationId) && autoTopup != 0) {
            this.autoTopUpConfigurationService.changeConfiguration(cartDTO.getCardId(), orderId, autoTopup, stationId);
        }
    }

    protected CartItemCmdImpl setCartItemForNewCardDefaults(CartItemCmdImpl cartItemCmd) {
        assert (cartItemCmd != null);
        cartItemCmd.setStartDate(formatDate(addDaysToDate(new Date(), systemParameterService.getIntegerParameterValue(PRODUCT_START_AFTER_DAYS))));
        cartItemCmd.setEndDate(null);
        cartItemCmd.setEmailReminder(REMINDER_DATE_NONE);
        return cartItemCmd;
    }

    protected CartItemCmdImpl setCartItemForExistingCardDefaults(CartItemCmdImpl cartItemCmd) {
        cartItemCmd.setStartDate(formatDate(getBusinessDay(new Date(), systemParameterService.getIntegerParameterValue(USER_PRODUCT_START_AFTER_DAYS))));
        cartItemCmd.setEndDate(formatDate(getBusinessDay(new Date(), systemParameterService.getIntegerParameterValue(USER_PRODUCT_START_AFTER_DAYS)
                        + systemParameterService.getIntegerParameterValue(PAY_AS_YOU_GO_COLLECTION_DAYS))));
        cartItemCmd.setEmailReminder(REMINDER_DATE_NONE);
        return cartItemCmd;
    }

    protected AutoTopUpConfigurationItemDTO getAutoTopUpFromCart(CartDTO cartDTO) {
        return cartDTO.getAutoTopUpItem();
    }

    protected PayAsYouGoItemDTO getPayAsYouGoFromCart(CartDTO cartDTO) {
        return cartDTO.getPayAsYouGoItem();
    }

    protected boolean isExistingOysterCard(Long stationId) {
        return stationId != null;
    }

    protected void checkJobCentrePlusDiscountForAllowedMonths(CartItemCmdImpl cmd) {
        JobCentrePlusDiscountDTO jobCenterPlusDiscount = getJobCentrePlusDiscountDetails(cmd.getCardId());
        if (isJobCenterPlusDiscountAvailable(jobCenterPlusDiscount)
                        && isJobCentrePlusDiscountDurationGreaterThanMaximumAllowedMonths(jobCenterPlusDiscount.getDiscountExpiryDate())) {
            addJobCentrePlusInvestigationRecord(jobCenterPlusDiscount, cmd.getCardId());
        }
    }

    protected JobCentrePlusDiscountDTO getJobCentrePlusDiscountDetails(Long cardId) {
        String cardNumber = cardDataService.findById(cardId).getCardNumber();
        return getCardService.getJobCentrePlusDiscountDetails(cardNumber);
    }

    protected boolean isJobCenterPlusDiscountAvailable(JobCentrePlusDiscountDTO jobCenterPlusDiscount) {
        return jobCenterPlusDiscount != null && jobCenterPlusDiscount.getJobCentrePlusDiscountAvailable();
    }

    protected boolean isJobCentrePlusDiscountDurationGreaterThanMaximumAllowedMonths(Date jobCentrePlusDiscountExpiryDate) {
        Date todayDate = getMidnightDay(new Date());
        int diffMonths = getDateDiffInMonths(todayDate, jobCentrePlusDiscountExpiryDate);
        int diffDaysExcludingMonths = getDaysDiffExcludingMonths(todayDate, jobCentrePlusDiscountExpiryDate);
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

    protected void addJobCentrePlusInvestigationRecord(JobCentrePlusDiscountDTO jobCenterPlusDiscount, Long cardId) {
        JobCentrePlusInvestigationDTO jobCentrePlusInvestigationDTO = new JobCentrePlusInvestigationDTO(cardId, jobCenterPlusDiscount.getPrestigeId()
                        .toString(), new Date(), jobCenterPlusDiscount.getDiscountExpiryDate());
        jobCentrePlusInvestigationDataService.createOrUpdate(jobCentrePlusInvestigationDTO);
    }
}
