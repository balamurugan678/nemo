package com.novacroft.nemo.tfl.common.application_service.impl.journey_history;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.application_service.impl.BaseEmailService;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.application_service.journey_history.AutoTopUpConfirmationEmailService;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.SystemParameterCode;
import com.novacroft.nemo.tfl.common.transfer.EmailArgumentsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

import static com.novacroft.nemo.common.constant.CommonFreemarkerTemplate.STANDARD_EMAIL_BODY;
import static com.novacroft.nemo.common.constant.EmailConstant.*;

/**
 * Auto Top Up  email service
 */
@Service("autoTopUpConfirmationEmailService")
public class AutoTopUpConfirmationEmailServiceImpl extends BaseEmailService implements AutoTopUpConfirmationEmailService {
    @Autowired
    protected ApplicationContext applicationContext;
    @Autowired
    protected SystemParameterService systemParameterService;

    @Override
    @SuppressWarnings("unchecked")
    public void sendConfirmationMessage(EmailArgumentsDTO arguments) {
        Map model = prepareModel(arguments);
        MimeMessage message = prepareMessage(model, STANDARD_EMAIL_BODY);
        this.emailService.sendMessage(message);
    }

    protected Map<String, String> prepareModel(EmailArgumentsDTO arguments) {
        Map<String, String> model = new HashMap<String, String>();
        model.put(TO_ADDRESS_MODEL_ATTRIBUTE, arguments.getToAddress());
        model.put(SUBJECT_MODEL_ATTRIBUTE, getContent(ContentCode.AUTO_TOP_UP_CONFIRMATION_SUBJECT.textCode()));
        model.put(SALUTATION_MODEL_ATTRIBUTE, arguments.getSalutation());
		model.put(
				BODY_TEXT_MODEL_ATTRIBUTE,
				getContent(
						ContentCode.AUTO_TOP_UP_CONFIRMATION_BODY.textCode(),
						arguments.getPickUpLocationName(),
						DateUtil.formatDate(arguments.getRangeFrom()),
						DateUtil.formatDate(arguments.getRangeTo() ),arguments.getReferenceNumber()+"" ));
        model.put(BASE_IMAGE_URL_MODEL_ATTRIBUTE,
                this.systemParameterService.getParameterValue(SystemParameterCode.ONLINE_SYSTEM_BASE_URI.code()) + PATH_SEPARATOR +
                        IMAGES_PATH);
        
        return model;
    }
}

