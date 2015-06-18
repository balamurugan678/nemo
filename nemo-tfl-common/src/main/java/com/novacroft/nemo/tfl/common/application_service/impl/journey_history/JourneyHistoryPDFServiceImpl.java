package com.novacroft.nemo.tfl.common.application_service.impl.journey_history;

import static com.novacroft.nemo.common.constant.EmailConstant.ICON1;
import static com.novacroft.nemo.common.constant.EmailConstant.ICON2;
import static com.novacroft.nemo.common.constant.EmailConstant.IMAGES_PATH;
import static com.novacroft.nemo.common.constant.EmailConstant.JOURNEY_HISTORY;
import static com.novacroft.nemo.common.constant.EmailConstant.LOGO;
import static com.novacroft.nemo.common.constant.EmailConstant.NAME_AND_ADDRESS_LINES;
import static com.novacroft.nemo.common.constant.EmailConstant.NOW;
import static com.novacroft.nemo.common.constant.EmailConstant.OYSTER;
import static com.novacroft.nemo.common.constant.EmailConstant.PATH_SEPARATOR;
import static com.novacroft.nemo.tfl.common.constant.FreemarkerTemplate.JOURNEY_HISTORY_HTML_FOR_PDF;
import static com.novacroft.nemo.tfl.common.util.AddressFormatUtil.formatNameAndAddress;
import static com.novacroft.nemo.tfl.common.util.JourneyUtil.NO_TOUCH_IN;
import static com.novacroft.nemo.tfl.common.util.JourneyUtil.NO_TOUCH_OUT;
import static com.novacroft.nemo.tfl.common.util.JourneyUtil.isJourneyUnFinished;
import static com.novacroft.nemo.tfl.common.util.JourneyUtil.isJourneyUnStarted;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.novacroft.nemo.common.application_service.FreemarkerTemplateService;
import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.tfl.common.application_service.PersonalDetailsService;
import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryPDFService;
import com.novacroft.nemo.tfl.common.command.impl.PersonalDetailsCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.constant.SystemParameterCode;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDayDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyHistoryDTO;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Journey History PDF service implementation
 *
 * <p>This class uses Apache FOP (Formatting Objects Processor) to create the PDF file.</p>
 *
 * <p>The process is:</p>
 * <ol>
 * <li>Convert the input data to XML</li>
 * <li>Convert the input data to XML</li>
 * <li>Transform XML Apply style sheet to input data to </li>
 * </ol>
 *
 * @See http://xmlgraphics.apache.org/fop/
 */
@Service("journeyHistoryPDFService")
public class JourneyHistoryPDFServiceImpl implements JourneyHistoryPDFService {
    static final Logger logger = LoggerFactory.getLogger(JourneyHistoryPDFServiceImpl.class);

    @Autowired
    protected ApplicationContext applicationContext;
    @Autowired
    protected FreemarkerTemplateService freemarkerTemplateService;
    @Autowired
    protected PersonalDetailsService personalDetailsService;
    @Autowired
    protected SystemParameterService systemParameterService;
    
    @Override
    public byte[] createPDF(JourneyHistoryDTO journeyHistory) {
        String journeyHistoryHtml = convertJourneyHistoryModelToHtml(journeyHistory);
        return generatePdfFromHtml(journeyHistoryHtml);
    }

    protected String convertJourneyHistoryModelToHtml(JourneyHistoryDTO journeyHistory) {
        try {
            Template htmlTemplate = this.freemarkerTemplateService.getTemplate(JOURNEY_HISTORY_HTML_FOR_PDF);
            StringWriter stringWriter = new StringWriter();
            alterJourneyDescriptionForUnStartedAndUnFinishedJourneys(journeyHistory);
            htmlTemplate.process(getModel(journeyHistory), stringWriter);
            logger.debug(stringWriter.toString());
            return stringWriter.toString();
        } catch (TemplateException | IOException e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationServiceException(PrivateError.UNABLE_TO_CREATE_PDF.message(), e);
        }
    }

    protected void alterJourneyDescriptionForUnStartedAndUnFinishedJourneys(JourneyHistoryDTO journeyHistory) {
        for (JourneyDayDTO journeyDay : journeyHistory.getJourneyDays()) {
            findUnStartedAndUnFinishedJourneysAndAlterJourneyDescription(journeyDay);
        }
    }

    protected void findUnStartedAndUnFinishedJourneysAndAlterJourneyDescription(JourneyDayDTO journeyDay) {
        for (JourneyDTO journey : journeyDay.getJourneys()) {
            if (isJourneyUnStarted(journey) && journey.getJourneyDisplay() != null) {
                journey.getJourneyDisplay().setJourneyDescription(NO_TOUCH_IN + journey.getJourneyDisplay().getExitLocationName());
            } else if (isJourneyUnFinished(journey) && journey.getJourneyDisplay() != null) {
                journey.getJourneyDisplay().setJourneyDescription(NO_TOUCH_OUT + journey.getJourneyDisplay().getTransactionLocationName());
            }
        }
    }

    protected Map<String, Object> getModel(JourneyHistoryDTO journeyHistory) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put(JOURNEY_HISTORY, journeyHistory);
        model.put(LOGO, getImageUrlAsString("header-print-only.gif"));
        model.put(OYSTER, getImageUrlAsString("oyster.png"));
        model.put(ICON1, getImageUrlAsString("vsprite_x2.png"));
        model.put(ICON2, getImageUrlAsString("vsprite.png"));
        model.put(NOW, new Date());
        model.put(NAME_AND_ADDRESS_LINES, getNameAndAddress(journeyHistory.getCardNumber()));
        return model;
    }

    protected byte[] generatePdfFromHtml(String journeyHistoryHtml) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ITextRenderer renderer = getRenderer(journeyHistoryHtml);
            renderer.createPDF(byteArrayOutputStream);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationServiceException(PrivateError.UNABLE_TO_CREATE_PDF.message(), e);
        } finally {
            try {
                byteArrayOutputStream.flush();
                byteArrayOutputStream.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new ApplicationServiceException(PrivateError.UNABLE_TO_CREATE_PDF.message(), e);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * @param journeyHistoryHtml
     * @return ITextRenderer
     */
    protected ITextRenderer getRenderer(String journeyHistoryHtml) {
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(journeyHistoryHtml);
        renderer.layout();
        return renderer;
    }

    protected String getResourceUrlAsString(String resource) {
        try {
            return this.applicationContext.getResource(resource).getURL().toString();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationServiceException(PrivateError.UNABLE_TO_CREATE_PDF.message(), e);
        }
    }

    protected List<String> getNameAndAddress(String cardNumber) {
        PersonalDetailsCmdImpl cmd = this.personalDetailsService.getPersonalDetailsByCardNumber(cardNumber);
        return formatNameAndAddress("", cmd.getFirstName(), cmd.getInitials(), cmd.getLastName(), cmd.getHouseNameNumber(),
                cmd.getStreet(), cmd.getTown(), cmd.getPostcode(), cmd.getCountry());
    }

    protected String getImageUrlAsString(String imageName) {
        return this.systemParameterService.getParameterValue(SystemParameterCode.ONLINE_SYSTEM_BASE_URI.code()) + PATH_SEPARATOR +
                IMAGES_PATH + PATH_SEPARATOR + imageName;
    }
}
