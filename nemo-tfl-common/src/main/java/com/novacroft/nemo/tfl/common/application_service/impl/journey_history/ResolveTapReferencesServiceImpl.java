package com.novacroft.nemo.tfl.common.application_service.impl.journey_history;

import static com.novacroft.nemo.common.utils.DateUtil.formatTimeToMinute;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.application_service.impl.BaseService;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.application_service.journey_history.ResolveTapReferencesService;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.data_service.PseudoTransactionTypeLookupDataService;
import com.novacroft.nemo.tfl.common.transfer.LocationDTO;
import com.novacroft.nemo.tfl.common.transfer.PseudoTransactionTypeLookupDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.TapDTO;
import com.novacroft.nemo.tfl.common.util.JourneyUtil;

/**
 * Translate reference codes/flags to meaningful display descriptions.
 */
@Service("resolveTapReferencesService")
public class ResolveTapReferencesServiceImpl extends BaseService implements ResolveTapReferencesService {

    @Autowired
    protected LocationDataService locationDataService;
    @Autowired
    protected PseudoTransactionTypeLookupDataService pseudoTransactionTypeLookupDataService;

    @Override
    public void resolveReferences(List<TapDTO> taps) {
        for (TapDTO tap : taps) {
            resolveTapReferences(tap);
        }
    }

    protected void resolveTapReferences(TapDTO tap) {
        resolveTransactionLocation(tap);
        resolveTransactionType(tap);
        resolveTransactionTime(tap);
        resolveChargeAmount(tap);
        resolveCreditAmount(tap);
        resolveBusFlag(tap);
        resolveUndergroundFlag(tap);
        resolveRailFlag(tap);
        resolveTopupActivated(tap);
    }

    protected void resolveTransactionLocation(TapDTO tap) {
        if (isKnownLocation(tap.getNationalLocationCode())) {
            tap.getTapDisplay().setNationalLocationName(lookupLocationName(tap.getNationalLocationCode()));
        }
    }

    protected boolean isKnownLocation(Integer location) {
        return (location != null) && (location != -1) && (location != 0);
    }

    protected String lookupLocationName(Integer location) {
        LocationDTO locationDTO = this.locationDataService.findById((long) location);
        return (locationDTO != null) ? locationDTO.getName() : "";
    }

    protected void resolveTransactionTime(TapDTO tap) {
        tap.getTapDisplay().setTransactionTime(formatTimeToMinute(tap.getTransactionAt()));
    }

    protected void resolveTransactionType(TapDTO tap) {
        PseudoTransactionTypeLookupDTO transactionTypeDTO = this.pseudoTransactionTypeLookupDataService.findById((long) tap.getTransactionType());
        tap.getTapDisplay().setTransactionTypeDescription(transactionTypeDTO != null ? transactionTypeDTO.getDisplayDescription() : "");
    }

    protected void resolveChargeAmount(TapDTO tap) {
        if (tap.getAddedStoredValueBalance() != null && tap.getAddedStoredValueBalance() <= 0) {
            tap.getTapDisplay().setChargeAmount(Math.abs(tap.getAddedStoredValueBalance()));
        }
    }

    protected void resolveCreditAmount(TapDTO tap) {
        if (tap.getAddedStoredValueBalance() != null && tap.getAddedStoredValueBalance() > 0) {
            tap.getTapDisplay().setCreditAmount(tap.getAddedStoredValueBalance());
        }
    }

    protected void resolveBusFlag(TapDTO tap) {
        if (!StringUtil.isBlank(tap.getRouteId())
                        && !tap.getRouteId().equals(getContent(ContentCode.BUS_ROUTE_ID_UNDEFINED_IDENTIFER_TEXT.codeStem()))) {
            tap.getTapDisplay().setLocationBusFlag(true);
        }
    }

    protected void resolveUndergroundFlag(TapDTO tap) {
        if (!StringUtil.isBlank(tap.getTapDisplay().getNationalLocationName())
                        && tap.getTapDisplay().getNationalLocationName()
                                        .indexOf(getContent(ContentCode.LONDON_UNDERGROUND_IDENTIFIER_TEXT.codeStem())) != -1) {
            tap.getTapDisplay().setLocationUndergroundFlag(true);
        }
    }

    protected void resolveRailFlag(TapDTO tap) {
        if (!StringUtil.isBlank(tap.getTapDisplay().getNationalLocationName())
                        && tap.getTapDisplay().getNationalLocationName()
                                        .indexOf(getContent(ContentCode.NATIONAL_RAIL_IDENTIFIER_TEXT.codeStem())) != -1) {
            tap.getTapDisplay().setLocationNationalRailFlag(true);
        }
    }

    protected void resolveTopupActivated(TapDTO tap) {
        if (JourneyUtil.isAutoTopUpJourney(tap.getTransactionType())) {
            tap.getTapDisplay().setTopUpActivated(Boolean.TRUE);
        }
    }

}
