package com.novacroft.nemo.tfl.common.form_validator;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.common.validator.PostcodeValidator;
import com.novacroft.nemo.tfl.common.command.impl.PaymentCardCmdImpl;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.novacroft.nemo.common.constant.CommonContentCode.INVALID_POSTCODE;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Edit payment card validator
 */
@Component("paymentCardEditValidator")
public class PaymentCardEditValidator extends BaseValidator implements Validator {

    public static final String FIELD_HOUSE_NAME_NUMBER = "paymentCardDTO.addressDTO.houseNameNumber";
    public static final String FIELD_STREET = "paymentCardDTO.addressDTO.street";
    public static final String FIELD_TOWN = "paymentCardDTO.addressDTO.town";
    public static final String FIELD_POSTCODE = "paymentCardDTO.addressDTO.postcode";
    public static final String FIELD_NICK_NAME = "paymentCardDTO.nickName";

    @Autowired
    protected PostcodeValidator postcodeValidator;
    @Autowired
    protected PaymentCardDataService paymentCardDataService;

    @Override
    public boolean supports(Class<?> targetClass) {
        return PaymentCardCmdImpl.class.equals(targetClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PaymentCardCmdImpl cmd = (PaymentCardCmdImpl) target;

        rejectIfMandatoryFieldEmpty(errors, FIELD_POSTCODE);

        rejectIfMandatoryFieldEmpty(errors, FIELD_HOUSE_NAME_NUMBER);
        rejectIfMandatoryFieldEmpty(errors, FIELD_STREET);
        rejectIfMandatoryFieldEmpty(errors, FIELD_TOWN);

        rejectIfNickNameNotUnique(errors, cmd);
        rejectIfInvalidPostcode(errors, cmd.getPostcode());
    }

    protected void rejectIfInvalidPostcode(Errors errors, String field) {
        if (!postcodeValidator.validate(field)) {
            errors.rejectValue(FIELD_POSTCODE, INVALID_POSTCODE.errorCode());
        }
    }

    protected void rejectIfNickNameNotUnique(Errors errors, PaymentCardCmdImpl cmd) {
        if (isNotBlank(cmd.getPaymentCardDTO().getNickName()) && isNotNickNameUnique(cmd)) {
            errors.rejectValue(FIELD_NICK_NAME, ContentCode.NICK_NAME_ALREADY_USED.errorCode());
        }
    }

    protected boolean isNickNameUnique(PaymentCardCmdImpl cmd) {
        return null == this.paymentCardDataService
                .findByCustomerIdIfNickNameUsedByAnotherCard(cmd.getPaymentCardDTO().getCustomerId(),
                        cmd.getPaymentCardDTO().getId(), cmd.getPaymentCardDTO().getNickName());
    }

    protected boolean isNotNickNameUnique(PaymentCardCmdImpl cmd) {
        return !isNickNameUnique(cmd);
    }
}
