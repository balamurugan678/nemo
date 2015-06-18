package com.novacroft.nemo.tfl.common.application_service.impl.journey_history;

import static com.novacroft.nemo.common.utils.DateUtil.getDayBefore;
import static com.novacroft.nemo.common.utils.DateUtil.truncateAtDay;
import static com.novacroft.nemo.tfl.common.form_validator.JourneyHistoryValidator.LAST_WEEK_OPTION_CODE;
import static com.novacroft.nemo.tfl.common.util.JourneyStatementDateUtil.isMonday;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.application_service.impl.BaseService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.application_service.journey_history.CollateJourneysService;
import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryCSVService;
import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryPDFService;
import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryService;
import com.novacroft.nemo.tfl.common.application_service.journey_history.ResolveJourneyReferencesService;
import com.novacroft.nemo.tfl.common.application_service.journey_history.ResolveTapReferencesService;
import com.novacroft.nemo.tfl.common.command.impl.CompleteJourneyCommandImpl;
import com.novacroft.nemo.tfl.common.comparator.journey_history.JourneyComparator;
import com.novacroft.nemo.tfl.common.comparator.journey_history.JourneyDayComparator;
import com.novacroft.nemo.tfl.common.comparator.journey_history.TapComparator;
import com.novacroft.nemo.tfl.common.constant.JourneyNature;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.OysterJourneyHistoryDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDayDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyHistoryDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.TapDTO;
import com.novacroft.nemo.tfl.common.util.JourneyStatementDateUtil;
import com.novacroft.nemo.tfl.common.util.JourneyUtil;

/**
 * Journey History application service implementation
 */
@Service("journeyHistoryService")
public class JourneyHistoryServiceImpl extends BaseService implements JourneyHistoryService {
    static final Logger logger = LoggerFactory.getLogger(JourneyHistoryServiceImpl.class);

    public static final String WEEK_RANGE_DATE_DISPLAY_FORMAT = "dd MMMM";
    public static final String WEEK_RANGE_DATE_DISPLAY_FORMAT_SEPARATOR = " - ";
    private static final int WEEK_RANGE_DATE_LOOP_LIMIT = 7;
    private static final int WEEK_RANGE_LAST_OPTION_INDEX = 10;
    private static final int MINUS_7_DAYS = -7;
    public static final int MINUS_56_DAYS = -56;
    private static final String CONTENT_CODE_THIS_WEEK = "JourneyHistory.thisWeek";
    private static final String CONTENT_CODE_CUSTOM_PERIOD = "JourneyHistory.customPeriod";

    @Autowired
    protected JourneyHistoryCSVService journeyHistoryCSVService;
    @Autowired
    protected JourneyHistoryPDFService journeyHistoryPDFService;
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected ResolveJourneyReferencesService resolveJourneyReferencesService;
    @Autowired
    protected ResolveTapReferencesService resolveTapReferencesService;
    @Autowired
    protected OysterJourneyHistoryDataService oysterJourneyHistoryDataService;
    @Autowired
    protected CollateJourneysService collateJourneysService;

    @Override
    public JourneyHistoryDTO getJourneyHistory(Long cardId, Date rangeFrom, Date rangeTo) {
        CardDTO cardDTO = this.cardDataService.findById(cardId);
        List<JourneyDTO> journeys = this.oysterJourneyHistoryDataService
                .findByCardNumberForDateRangeForOnline(cardDTO.getCardNumber(), rangeFrom, rangeTo).getJourneys();
        this.resolveJourneyReferencesService.resolveReferences(journeys);
        List<JourneyDayDTO> journeyDays = this.collateJourneysService.collateByDay(journeys);
        sortJourneyDaysWithMostRecentFirst(journeyDays);
        return new JourneyHistoryDTO(cardDTO.getCardNumber(), rangeFrom, rangeTo, journeyDays);
    }

    @Override
    public byte[] getJourneyHistoryAsCsv(JourneyHistoryDTO journeyHistory) {
        return this.journeyHistoryCSVService.createCSV(journeyHistory.getJourneyDays());
    }

    @Override
    public JourneyDTO getJourneyHistoryItem(Long cardId, Date journeyDate, Integer journeyId) {
        CardDTO cardDTO = this.cardDataService.findById(cardId);
        List<JourneyDTO> journeys = this.oysterJourneyHistoryDataService
                .findByCardNumberForDateRangeForOnline(cardDTO.getCardNumber(), journeyDate, journeyDate).getJourneys();
        JourneyDTO journey = null;
        if (null != journeys && !journeys.isEmpty()) {
            journey = findJourneyById(journeyId, journeys);
            if (null != journey) {
                journeys = new ArrayList<JourneyDTO>();
                journeys.add(journey);
                sortJourneysWithMostRecentFirst(journeys);
                this.resolveJourneyReferencesService.resolveReferences(journeys);
            }
        }
        return journey;
    }

