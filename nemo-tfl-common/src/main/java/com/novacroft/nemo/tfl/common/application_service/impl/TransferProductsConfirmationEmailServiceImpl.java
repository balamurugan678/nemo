package com.novacroft.nemo.tfl.common.application_service.impl;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.application_service.impl.BaseEmailService;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.application_service.journey_history.AutoTopUpConfirmationEmailService;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.SystemParameterCode;
import com.novacroft.nemo.tfl.common.transfer.EmailArgumentsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

import static com.novacroft.nemo.common.constant.CommonFreemarkerTemplate.STANDARD_EMAIL_BODY;
import static com.novacroft.nemo.common.constant.EmailConstant.*;

/**
 * Transfer products Confirmation  email service
 */
@Service("transferProductsConfirmationEmailService")
public class TransferProductsConfirmationEmailServiceImpl extends BaseEmailService implements AutoTopUpConfirmationEmailService {
    @Autowired
    protected SystemParameterService systemParameterService;

    public void sendConfirmationMessage(EmailArgumentsDTO arguments) {
        Map model = prepareModel(arguments);
        @SuppressWarnings("unchecked")
        MimeMessage message = prepareMessage(model, STANDARD_EMAIL_BODY);
        this.emailService.sendMessage(message);
    }

    protected Map<String, String> prepareModel(EmailArgumentsDTO arguments) {
        Map<String, String> model = new HashMap<String, String>();
        model.put(TO_ADDRESS_MODEL_ATTRIBUTE, arguments.getToAddress());
        model.put(SUBJECT_MODEL_ATTRIBUTE, getContent(ContentCode.TRANSFER_PRODUCTS_CONFIRMATION_SUBJECT.textCode()));
        model.put(SALUTATION_MODEL_ATTRIBUTE, arguments.getSalutation());
		model.put(
				BODY_TEXT_MODEL_ATTRIBUTE,
				getContent(
						ContentCode.TRANSFER_PRODUCTS_CONFIRMATION_BODY.textCode(),
						arguments.getPickUpLocationName(),
						DateUtil.formatDate(arguments.getRangeFrom()),
						DateUtil.formatDate(arguments.getRangeTo() ),arguments.getReferenceNumber()+"" ));
        model.put(BASE_IMAGE_URL_MODEL_ATTRIBUTE,
                this.systemParameterService.getParameterValue(SystemParameterCode.ONLINE_SYSTEM_BASE_URI.code()) + PATH_SEPARATOR +
                        IMAGES_PATH);
        
        return model;
    }
}

