package com.novacroft.nemo.tfl.common.converter.impl.journey_history;

import static com.novacroft.nemo.common.utils.CurrencyUtil.formatPenceWithoutCurrencySymbol;
import static com.novacroft.nemo.common.utils.DateUtil.convertXMLGregorianToDate;
import static com.novacroft.nemo.common.utils.DateUtil.formatDate;
import static com.novacroft.nemo.tfl.common.util.JourneyUtil.NO_TOUCH_IN;
import static com.novacroft.nemo.tfl.common.util.JourneyUtil.NO_TOUCH_OUT;
import static com.novacroft.nemo.tfl.common.util.JourneyUtil.isAutoCompleted;
import static com.novacroft.nemo.tfl.common.util.JourneyUtil.isDailyCapped;
import static com.novacroft.nemo.tfl.common.util.JourneyUtil.isJourneyUnFinished;
import static com.novacroft.nemo.tfl.common.util.JourneyUtil.isJourneyUnStarted;
import static com.novacroft.nemo.tfl.common.util.JourneyUtil.isPayAsYouGoUsed;
import static com.novacroft.nemo.tfl.common.util.JourneyUtil.isTfrDiscountsApplied;
import static com.novacroft.nemo.tfl.common.util.JourneyUtil.isTravelCardUsed;
import static org.apache.commons.lang3.BooleanUtils.toBoolean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.application_service.impl.BaseService;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.converter.journey_history.JourneyHistoryJourneyConverter;
import com.novacroft.nemo.tfl.common.converter.journey_history.JourneyHistoryTapConverter;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.TapDTO;
import com.novacroft.tfl.web_service.model.oyster_journey_history.ArrayOfTap;
import com.novacroft.tfl.web_service.model.oyster_journey_history.Journey;
import com.novacroft.tfl.web_service.model.oyster_journey_history.Tap;

/**
 * Journey history journey transfer class / web service model converter.
 */
@Component("journeyHistoryJourneyConverter")
public class JourneyHistoryJourneyConverterImpl extends BaseService implements JourneyHistoryJourneyConverter {
    @Autowired
    protected JourneyHistoryTapConverter journeyHistoryTapConverter;

    @Override
    public JourneyDTO convertModelToDto(Journey journey) {
        return new JourneyDTO(journey.getAddedsvbalance().getValue(), journey.getAgentno().getValue(),
                isAutoCompleted(journey.getAutocompletionflag()), journey.getBestvalueactualfare().getValue(),
                journey.getBestvaluecappingscheme().getValue(), journey.getBusoffpeakruntot().getValue(),
                journey.getBuspeakruntot().getValue(), journey.getCarddiscountid().getValue(),
                journey.getCardpassengertypeid().getValue(), journey.getCardtype().getValue(),
                journey.getChargedesc().getValue(), journey.getConcessionnarrative().getValue(),
                isDailyCapped(journey.getDailycappingflag()), journey.getDiscountedfare().getValue(),
                journey.getDiscountnarrative().getValue(), convertXMLGregorianToDate(journey.getExdatetime().getValue()),
                journey.getExlocation().getValue(), journey.getJourneyid(), journey.getInnerzone().getValue(),
                journey.getModeoftravel().getValue(), journey.getNarrativeDd().getValue(), journey.getOffpeakcap().getValue(),
                journey.getOsi().getValue(), journey.getOuterzone().getValue(), isPayAsYouGoUsed(journey.getPaygused()),
                journey.getPaymentmethodcode().getValue(), journey.getPeakcap().getValue(), journey.getPeakfare().getValue(),
                journey.getPrestigeid(), journey.getProductcode().getValue(),
                convertXMLGregorianToDate(journey.getProductexpirydate().getValue()),
                convertXMLGregorianToDate(journey.getProductstartdate().getValue()),
                journey.getProducttimevaliditycode().getValue(), journey.getProductzonalvaliditycode().getValue(),
                journey.getPttid(), journey.getRailoffpeakmaxOutzone().getValue(),
                journey.getRailoffpeakminInnzone().getValue(), journey.getRailoffpeakruntot().getValue(),
                journey.getRailpeakmaxOutzone().getValue(), journey.getRailpeakminInnzone().getValue(),
                journey.getRailpeakruntot().getValue(), journey.getRouteid().getValue(), journey.getRunningtotal().getValue(),
                toBoolean(journey.getSuppresscode()), journey.getSvbalance().getValue(),
                convertTaps(journey.getTaps().getValue()), isTfrDiscountsApplied(journey.getTfrdiscountsappliedflag()),
                convertXMLGregorianToDate(journey.getTrafficdate()), isTravelCardUsed(journey.getTravelcardused()),
                journey.getTraveltype().getValue(), convertXMLGregorianToDate(journey.getTxndatetime().getValue()),
                journey.getTxnlocation().getValue(), journey.getValidtickettonCMS().getValue(),
                journey.getZonalindicator().getValue());
    }