    @Override
    public JourneyDTO getIncompleteJourney(Long cardId, Date transactionDateTime, Integer linkedStation) {
        CardDTO cardDTO = this.cardDataService.findById(cardId);
        final Date fromMidnightTime = DateUtil.getMidnightDay(transactionDateTime);
        final Date nextDayMidnight = DateUtil.addDaysToDate(fromMidnightTime, 1);
        final List<JourneyDTO> journeysList = oysterJourneyHistoryDataService
                .findByCardNumberForDateRangeForOnline(cardDTO.getCardNumber(), fromMidnightTime, nextDayMidnight)
                .getJourneys();
        return findLinkedJourney(transactionDateTime, linkedStation, journeysList);
    }

    @Override
    public JourneyDayDTO getJourneysForDay(Long cardId, Date transactionDateTime) {
        Date effectiveDay = getEffectiveJourneyDay(transactionDateTime);
        final Date fromTime = DateUtil.addDaysToDate(transactionDateTime, -1);
        final Date endTime = DateUtil.addDaysToDate(transactionDateTime, 1);
        JourneyHistoryDTO journeyHistoryDTO = getJourneyHistory(cardId, fromTime, endTime);
        return findLinkedDay(journeyHistoryDTO, effectiveDay);

    }

    protected Date getEffectiveJourneyDay(Date transactionDateTime) {
        if (JourneyUtil.isJourneyStartBeforeFourThirtyInTheMorning(transactionDateTime)) {
            return truncateAtDay(getDayBefore(transactionDateTime));
        }
        return truncateAtDay(transactionDateTime);
    }

    protected JourneyDayDTO findLinkedDay(JourneyHistoryDTO journeyHistoryDTO, Date effectiveDay) {
        if (journeyHistoryDTO != null) {
            for (JourneyDayDTO journeyDayDTO : journeyHistoryDTO.getJourneyDays()) {
                if (effectiveDay.equals(journeyDayDTO.getEffectiveTrafficOn())) {
                    return journeyDayDTO;
                }
            }
        }
        return null;
    }

    protected JourneyDTO findLinkedJourney(Date transactionDateTime, Integer linkedStation, List<JourneyDTO> journeysList) {
        if (journeysList != null) {
            for (JourneyDTO journeyDTO : journeysList) {
                List<TapDTO> tapList = journeyDTO.getTaps();
                for (TapDTO tap : tapList) {
                	
                    if (transactionDateTime.equals(tap.getTransactionAt()) &&
                            linkedStation.equals(tap.getNationalLocationCode()) && isIncomplete(journeyDTO) ) {
                        List<JourneyDTO> journeyInList = new ArrayList<>();
                        journeyInList.add(journeyDTO);
                        this.resolveJourneyReferencesService.resolveReferences(journeyInList);
                        updateTapDisplayDescriptionsAndFlags(journeyDTO.getTaps());
                        return journeyInList.get(0);
                    }
                }
            }
        }
        return null;
    }
    
    protected boolean isIncomplete(JourneyDTO journeyDTO){
    	Set<JourneyNature> journeyNatures = JourneyNature.getNatures(journeyDTO.getPseudoTransactionTypeId());
    	if(journeyNatures != null){
    		return journeyNatures.contains(JourneyNature.UNFINISHED) || journeyNatures.contains(JourneyNature.UNSTARTED) ;
    	}
    	return false;
    }

    @Override
    public byte[] getJourneyHistoryAsPdf(JourneyHistoryDTO journeyHistory) {
        return this.journeyHistoryPDFService.createPDF(journeyHistory);
    }

    @Override
    public SelectListDTO getWeekRangeSelectListDTO() {
        SelectListDTO selectList = new SelectListDTO();
        selectList.setName("");
        selectList.setDescription("");

        List<SelectListOptionDTO> options = new ArrayList<SelectListOptionDTO>();

        int index = 0;
        options.add(buildThisWeekOption(index));

        options = addWeekRangeOptions(options);

        index = WEEK_RANGE_LAST_OPTION_INDEX;
        options.add(buildCustomPeriodOption(index));

        selectList.setOptions(options);

        return selectList;
    }

    protected void sortJourneyDaysWithMostRecentFirst(List<JourneyDayDTO> journeyDays) {
        Collections.sort(journeyDays, new JourneyDayComparator());
        for (JourneyDayDTO journeyDay : journeyDays) {
            sortJourneysWithMostRecentFirst(journeyDay.getJourneys());
        }
    }

    protected void sortJourneysWithMostRecentFirst(List<JourneyDTO> journeys) {
        Collections.sort(journeys, new JourneyComparator());
        for (JourneyDTO journey : journeys) {
            Collections.sort(journey.getTaps(), new TapComparator());
        }
    }

