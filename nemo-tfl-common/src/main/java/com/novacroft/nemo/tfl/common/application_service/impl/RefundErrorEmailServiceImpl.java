package com.novacroft.nemo.tfl.common.application_service.impl;

import com.novacroft.nemo.tfl.common.application_service.EmailMessageService;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.transfer.EmailArgumentsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.novacroft.nemo.common.utils.CurrencyUtil.formatPenceWithoutCurrencySymbol;
import static com.novacroft.nemo.common.utils.DateUtil.formatDate;

/**
 * Email user when refund pick up window has expired
 */
@Service(value = "refundErrorEmailService")
public class RefundErrorEmailServiceImpl extends BaseRefundEmailService implements EmailMessageService {
    protected static final Logger logger = LoggerFactory.getLogger(RefundErrorEmailServiceImpl.class);

    @Override
    protected String getSubject(EmailArgumentsDTO arguments) {
        return getContent(ContentCode.REFUND_ERROR_EMAIL_SUBJECT.textCode());
    }

    @Override
    protected String getBody(EmailArgumentsDTO arguments) {
        return getContent(ContentCode.REFUND_ERROR_EMAIL_BODY.textCode(),
                formatPenceWithoutCurrencySymbol(arguments.getRefundAmountInPence()),
                arguments.getPickUpLocationName(), arguments.getCardNumber(), formatDate(arguments.getPickUpExpiresOn()));
    }
}