    @Override
    public String[] convertDtoToRecord(JourneyDTO journeyDTO) {
        return new String[]{getJourneyDate(journeyDTO), getJourneyStartTime(journeyDTO), getJourneyEndTime(journeyDTO),
                getJourneyDescription(journeyDTO), getChargeValue(journeyDTO), getCreditValue(journeyDTO),
                getBalanceValue(journeyDTO), getNote(journeyDTO)};
    }

    protected List<TapDTO> convertTaps(ArrayOfTap arrayOfTap) {
        if (arrayOfTap == null) {
            return null;
        }
        List<TapDTO> tapDTOs = new ArrayList<TapDTO>();
        for (Tap tap : arrayOfTap.getTap()) {
            tapDTOs.add(convertTap(tap));
        }
        return tapDTOs;
    }

    protected TapDTO convertTap(Tap tap) {
        return this.journeyHistoryTapConverter.convertModelToDto(tap);
    }

    protected String getJourneyDate(JourneyDTO journeyDTO) {
        return formatDate(journeyDTO.getJourneyDisplay().getEffectiveTrafficOn());
    }

    protected String getJourneyStartTime(JourneyDTO journeyDTO) {
        return journeyDTO.getJourneyDisplay().getJourneyStartTime();
    }

    protected String getJourneyEndTime(JourneyDTO journeyDTO) {
        return journeyDTO.getJourneyDisplay().getJourneyEndTime();
    }

    protected String getJourneyDescription(JourneyDTO journey) {
        if (isJourneyUnStarted(journey) && journey.getJourneyDisplay() != null) {
            journey.getJourneyDisplay().setJourneyDescription(NO_TOUCH_IN + journey.getJourneyDisplay().getExitLocationName());
        } else if(isJourneyUnFinished(journey) && journey.getJourneyDisplay() != null){
            journey.getJourneyDisplay().setJourneyDescription(NO_TOUCH_OUT + journey.getJourneyDisplay().getTransactionLocationName());        
        }
        return journey.getJourneyDisplay().getJourneyDescription();
    }
    
    protected String getChargeValue(JourneyDTO journeyDTO) {
        return formatPenceWithoutCurrencySymbol(journeyDTO.getJourneyDisplay().getChargeAmount());
    }

    protected String getCreditValue(JourneyDTO journeyDTO) {
        return formatPenceWithoutCurrencySymbol(journeyDTO.getJourneyDisplay().getCreditAmount());
    }

    protected String getBalanceValue(JourneyDTO journeyDTO) {
        return formatPenceWithoutCurrencySymbol(journeyDTO.getStoredValueBalance());
    }

    protected String getNote(JourneyDTO journeyDTO) {
        if (journeyDTO.getJourneyDisplay().getWarning()) {
            return journeyDTO.getJourneyDisplay().getPseudoTransactionTypeDisplayDescription();
        }
        if (journeyDTO.getDailyCappingFlag()) {
            return getContent(ContentCode.JOURNEY_CAPPED.tipCode());
        }
        if (journeyDTO.getAutoCompletionFlag()) {
            return getContent(ContentCode.JOURNEY_AUTO_COMPLETED.tipCode());
        }
        if (journeyDTO.getJourneyDisplay().getManuallyCorrected()) {
            return getContent(ContentCode.JOURNEY_MANUALLY_CORRECTED.tipCode());
        }
        return "";
    }
}
