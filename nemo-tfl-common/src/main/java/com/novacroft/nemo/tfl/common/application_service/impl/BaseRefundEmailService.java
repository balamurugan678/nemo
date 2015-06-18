package com.novacroft.nemo.tfl.common.application_service.impl;

import com.novacroft.nemo.common.application_service.impl.BaseEmailService;
import com.novacroft.nemo.tfl.common.transfer.EmailArgumentsDTO;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

import static com.novacroft.nemo.common.constant.CommonFreemarkerTemplate.STANDARD_EMAIL_BODY;
import static com.novacroft.nemo.common.constant.EmailConstant.*;
import static com.novacroft.nemo.common.utils.UriUrlUtil.addPathToUriAsUrlString;

/**
 * Base service for emails that are related to refunds (ie include refund amount, pick up location, card number and pick up
 * expiry date)
 */
public abstract class BaseRefundEmailService extends BaseEmailService {

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void sendMessage(EmailArgumentsDTO arguments) {
        Map model = prepareModel(arguments);
        MimeMessage message = prepareMessage(model, STANDARD_EMAIL_BODY);
        this.emailService.sendMessage(message);
    }

    protected Map<String, String> prepareModel(EmailArgumentsDTO arguments) {
        Map<String, String> model = new HashMap<String, String>();
        model.put(TO_ADDRESS_MODEL_ATTRIBUTE, arguments.getToAddress());
        model.put(SUBJECT_MODEL_ATTRIBUTE, getSubject(arguments));
        model.put(SALUTATION_MODEL_ATTRIBUTE, arguments.getSalutation());
        model.put(BASE_IMAGE_URL_MODEL_ATTRIBUTE, addPathToUriAsUrlString(arguments.getBaseUri(), IMAGES_PATH));
        model.put(BODY_TEXT_MODEL_ATTRIBUTE, getBody(arguments));
        return model;
    }

    protected abstract String getSubject(EmailArgumentsDTO arguments);

    protected abstract String getBody(EmailArgumentsDTO arguments);
}
