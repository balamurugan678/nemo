package com.novacroft.nemo.tfl.common.form_validator;

import static com.novacroft.nemo.tfl.common.constant.CartAttribute.MINIMUM_AUTO_TOP_UP_AMT;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.INVALID_AMOUNT;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.PAY_AS_YOU_GO_CREDIT_GREATER_THAN_LIMIT;
import static com.novacroft.nemo.tfl.common.constant.ContentCode.PAY_AS_YOU_GO_CREDIT_GREATER_THAN_PURCHASE_LIMIT;
import static com.novacroft.nemo.tfl.common.constant.PageCommandAttribute.FIELD_CREDIT_BALANCE;
import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.PAY_AS_YOU_GO_LIMIT;
import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.PAY_AS_YOU_GO_AD_HOC_PURCHASE_LIMIT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.domain.cubic.PrePayValue;
import com.novacroft.nemo.common.utils.CurrencyUtil;
import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.PayAsYouGoCmd;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

/**
 * Pay as you go validation
 */
@Component("payAsYouGoValidator")
public class PayAsYouGoValidator extends BaseValidator {
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected GetCardService getCardService;
    @Autowired
    protected SystemParameterService systemParameterService;

    @Override
    public boolean supports(Class<?> targetClass) {
        return PayAsYouGoCmd.class.isAssignableFrom(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PayAsYouGoCmd cmd = (PayAsYouGoCmd) target;
        rejectIfMandatoryFieldEmpty(errors, FIELD_CREDIT_BALANCE);
        rejectIfCreditBalaneIsGreaterThanPurchaseLimit(errors, FIELD_CREDIT_BALANCE, cmd.getCreditBalance());
        rejectIfCreditBalanceIsLessThanMinimumAutoTopUpAmount(errors, FIELD_CREDIT_BALANCE, cmd.getAutoTopUpAmt(), cmd.getCreditBalance());
        rejectIfPayAsYouGoBalanceIsGreaterThanLimitInSystem(errors, FIELD_CREDIT_BALANCE, cmd.getCardId(), cmd.getCreditBalance(),
                        cmd.getExistingCreditBalance());
    }
    
    protected void rejectIfCreditBalaneIsGreaterThanPurchaseLimit(Errors errors, String field, Integer creditBalance){
        int purchaseLimit = systemParameterService.getIntegerParameterValue(PAY_AS_YOU_GO_AD_HOC_PURCHASE_LIMIT.code());
        if(hasNoFieldError(errors, field) && creditBalance > purchaseLimit){
            errors.rejectValue(field, PAY_AS_YOU_GO_CREDIT_GREATER_THAN_PURCHASE_LIMIT.errorCode(), new String[]{CurrencyUtil.formatPenceWithoutCurrencySymbol(purchaseLimit)}, null);
        }
    }

    protected void rejectIfCreditBalanceIsLessThanMinimumAutoTopUpAmount(Errors errors, String field, Integer autoTopUpAmt, Integer creditBalance) {
        int minimumAutoTopUpAmt = systemParameterService.getIntegerParameterValue(MINIMUM_AUTO_TOP_UP_AMT);
        if (hasNoFieldError(errors, field) && autoTopUpAmt > 0 && creditBalance < minimumAutoTopUpAmt) {
            errors.rejectValue(field, INVALID_AMOUNT.errorCode());
        }
    }

    protected void rejectIfPayAsYouGoBalanceIsGreaterThanLimitInSystem(Errors errors, String field, Long cardId, Integer creditBalance,
                    Integer existingCreditBalance) {
        if (hasNoFieldError(errors, field) && cardId != null) {
            CardDTO cardDTO = cardDataService.findById(cardId);
            // For order card functionality oyster card number is null
            if (isOysterCardIssued(cardDTO)) {
                
                int limit = systemParameterService.getIntegerParameterValue(PAY_AS_YOU_GO_LIMIT.code());
                Integer payAsYouGoBalance = creditBalance + existingCreditBalance + sumExisitingBalanceAndPendingValues(cardDTO.getCardNumber());
                
                if (payAsYouGoBalance > limit) {
                    errors.rejectValue(field, PAY_AS_YOU_GO_CREDIT_GREATER_THAN_LIMIT.errorCode(),
                            new String[]{CurrencyUtil.formatPenceWithoutCurrencySymbol(limit)}, null);
                }
            }
        }
    }
    
    protected Integer sumExisitingBalanceAndPendingValues(String cardNumber){
        Integer payAsYouGoBalance = 0;
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardService.getCard(cardNumber);
        if (cardInfoResponseV2DTO != null) {
            if(cardInfoResponseV2DTO.getPpvDetails() != null && cardInfoResponseV2DTO.getPpvDetails().getBalance() != null){
                payAsYouGoBalance += cardInfoResponseV2DTO.getPpvDetails().getBalance();
            }
            if(cardInfoResponseV2DTO.getPendingItems()!= null && cardInfoResponseV2DTO.getPendingItems().getPpvs() != null && cardInfoResponseV2DTO.getPendingItems().getPpvs().size() >0){
                for(PrePayValue prePayValue: cardInfoResponseV2DTO.getPendingItems().getPpvs()){
                    if(prePayValue != null){
                        payAsYouGoBalance += prePayValue.getPrePayValue();
                    }
                }
            }
            
        }
        return payAsYouGoBalance;
    }

    protected boolean isOysterCardIssued(CardDTO cardDTO) {
        return cardDTO.getCardNumber() != null;
    }

}
