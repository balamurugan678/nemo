package com.novacroft.nemo.tfl.common.application_service.impl.journey_history;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.application_service.impl.BaseEmailService;
import com.novacroft.nemo.common.transfer.EmailAttachmentDTO;
import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryStatementEmailService;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.JourneyHistoryOutput;
import com.novacroft.nemo.tfl.common.constant.SystemParameterCode;
import com.novacroft.nemo.tfl.common.transfer.EmailArgumentsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.novacroft.nemo.common.constant.CommonFreemarkerTemplate.STANDARD_EMAIL_BODY;
import static com.novacroft.nemo.common.constant.EmailConstant.*;
import static com.novacroft.nemo.common.utils.DateUtil.formatDate;
import static com.novacroft.nemo.tfl.common.util.JourneyUtil.createDatedFileName;

/**
 * Journey history statement email service
 */
@Service("journeyHistoryStatementEmailService")
public class JourneyHistoryStatementEmailServiceImpl extends BaseEmailService implements JourneyHistoryStatementEmailService {
    @Autowired
    protected ApplicationContext applicationContext;
    @Autowired
    protected SystemParameterService systemParameterService;

    @Override
    @SuppressWarnings("unchecked")
    public void sendWeeklyMessage(EmailArgumentsDTO arguments) {
        Map model = prepareModelForWeeklyStatement(arguments);
        MimeMessage message = prepareMessage(model, STANDARD_EMAIL_BODY, getAttachments(arguments));
        this.emailService.sendMessage(message);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void sendMonthlyMessage(EmailArgumentsDTO arguments) {
        Map model = prepareModelForMonthlyStatement(arguments);
        MimeMessage message = prepareMessage(model, STANDARD_EMAIL_BODY, getAttachments(arguments));
        this.emailService.sendMessage(message);
    }

    protected Map<String, String> prepareModelForWeeklyStatement(EmailArgumentsDTO arguments) {
        Map<String, String> model = prepareModel(arguments);
        model.put(SUBJECT_MODEL_ATTRIBUTE, getContent(ContentCode.JOURNEY_WEEKLY_SUBJECT.textCode(), arguments.getCardNumber(),
                formatDate(arguments.getRangeFrom()), formatDate(arguments.getRangeTo())));
        model.put(BODY_TEXT_MODEL_ATTRIBUTE, getContent(ContentCode.JOURNEY_WEEKLY_BODY.textCode(), arguments.getCardNumber(),
                formatDate(arguments.getRangeFrom()), formatDate(arguments.getRangeTo())));
        return model;
    }

    protected Map<String, String> prepareModelForMonthlyStatement(EmailArgumentsDTO arguments) {
        Map<String, String> model = prepareModel(arguments);
        model.put(SUBJECT_MODEL_ATTRIBUTE, getContent(ContentCode.JOURNEY_MONTHLY_SUBJECT.textCode(), arguments.getCardNumber(),
                formatDate(arguments.getRangeFrom()), formatDate(arguments.getRangeTo())));
        model.put(BODY_TEXT_MODEL_ATTRIBUTE, getContent(ContentCode.JOURNEY_MONTHLY_BODY.textCode(), arguments.getCardNumber(),
                formatDate(arguments.getRangeFrom()), formatDate(arguments.getRangeTo())));
        return model;
    }

    protected Map<String, String> prepareModel(EmailArgumentsDTO arguments) {
        Map<String, String> model = new HashMap<String, String>();
        model.put(TO_ADDRESS_MODEL_ATTRIBUTE, arguments.getToAddress());
        model.put(SALUTATION_MODEL_ATTRIBUTE, arguments.getSalutation());
        model.put(BASE_IMAGE_URL_MODEL_ATTRIBUTE,
                this.systemParameterService.getParameterValue(SystemParameterCode.ONLINE_SYSTEM_BASE_URI.code()) + PATH_SEPARATOR +
                        IMAGES_PATH);
        return model;
    }

    protected List<EmailAttachmentDTO> getAttachments(EmailArgumentsDTO arguments) {
        List<EmailAttachmentDTO> attachments = new ArrayList<EmailAttachmentDTO>();
        if (arguments.getJourneyHistoryCsv() != null) {
            attachments
                    .add(new EmailAttachmentDTO(createDatedFileName(arguments.getCardNumber(), JourneyHistoryOutput.CSV.code()),
                            JourneyHistoryOutput.CSV.contentType(), arguments.getJourneyHistoryCsv()));
        }
        if (arguments.getJourneyHistoryPdf() != null) {
            attachments
                    .add(new EmailAttachmentDTO(createDatedFileName(arguments.getCardNumber(), JourneyHistoryOutput.PDF.code()),
                            JourneyHistoryOutput.PDF.contentType(), arguments.getJourneyHistoryPdf()));
        }
        return attachments;
    }
}

