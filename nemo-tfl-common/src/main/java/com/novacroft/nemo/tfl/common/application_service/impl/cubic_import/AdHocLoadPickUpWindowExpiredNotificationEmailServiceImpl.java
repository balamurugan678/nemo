package com.novacroft.nemo.tfl.common.application_service.impl.cubic_import;

import static com.novacroft.nemo.common.constant.CommonFreemarkerTemplate.STANDARD_EMAIL_BODY;
import static com.novacroft.nemo.common.constant.EmailConstant.BASE_IMAGE_URL_MODEL_ATTRIBUTE;
import static com.novacroft.nemo.common.constant.EmailConstant.BODY_TEXT_MODEL_ATTRIBUTE;
import static com.novacroft.nemo.common.constant.EmailConstant.IMAGES_PATH;
import static com.novacroft.nemo.common.constant.EmailConstant.PATH_SEPARATOR;
import static com.novacroft.nemo.common.constant.EmailConstant.SALUTATION_MODEL_ATTRIBUTE;
import static com.novacroft.nemo.common.constant.EmailConstant.SUBJECT_MODEL_ATTRIBUTE;
import static com.novacroft.nemo.common.constant.EmailConstant.TO_ADDRESS_MODEL_ATTRIBUTE;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.application_service.impl.BaseEmailService;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.application_service.EmailMessageService;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.SystemParameterCode;
import com.novacroft.nemo.tfl.common.transfer.EmailArgumentsDTO;

/**
 * Auto Top Up  email service
 */
@Service("adHocLoadPickupWindowExpiredEmailService")
public class AdHocLoadPickUpWindowExpiredNotificationEmailServiceImpl extends BaseEmailService implements EmailMessageService {

    @Autowired
    protected SystemParameterService systemParameterService;

    @Override
    public void sendMessage(EmailArgumentsDTO arguments) {
        Map<String, Object>  model = prepareModel(arguments);
        MimeMessage message = prepareMessage(model, STANDARD_EMAIL_BODY);
        this.emailService.sendMessage(message);
    }

    protected Map<String, Object> prepareModel(EmailArgumentsDTO arguments) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put(TO_ADDRESS_MODEL_ATTRIBUTE, arguments.getToAddress());
        model.put(SUBJECT_MODEL_ATTRIBUTE, getContent(ContentCode.CUBIC_INTERACTION_PICKUP_WINDOW_EXPIRED_SUBJECT.textCode()));
        model.put(SALUTATION_MODEL_ATTRIBUTE, arguments.getSalutation());
		model.put(BODY_TEXT_MODEL_ATTRIBUTE,
				getContent(
						ContentCode.CUBIC_INTERACTION_PICKUP_WINDOW_EXPIRED_BODY.textCode(),
						DateUtil.formatDate(arguments.getPickUpExpiresOn() ),Objects.toString(arguments.getReferenceNumber() , StringUtil.EMPTY_STRING)));
        model.put(BASE_IMAGE_URL_MODEL_ATTRIBUTE,
                this.systemParameterService.getParameterValue(SystemParameterCode.ONLINE_SYSTEM_BASE_URI.code()) + PATH_SEPARATOR +
                        IMAGES_PATH);
        
        return model;
    }
	
}