    protected List<SelectListOptionDTO> addWeekRangeOptions(List<SelectListOptionDTO> options) {
        Date currentDate = new Date();
        Date startOfWeekDate = JourneyStatementDateUtil.getStartOfWeek(DateUtil.getDayBefore(currentDate));
        Date endOfWeekDate = JourneyStatementDateUtil.getEndOfWeek(DateUtil.getDayBefore(currentDate));
        int limit;
        if (isMonday(currentDate)) {
            limit = WEEK_RANGE_DATE_LOOP_LIMIT;
        } else {
            limit = WEEK_RANGE_DATE_LOOP_LIMIT + 1;
        }
        for (int index = 1; index < limit; index++) {
            startOfWeekDate = DateUtil.addDaysToDate(startOfWeekDate, MINUS_7_DAYS);
            endOfWeekDate = DateUtil.addDaysToDate(endOfWeekDate, MINUS_7_DAYS);
            options.add(buildWeekRangeOption(index, startOfWeekDate, endOfWeekDate));
        }
        endOfWeekDate = JourneyStatementDateUtil.getEndOfWeek(DateUtil.addDaysToDate(currentDate, MINUS_56_DAYS));
        Date startDate = DateUtil.addDaysToDate(currentDate, MINUS_56_DAYS);
        options.add(buildWeekRangeOption(LAST_WEEK_OPTION_CODE, startDate, endOfWeekDate));

        return options;
    }

    protected SelectListOptionDTO buildWeekRangeOption(int index, Date startOfWeekDate, Date endOfWeekDate) {
        return buildSelectListOptionDTO(index, buildWeekRangeOptionMeaning(startOfWeekDate, endOfWeekDate));
    }

    protected SelectListOptionDTO buildThisWeekOption(int index) {
        return buildSelectListOptionDTO(index, getContent(CONTENT_CODE_THIS_WEEK));
    }

    protected SelectListOptionDTO buildCustomPeriodOption(int index) {
        return buildSelectListOptionDTO(index, getContent(CONTENT_CODE_CUSTOM_PERIOD));
    }

    protected SelectListOptionDTO buildSelectListOptionDTO(int index, String meaning) {
        SelectListOptionDTO option = new SelectListOptionDTO();
        option.setDisplayOrder(index);
        option.setValue(Integer.toString(index));
        option.setMeaning(meaning);
        return option;
    }

    protected String buildWeekRangeOptionMeaning(Date startOfWeekDate, Date endOfWeekDate) {
        StringBuilder formattedText = new StringBuilder(DateUtil.formatDate(startOfWeekDate, WEEK_RANGE_DATE_DISPLAY_FORMAT))
                .append(WEEK_RANGE_DATE_DISPLAY_FORMAT_SEPARATOR)
                .append(DateUtil.formatDate(endOfWeekDate, WEEK_RANGE_DATE_DISPLAY_FORMAT));
        return formattedText.toString();
    }

    protected JourneyDTO findJourneyById(Integer journeyId, List<JourneyDTO> journeys) {
        JourneyDTO foundDTO = null;
        for (JourneyDTO journey : journeys) {
            if (journeyId.equals(journey.getJourneyId())) {
                foundDTO = journey;
                updateTapDisplayDescriptionsAndFlags(foundDTO.getTaps());
                return foundDTO;
            }
        }
        return null;
    }

    protected void updateTapDisplayDescriptionsAndFlags(List<TapDTO> taps) {
        resolveTapReferencesService.resolveReferences(taps);
    }

    @Override
    public void resolveLinkedStationAndTransaction(CompleteJourneyCommandImpl completeJourneyCommandImpl) {
        final TapDTO relatedTap = resolveStartOrExitTapForIncompleteJourney(completeJourneyCommandImpl.getJourney());
        if (null != relatedTap) {
            completeJourneyCommandImpl.setLinkedStationKey(relatedTap.getNationalLocationCode());
            completeJourneyCommandImpl.setLinkedTransactionTime(relatedTap.getTransactionAt());
        }
    }

    protected TapDTO resolveStartOrExitTapForIncompleteJourney(final JourneyDTO journeyDTO) {
        if (journeyDTO != null) {
            if (JourneyUtil.isWarningJourney(journeyDTO.getPseudoTransactionTypeId()) &&
                    StringUtil.isBlank(journeyDTO.getJourneyDisplay().getTransactionLocationName())) {
                return journeyDTO.getTaps().get(journeyDTO.getTaps().size() - 1);
            } else if (JourneyUtil.isWarningJourney(journeyDTO.getPseudoTransactionTypeId()) &&
                    StringUtil.isBlank(journeyDTO.getJourneyDisplay().getExitLocationName())) {
                return journeyDTO.getTaps().get(0);
            }
        }
        return null;
    }
}
