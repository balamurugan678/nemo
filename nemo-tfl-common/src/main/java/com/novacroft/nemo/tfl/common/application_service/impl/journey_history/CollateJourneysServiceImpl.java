package com.novacroft.nemo.tfl.common.application_service.impl.journey_history;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.novacroft.nemo.tfl.common.application_service.journey_history.CollateJourneysService;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDayDTO;

/**
 * Collate journeys
 */
@Service("collateJourneysService")
public class CollateJourneysServiceImpl implements CollateJourneysService {
    @Override
    public List<JourneyDayDTO> collateByDay(List<JourneyDTO> journeys) {
        List<JourneyDayDTO> journeyDays = new ArrayList<JourneyDayDTO>();
        for (JourneyDTO journey : journeys) {
            addJourneyToDay(journeyDays, journey);
        }
        for (JourneyDayDTO day : journeyDays) {
            enrichJourneyDayDTOWithExplanatoryFlags(day);
        }
        return journeyDays;
    }

    protected void addJourneyToDay(List<JourneyDayDTO> journeyDays, JourneyDTO journey) {
        JourneyDayDTO day = getDay(journeyDays, journey.getJourneyDisplay().getEffectiveTrafficOn());
        day.getJourneys().add(journey);
        day.setDailyBalance(day.getDailyBalance() + journey.getAddedStoredValueBalance());
        day.setTotalSpent(day.getTotalSpent() + deriveSpentAmount(journey));
    }

    protected JourneyDayDTO getDay(List<JourneyDayDTO> journeyDays, Date effectiveTrafficOn) {
        for (JourneyDayDTO day : journeyDays) {
            if (day.getEffectiveTrafficOn().equals(effectiveTrafficOn)) {
                return day;
            }
        }
        return initialiseDay(journeyDays, effectiveTrafficOn);
    }

    protected JourneyDayDTO initialiseDay(List<JourneyDayDTO> journeyDays, Date effectiveTrafficOn) {
        JourneyDayDTO day = new JourneyDayDTO(effectiveTrafficOn, new ArrayList<JourneyDTO>(), 0, 0);
        journeyDays.add(day);
        return day;
    }

    protected int deriveSpentAmount(JourneyDTO journey) {
        if (journey.getAddedStoredValueBalance() < 0) {
            return Math.abs(journey.getAddedStoredValueBalance());
        } else {
            return 0;
        }
    }

    protected void enrichJourneyDayDTOWithExplanatoryFlags(JourneyDayDTO day) {
        for (JourneyDTO journey : day.getJourneys()) {
            if (journey.getJourneyDisplay().getWarning().booleanValue()) {
                day.setContainsExplanatoryWarningFlag(Boolean.TRUE);
            }
            if (journey.getDailyCappingFlag().booleanValue()) {
                day.setContainsExplanatoryCappingFlag(Boolean.TRUE);
            }
            if (journey.getAutoCompletionFlag().booleanValue()) {
                day.setContainsExplanatoryAutoCompleteFlag(Boolean.TRUE);
            }
            if (journey.getJourneyDisplay().getManuallyCorrected().booleanValue()) {
                day.setContainsExplanatoryManuallyCorrectedFlag(Boolean.TRUE);
            }
        }
    }
}
