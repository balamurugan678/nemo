package com.novacroft.nemo.tfl.common.application_service.impl.journey_history;

import static com.novacroft.nemo.common.utils.DateUtil.formatTimeToMinute;
import static com.novacroft.nemo.common.utils.DateUtil.getDayBefore;
import static com.novacroft.nemo.common.utils.DateUtil.truncateAtDay;
import static com.novacroft.nemo.tfl.common.util.JourneyUtil.JOURNEY_DAY_CUT_OFF_HOUR;
import static com.novacroft.nemo.tfl.common.util.JourneyUtil.JOURNEY_DAY_CUT_OFF_MINUTE;
import static com.novacroft.nemo.tfl.common.util.JourneyUtil.createJourneyDescription;
import static com.novacroft.nemo.tfl.common.util.JourneyUtil.createTimeDescription;
import static com.novacroft.nemo.tfl.common.util.JourneyUtil.isAutoTopUpJourney;
import static com.novacroft.nemo.tfl.common.util.JourneyUtil.isWarningJourney;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.application_service.impl.BaseService;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.application_service.journey_history.ResolveJourneyReferencesService;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.data_service.PseudoTransactionTypeLookupDataService;
import com.novacroft.nemo.tfl.common.transfer.LocationDTO;
import com.novacroft.nemo.tfl.common.transfer.PseudoTransactionTypeLookupDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.TapDTO;
import com.novacroft.nemo.tfl.common.util.JourneyUtil;

/**
 * Look up references in the Journey History data
 */
@Service("resolveJourneyHistoryReferences")
public class ResolveJourneyReferencesServiceImpl extends BaseService implements ResolveJourneyReferencesService {

   

    @Autowired
    protected LocationDataService locationDataService;
    @Autowired
    protected PseudoTransactionTypeLookupDataService pseudoTransactionTypeLookupDataService;

    @Override
    public void resolveReferences(List<JourneyDTO> journeys) {
        for (JourneyDTO journey : journeys) {
            resolveJourneyReferences(journey);
        }
    }

    protected void resolveJourneyReferences(JourneyDTO journey) {
        resolveTransactionLocation(journey);
        resolveExitLocation(journey);
        resolveEffectiveJourneyDate(journey);
        resolveTransactionType(journey);
        resolveJourneyStartTime(journey);
        resolveJourneyEndTime(journey);
        resolveJourneyTime(journey);
        resolveJourneyDescription(journey);
        resolveChargeAmount(journey);
        resolveCreditAmount(journey);
        resolveWarning(journey);
        resolveManualCorrection(journey);
        resolveTopUpActivated(journey);
    }

    protected void resolveTransactionLocation(JourneyDTO journey) {
        if (isKnownLocation(journey.getTransactionLocation())) {
            journey.getJourneyDisplay().setTransactionLocationName(lookupLocationName(journey.getTransactionLocation()));
        }
    }

    protected void resolveExitLocation(JourneyDTO journey) {
        if (isKnownLocation(journey.getExitLocation())) {
            journey.getJourneyDisplay().setExitLocationName(lookupLocationName(journey.getExitLocation()));
        }
    }

    protected void resolveEffectiveJourneyDate(JourneyDTO journey) {
        journey.getJourneyDisplay().setEffectiveTrafficOn(truncateAtDay(journey.getTrafficOn()));
        if (journey.getTrafficOn() == null || journey.getTransactionAt() == null) {
            return;
        }
        if (JourneyUtil.isJourneyStartBeforeFourThirtyInTheMorning(journey.getTransactionAt())) {
            journey.getJourneyDisplay().setEffectiveTrafficOn(truncateAtDay(getDayBefore(journey.getJourneyDisplay().getEffectiveTrafficOn())));
        }
    }

    protected void resolveTransactionType(JourneyDTO journey) {
        PseudoTransactionTypeLookupDTO transactionTypeDTO = null;
        if ((journey.getPseudoTransactionTypeId()) > 0) {
            transactionTypeDTO = this.pseudoTransactionTypeLookupDataService.findById((long) journey.getPseudoTransactionTypeId());
        }
        journey.getJourneyDisplay().setPseudoTransactionTypeDisplayDescription(
                        transactionTypeDTO != null ? transactionTypeDTO.getDisplayDescription() : "");
    }

