package com.novacroft.nemo.tfl.common.converter.impl.journey_history;

import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.converter.journey_history.JourneyHistoryTapConverter;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.TapDTO;
import com.novacroft.tfl.web_service.model.oyster_journey_history.Tap;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Component;

/**
 * Journey history tap transfer class / web service model converter.
 */
@Component("journeyHistoryTapConverter")
public class JourneyHistoryTapConverterImpl implements JourneyHistoryTapConverter {
    @Override
    public TapDTO convertModelToDto(Tap tap) {
        return new TapDTO(tap.getAddedsvbalance().getValue(), tap.getAgentno().getValue(), tap.getInnerzone().getValue(),
                tap.getJourneyidDd(), tap.getNarrativeDd().getValue(), tap.getNlc().getValue(), tap.getOuterzone().getValue(),
                tap.getPaymentmethodcode().getValue(), tap.getRollovernumber().getValue(), tap.getRouteid().getValue(),
                tap.getSequenceno(), BooleanUtils.toBoolean(tap.getSuppresscode().getValue()), tap.getSvbalance().getValue(),
                tap.isSynthetictap(), DateUtil.convertXMLGregorianToDate(tap.getTransactiontime().getValue()),
                tap.getTransactiontype());
    }
}
