package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.tfl.common.constant.ContentCode.ALREADY_USED;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.HOTLISTED;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.INVALID;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.NOT_AVAILABLE;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_CARD_NUMBER;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.command.OysterCardCmd;
import com.novacroft.nemo.common.utils.OysterCardNumberUtil;
import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.application_service.HotlistService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.constant.SystemParameterCode;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

/**
 * OysterCard validation
 */
@Component("oysterCardValidator")
public class OysterCardValidator extends BaseValidator {
    protected static final Logger logger = LoggerFactory.getLogger(OysterCardValidator.class);
    
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected GetCardService getCardService;
    @Autowired
    protected SystemParameterService systemParameterService;
    @Autowired
    protected HotlistService hotlistService;

    @Override
    public boolean supports(Class<?> targetClass) {
        return OysterCardCmd.class.equals(targetClass);
    }
    
    protected Integer systemParameterOysterCardFullLength;
    protected Integer systemParameterOysterCardChecksumLength;
    
    @PostConstruct
    public void initialize() {
        systemParameterOysterCardFullLength = systemParameterService.getIntegerParameterValue(SystemParameterCode.OYSTER_CARD_FULL_LENGTH.code());
        systemParameterOysterCardChecksumLength = systemParameterService.getIntegerParameterValue(SystemParameterCode.OYSTER_CARD_CHECKSUM_LENGTH.code());
    }

    @Override
    public void validate(Object target, Errors errors) {
        OysterCardCmd cmd = (OysterCardCmd) target;
        String cardNumber = cmd.getCardNumber();
        rejectIfMandatoryFieldEmpty(errors, FIELD_CARD_NUMBER);
        
        if (hasCardNumberNoFieldError(errors)) {            
            rejectIfNotNumeric(errors, FIELD_CARD_NUMBER);
            if (hasCardNumberNoFieldError(errors) && isCardNumberNotEqualsToTwelveDigits(cardNumber)) {
                errors.rejectValue(FIELD_CARD_NUMBER, INVALID.errorCode());
            } else if (hasCardNumberNoFieldError(errors)) {
                checkCardCheckDigitAndAvailableOnCubicAndAlreadyAssociated(cardNumber, errors);
            } 
        }
    }
    
    protected boolean hasCardNumberNoFieldError(Errors errors) {
        return !errors.hasFieldErrors(FIELD_CARD_NUMBER);
    }
    
    protected boolean isCardNumberNotEqualsToTwelveDigits(String cardNumber) {
        return cardNumber.length() != systemParameterOysterCardFullLength;
    }
    
    protected void checkCardCheckDigitAndAvailableOnCubicAndAlreadyAssociated(String cardNumber, Errors errors) {
        if (!verifyCardCheckDigit(cardNumber)) {
            errors.rejectValue(FIELD_CARD_NUMBER, INVALID.errorCode());
        }
        
        if (hasCardNumberNoFieldError(errors) && !checkCardAvailableOnCubic(cardNumber)) {
            errors.rejectValue(FIELD_CARD_NUMBER, NOT_AVAILABLE.errorCode());
        }
        
        if (hasCardNumberNoFieldError(errors)) {
            checkCardNotAlreadyAssociated(errors, cardNumber);
        }
        
        if (hasCardNumberNoFieldError(errors) && isCardHotListedInCubic(cardNumber)) {
            errors.rejectValue(FIELD_CARD_NUMBER, HOTLISTED.errorCode());
        }
    }
    
    protected boolean verifyCardCheckDigit(String cardNumber) {
        String expectedCheckDigits = OysterCardNumberUtil.calculateCheckDigits(cardNumber.substring(0, cardNumber.length() - systemParameterOysterCardChecksumLength));
        String actualCheckDigits = cardNumber.substring(cardNumber.length() - systemParameterOysterCardChecksumLength);
    
        return expectedCheckDigits.equalsIgnoreCase(actualCheckDigits);
    }
    
    protected boolean checkCardAvailableOnCubic(String cardNumber) {
        try {
            CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardService.getCard(cardNumber);
            return cardInfoResponseV2DTO != null;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }
    
    protected boolean isCardHotListedInCubic(String cardNumber) {
    	return hotlistService.isCardHotListedInCubic(cardNumber);
    }
    
    protected void checkCardNotAlreadyAssociated(Errors errors, String cardNumber) {
        CardDTO cardDTO = cardDataService.findByCardNumber(cardNumber);
        if (cardDTO != null) {
            errors.rejectValue(FIELD_CARD_NUMBER, ALREADY_USED.errorCode());
        }
    }
    
}