    protected void resolveJourneyStartTime(JourneyDTO journey) {
        journey.getJourneyDisplay().setJourneyStartTime(formatTimeToMinute(journey.getTransactionAt()));
    }

    protected void resolveJourneyEndTime(JourneyDTO journey) {
        journey.getJourneyDisplay().setJourneyEndTime(formatTimeToMinute(journey.getExitAt()));
    }

    protected void resolveJourneyTime(JourneyDTO journey) {
        journey.getJourneyDisplay().setJourneyTime(
                        createTimeDescription(journey.getTransactionAt(), journey.getExitAt(),
                                        getContent(ContentCode.JOURNEY_TIME_SEPARATOR.textCode())));
    }

    protected void resolveJourneyDescription(JourneyDTO journey) {
        if (isAutoTopUpJourney(journey.getPseudoTransactionTypeId())) {
            journey.getJourneyDisplay().setJourneyDescription(
                            createJourneyDescription(journey.getJourneyDisplay().getTransactionLocationName(),
                                            getContent(ContentCode.JOURNEY_TOP_UP_SEPARATOR.textCode()), journey.getJourneyDisplay()
                                                            .getPseudoTransactionTypeDisplayDescription()));
        } else {
            journey.getJourneyDisplay().setJourneyDescription(
                            createJourneyDescription(journey.getJourneyDisplay().getTransactionLocationName(), journey.getJourneyDisplay()
                                            .getExitLocationName(), getContent(ContentCode.JOURNEY_ORIGIN_DESTINATION_SEPARATOR.textCode()), journey
                                            .getRouteId(), getContent(ContentCode.JOURNEY_BUS_ROUTE_PREFIX.textCode())));
        }
    }

    protected void resolveChargeAmount(JourneyDTO journey) {
        if (journey.getAddedStoredValueBalance() <= 0) {
            journey.getJourneyDisplay().setChargeAmount(Math.abs(journey.getAddedStoredValueBalance()));
        }
    }

    protected void resolveCreditAmount(JourneyDTO journey) {
        if (journey.getAddedStoredValueBalance() > 0) {
            journey.getJourneyDisplay().setCreditAmount(journey.getAddedStoredValueBalance());
        }
    }

    protected void resolveWarning(JourneyDTO journey) {
        journey.getJourneyDisplay().setWarning(isWarningJourney(journey.getPseudoTransactionTypeId()));
    }

    protected boolean isKnownLocation(Integer location) {
        return (location != null) && (location != -1) && (location != 0);
    }

    protected String lookupLocationName(Integer location) {
        LocationDTO locationDTO = this.locationDataService.findById((long) location);
        return (locationDTO != null) ? locationDTO.getName() : "";
    }

    protected boolean isJourneyStartBeforeFourThirtyInTheMorning(Date startAt) {
        if (startAt == null) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startAt);
        return calendar.get(Calendar.HOUR_OF_DAY) < JOURNEY_DAY_CUT_OFF_HOUR
                        || (calendar.get(Calendar.HOUR_OF_DAY) == JOURNEY_DAY_CUT_OFF_HOUR && calendar.get(Calendar.MINUTE) < JOURNEY_DAY_CUT_OFF_MINUTE);
    }

    protected void resolveManualCorrection(JourneyDTO journey) {
        journey.getJourneyDisplay().setManuallyCorrected(Boolean.FALSE);
        for (TapDTO tap : journey.getTaps()) {
            if (tap.getSyntheticTap()) {
                journey.getJourneyDisplay().setManuallyCorrected(Boolean.TRUE);
                String jounrneyDescription = journey.getJourneyDisplay().getJourneyDescription();
                if(StringUtil.isNotBlank(jounrneyDescription)){
                	journey.getJourneyDisplay().setJourneyDescription(jounrneyDescription + " (Manually corrected !!)");
                }
                return;
            }
        }
    }

    protected void resolveTopUpActivated(JourneyDTO journey) {
        journey.getJourneyDisplay().setTopUpActivated(JourneyUtil.isAutoTopUpJourney(journey.getPseudoTransactionTypeId()));
    }

}
