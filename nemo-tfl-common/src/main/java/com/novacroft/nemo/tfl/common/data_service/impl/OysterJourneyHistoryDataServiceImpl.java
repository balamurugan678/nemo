package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.common.utils.DateUtil.convertDateToXMLGregorian;
import static com.novacroft.nemo.common.utils.DateUtil.getDaysBetween;
import static com.novacroft.nemo.common.utils.OysterCardNumberUtil.getNineDigitNumberAsLong;

import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.JAXBElement;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.ehcache.annotations.Cacheable;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.constant.DeviceCategoryTypeOfTap;
import com.novacroft.nemo.tfl.common.constant.PseudoTransactionType;
import com.novacroft.nemo.tfl.common.converter.journey_history.InsertSyntheticTapResponseConverter;
import com.novacroft.nemo.tfl.common.converter.journey_history.JourneyHistoryResponseConverter;
import com.novacroft.nemo.tfl.common.data_service.OysterJourneyHistoryDataService;
import com.novacroft.nemo.tfl.common.service_access.OysterJourneyHistoryServiceAccess;
import com.novacroft.nemo.tfl.common.transfer.incomplete_journey_notification.IncompleteJourneyNotificationDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.InsertSyntheticTapResponseDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyHistoryResponseDTO;
import com.novacroft.tfl.web_service.model.oyster_journey_history.GetHistory;
import com.novacroft.tfl.web_service.model.oyster_journey_history.GetHistoryResponse;
import com.novacroft.tfl.web_service.model.oyster_journey_history.InsertSyntheticOysterTap;
import com.novacroft.tfl.web_service.model.oyster_journey_history.ObjectFactory;
import com.novacroft.tfl.web_service.model.oyster_journey_history.OriginatingSystem;
import com.novacroft.tfl.web_service.model.oyster_journey_history.SyntheticOysterTapRequest;

/**
 * Journey history data service
 */
@Service("oysterJourneyHistoryDataService")
public class OysterJourneyHistoryDataServiceImpl implements OysterJourneyHistoryDataService {
    @Autowired
    protected OysterJourneyHistoryServiceAccess oysterJourneyHistoryServiceAccess;
    @Autowired
    protected JourneyHistoryResponseConverter journeyHistoryResponseConverter;
    @Autowired
    protected InsertSyntheticTapResponseConverter insertSyntheticTapResponseConverter;
    
    @Override
    public JourneyHistoryResponseDTO findByCardNumberForDateRangeForOnline(String cardNumber, Date rangeFrom, Date rangeTo) {
        this.oysterJourneyHistoryServiceAccess.setOnlineTimeout();
        GetHistoryResponse response =
                this.oysterJourneyHistoryServiceAccess.getHistory(createRequestForOnline(cardNumber, rangeFrom, rangeTo));
        return this.journeyHistoryResponseConverter.convertModelToDto(response);
    }
 
    
    @Override
    public InsertSyntheticTapResponseDTO insertSyntheticTap(IncompleteJourneyNotificationDTO incompleteJourneyNotificationDTO, String cardNumber, Integer missingStationId, String reasonForMissing,  boolean submitedByAgent) {
        this.oysterJourneyHistoryServiceAccess.setOnlineTimeout();
        return insertSyntheticTapResponseConverter.convertModelToDto(oysterJourneyHistoryServiceAccess.insertSyntheticTap(createSyntheticTapRequest(incompleteJourneyNotificationDTO, cardNumber, missingStationId, reasonForMissing,  submitedByAgent)));
    }
    
    @Cacheable(cacheName = "findJourneyHistoryByCardNumberForDateRange")
    @Override
    public JourneyHistoryResponseDTO findByCardNumberForDateRangeForBatch(String cardNumber, Date rangeFrom, Date rangeTo) {
        this.oysterJourneyHistoryServiceAccess.setBatchTimeout();
        GetHistoryResponse response =
                this.oysterJourneyHistoryServiceAccess.getHistory(createRequestForBatch(cardNumber, rangeFrom, rangeTo));
        return this.journeyHistoryResponseConverter.convertModelToDto(response);
    }

    protected InsertSyntheticOysterTap createSyntheticTapRequest(IncompleteJourneyNotificationDTO incompleteJourneyNotificationDTO, String cardNumber,  Integer missingStationId, String reasonForMissing, boolean submitedByAgent ) {
    	final ObjectFactory jaxbFactory = new ObjectFactory(); 
    	final InsertSyntheticOysterTap insertTap = jaxbFactory.createInsertSyntheticOysterTap();
    	final SyntheticOysterTapRequest syntheticTap = jaxbFactory.createSyntheticOysterTapRequest();
    	final JAXBElement<SyntheticOysterTapRequest> syntheticTapElement = new ObjectFactory().createInsertSyntheticOysterTapSyntheticOysterTapRequest(syntheticTap);
    	insertTap.setSyntheticOysterTapRequest(syntheticTapElement);
    	syntheticTap.setSubmittedByAgent(submitedByAgent);
    	syntheticTap.setDateTimeOfSyntheticSubmission(DateUtil.convertDateToXMLGregorian(Calendar.getInstance().getTime()));
    	syntheticTap.setLocationNLCOfSyntheticTap(missingStationId);
    	syntheticTap.setReasonForSyntheticSubmission(jaxbFactory.createSyntheticOysterTapRequestReasonForSyntheticSubmission(reasonForMissing));
    	syntheticTap.setLinkedSequenceNo(incompleteJourneyNotificationDTO.getLinkedSequenceNo());
    	syntheticTap.setLinkedRolloverSequenceNo(incompleteJourneyNotificationDTO.getLinkedRolloverSequenceNo());
    	syntheticTap.setTransactionType((PseudoTransactionType.ENTRY.pseudoTransactionTypeId() == incompleteJourneyNotificationDTO.getLinkedTransactionType()) ? PseudoTransactionType.EXIT_2.pseudoTransactionTypeId() : PseudoTransactionType.ENTRY.pseudoTransactionTypeId() );
    	syntheticTap.setPrestigeId(getNineDigitNumberAsLong(cardNumber).intValue());
    	syntheticTap.setLinkedDayKey(incompleteJourneyNotificationDTO.getLinkedDayKey());
    	syntheticTap.setDeviceCategoryOfSyntheticTap(DeviceCategoryTypeOfTap.GATE.name());
    	syntheticTap.setSubmittedForTrainingPurposes(Boolean.FALSE);
    	syntheticTap.setOriginatingSystemOfSyntheticTap(OriginatingSystem.OYSTER_ONLINE);
        return insertTap;
    }
    
    
    protected GetHistory createRequestForOnline(String cardNumber, Date rangeFrom, Date rangeTo) {
        return createRequest(cardNumber, rangeFrom, rangeTo, Boolean.TRUE);
    }

    protected GetHistory createRequestForBatch(String cardNumber, Date rangeFrom, Date rangeTo) {
        return createRequest(cardNumber, rangeFrom, rangeTo, Boolean.FALSE);
    }

    protected GetHistory createRequest(String cardNumber, Date rangeFrom, Date rangeTo, Boolean highPriority) {
        GetHistory request = new ObjectFactory().createGetHistory();
        request.setPrestigeId(getNineDigitNumberAsLong(cardNumber));
        request.setStartDay(convertDateToXMLGregorian(rangeTo));
        request.setDaysBack(getDaysBetween(rangeFrom, rangeTo) + 1);
        request.setLive(BooleanUtils.toInteger(Boolean.FALSE));
        request.setTrainingpurpose(Boolean.FALSE);
        request.setIsHighPriority(highPriority);
        return request;
    }
}
