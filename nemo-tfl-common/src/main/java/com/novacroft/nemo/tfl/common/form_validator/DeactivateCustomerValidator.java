package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.tfl.common.constant.ContentCode.AUTO_TOP_UP_CONFIGURED_FOR_ONE_OF_THE_CARDS;
import static com.novacroft.nemo.tfl.common.constant.CustomerConstant.OYSTER_CARD_AUTO_LOAD_STATE_TOP_UP_AMOUNT_1_CONFIGURED;
import static com.novacroft.nemo.tfl.common.constant.CustomerConstant.OYSTER_CARD_AUTO_LOAD_STATE_TOP_UP_AMOUNT_2_CONFIGURED;
import static com.novacroft.nemo.tfl.common.constant.CustomerConstant.OYSTER_CARD_AUTO_LOAD_STATE_TOP_UP_AMOUNT_3_CONFIGURED;
import static com.novacroft.nemo.tfl.common.constant.CustomerConstant.CUSTOMER_DEACTIVATION_REASON_OTHER;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.PersonalDetailsCmdImpl;
import com.novacroft.nemo.tfl.common.constant.CustomerConstant;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

/**
 * Deactivate Web Account validation
 */
@Component("deactivateCustomerValidator")
public class DeactivateCustomerValidator extends BaseValidator {
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected GetCardService getCardService;
    @Autowired
    protected CustomerDataService customerDataService;

    @Override
    public boolean supports(Class<?> targetClass) {
        return PersonalDetailsCmdImpl.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PersonalDetailsCmdImpl cmd = (PersonalDetailsCmdImpl) target;

        if (!isWebAccountDeactivated(cmd)) {
            rejectIfCustomerDeactivationReasonIsEmpty(cmd, errors);
            if (!errors.hasErrors()) {
                rejectIfCustomerDeactivationReasonOtherIsEmpty(cmd, errors);
                if (!errors.hasErrors()) {
                    rejectIfAutoTopUpConfiguredForOneOfTheCards(cmd, errors);
                }
            }
        }

    }

    protected boolean isWebAccountDeactivated(PersonalDetailsCmdImpl cmd) {
        CustomerDTO customer = customerDataService.findById(cmd.getCustomerId());
        return (customer.getDeactivated() == CustomerConstant.CUSTOMER_DEACTIVATED);
    }

    protected void rejectIfCustomerDeactivationReasonIsEmpty(PersonalDetailsCmdImpl cmd, Errors errors) {
        if (cmd.getCustomerDeactivated()) {
            rejectIfMandatoryFieldEmpty(errors, "customerDeactivationReason");
        }
    }

    protected void rejectIfCustomerDeactivationReasonOtherIsEmpty(PersonalDetailsCmdImpl cmd, Errors errors) {
        if (cmd.getCustomerDeactivated() && isCustomerDeactivationReasonOther(cmd.getCustomerDeactivationReason())) {
            rejectIfMandatoryFieldEmpty(errors, "customerDeactivationReasonOther");
        }
    }

    protected boolean isCustomerDeactivationReasonOther(String customerDeactivationReason) {
        return (customerDeactivationReason != null && customerDeactivationReason.equalsIgnoreCase(CUSTOMER_DEACTIVATION_REASON_OTHER)) ? true : false;
    }

    protected void rejectIfAutoTopUpConfiguredForOneOfTheCards(PersonalDetailsCmdImpl cmd, Errors errors) {
        List<CardDTO> cards = cardDataService.findByCustomerId(cmd.getCustomerId());

        boolean autoTopUpConfiguredForOneOfTheCards = false;

        for (CardDTO card : cards) {
            if (card.getCardNumber() != null && isAutoTopUpConfiguredForCard(card.getCardNumber())) {
                autoTopUpConfiguredForOneOfTheCards = true;
            }
        }

        if (autoTopUpConfiguredForOneOfTheCards && cmd.getCustomerDeactivated()) {
            errors.reject(AUTO_TOP_UP_CONFIGURED_FOR_ONE_OF_THE_CARDS.errorCode());
            cmd.setCustomerDeactivated(false);
        }
    }

    protected boolean isAutoTopUpConfiguredForCard(String cardNumber) {
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardService.getCard(cardNumber);
        assert (cardInfoResponseV2DTO != null);
        return isAutoTopUpConfiguredForCardInforResponseV2DTO(cardInfoResponseV2DTO.getAutoLoadState());
    }

    protected boolean isAutoTopUpConfiguredForCardInforResponseV2DTO(Integer autoLoadState) {
        if (autoLoadState != null
                        && (autoLoadState.intValue() == OYSTER_CARD_AUTO_LOAD_STATE_TOP_UP_AMOUNT_1_CONFIGURED
                                        || autoLoadState.intValue() == OYSTER_CARD_AUTO_LOAD_STATE_TOP_UP_AMOUNT_2_CONFIGURED || autoLoadState
                                        .intValue() == OYSTER_CARD_AUTO_LOAD_STATE_TOP_UP_AMOUNT_3_CONFIGURED)) {
            return true;
        }
        return false;
    }
}
