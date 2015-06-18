package com.novacroft.nemo.tfl.common.application_service.impl.journey_history;

import static com.novacroft.nemo.common.utils.DateUtil.formatDate;
import static com.novacroft.nemo.common.utils.UriUrlUtil.getUriFromAString;

import java.net.URI;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.application_service.ApplicationEventService;
import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.constant.EventName;
import com.novacroft.nemo.common.support.InformationBuilder;
import com.novacroft.nemo.tfl.common.application_service.impl.BaseEmailPreparationService;
import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryScheduledStatementService;
import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryService;
import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryStatementEmailService;
import com.novacroft.nemo.tfl.common.constant.JourneyHistoryFrequency;
import com.novacroft.nemo.tfl.common.constant.JourneyHistoryOutput;
import com.novacroft.nemo.tfl.common.constant.SystemParameterCode;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CardPreferencesDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CardPreferencesDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.EmailArgumentsDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyHistoryDTO;
import com.novacroft.nemo.tfl.common.util.JourneyStatementDateUtil;

/**
 * Journey History scheduled email statement service
 */
@Service("journeyHistoryScheduledStatementService")
public class JourneyHistoryScheduledStatementServiceImpl extends BaseEmailPreparationService
        implements JourneyHistoryScheduledStatementService {
    @Autowired
    protected CardPreferencesDataService cardPreferencesDataService;
    @Autowired
    protected JourneyHistoryService journeyHistoryService;
    @Autowired
    protected JourneyHistoryStatementEmailService journeyHistoryStatementEmailService;
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected SystemParameterService systemParameterService;
    @Autowired
    protected ApplicationEventService applicationEventService;

    @Override
    public List<CardPreferencesDTO> getCardsForWeeklyStatement() {
        return getCardsForStatementFrequency(JourneyHistoryFrequency.WEEKLY.code());
    }

    @Override
    public List<CardPreferencesDTO> getCardsForMonthlyStatement() {
        return getCardsForStatementFrequency(JourneyHistoryFrequency.MONTHLY.code());
    }

    @Override
    public void sendWeeklyStatement(Long cardId, Date rangeFrom, Date rangeTo) {
        EmailArgumentsDTO emailArguments = getEmailArguments(cardId, rangeFrom, rangeTo);
        this.journeyHistoryStatementEmailService.sendWeeklyMessage(emailArguments);
        createEvent(emailArguments);

    }

    @Override
    public void sendMonthlyStatement(Long cardId, Date rangeFrom, Date rangeTo) {
        EmailArgumentsDTO emailArguments = getEmailArguments(cardId, rangeFrom, rangeTo);
        this.journeyHistoryStatementEmailService.sendMonthlyMessage(emailArguments);
        createEvent(emailArguments);
    }

    @Override
    public Date getStartOfLastWeek() {
        return JourneyStatementDateUtil.getStartOfLastWeek(new Date());
    }

    @Override
    public Date getEndOfLastWeek() {
        return JourneyStatementDateUtil.getEndOfLastWeek(new Date());
    }

    @Override
    public Date getStartOfLastMonth() {
        return JourneyStatementDateUtil.getStartOfLastMonth(new Date());
    }

    @Override
    public Date getEndOfLastMonth() {
        return JourneyStatementDateUtil.getEndOfLastMonth(new Date());
    }

    protected List<CardPreferencesDTO> getCardsForStatementFrequency(String frequency) {
        return this.cardPreferencesDataService.findByEmailPreference(frequency);
    }

    protected EmailArgumentsDTO getEmailArguments(Long cardId, Date rangeFrom, Date rangeTo) {
        CardPreferencesDTO cardPreferencesDTO = this.cardPreferencesDataService.findByCardId(cardId);
        JourneyHistoryDTO journeyHistoryDTO = this.journeyHistoryService.getJourneyHistory(cardId, rangeFrom, rangeTo);
        byte[] journeyHistoryCsv = getJourneyHistoryCsvIfPreferred(cardPreferencesDTO, journeyHistoryDTO);
        byte[] journeyHistoryPdf = getJourneyHistoryPdfIfPreferred(cardPreferencesDTO, journeyHistoryDTO);
        CardDTO cardDTO = this.cardDataService.findById(cardId);
        CustomerDTO customerDTO = this.customerDataService.findById(cardDTO.getCustomerId());
        URI baseUri = getUriFromAString(
                this.systemParameterService.getParameterValue(SystemParameterCode.ONLINE_SYSTEM_BASE_URI.code()));
        return new EmailArgumentsDTO(customerDTO.getEmailAddress(), getSalutation(customerDTO), baseUri,
                cardDTO.getCardNumber(), rangeFrom, rangeTo, journeyHistoryCsv, journeyHistoryPdf, customerDTO.getId());
    }

    protected byte[] getJourneyHistoryCsvIfPreferred(CardPreferencesDTO cardPreferencesDTO,
                                                     JourneyHistoryDTO journeyHistoryDTO) {
        byte[] journeyHistoryCsv = null;
        if (isCsvPreferred(cardPreferencesDTO.getAttachmentType())) {
            journeyHistoryCsv = this.journeyHistoryService.getJourneyHistoryAsCsv(journeyHistoryDTO);
        }
        return journeyHistoryCsv;
    }

    protected byte[] getJourneyHistoryPdfIfPreferred(CardPreferencesDTO cardPreferencesDTO,
                                                     JourneyHistoryDTO journeyHistoryDTO) {
        byte[] journeyHistoryPdf = null;
        if (isPdfPreferred(cardPreferencesDTO.getAttachmentType())) {
            journeyHistoryPdf = this.journeyHistoryService.getJourneyHistoryAsPdf(journeyHistoryDTO);
        }
        return journeyHistoryPdf;
    }

    protected boolean isCsvPreferred(String attachmentType) {
        return JourneyHistoryOutput.CSV.code().equals(attachmentType) ||
                JourneyHistoryOutput.CSV_AND_PDF.code().equals(attachmentType);
    }

    protected boolean isPdfPreferred(String attachmentType) {
        return JourneyHistoryOutput.PDF.code().equals(attachmentType) ||
                JourneyHistoryOutput.CSV_AND_PDF.code().equals(attachmentType);
    }

    protected void createEvent(EmailArgumentsDTO emailArguments) {
        this.applicationEventService
                .create(emailArguments.getCustomerId(), EventName.EMAIL_SEND, buildAdditionalInformation(emailArguments));
    }

    protected String buildAdditionalInformation(EmailArgumentsDTO emailArguments) {
        return new InformationBuilder().append("Journey History Statement")
                .append("; Oyster card number [%s]", emailArguments.getCardNumber())
                .append("; period [%s - %s]", formatDate(emailArguments.getRangeFrom()),
                        formatDate(emailArguments.getRangeTo())).toString();
    }
}
