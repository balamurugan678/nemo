package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.constant.DeviceCategoryTypeOfTap;
import com.novacroft.nemo.tfl.common.converter.fare_aggregation_converter.FareAggregationEngineResponseConverter;
import com.novacroft.nemo.tfl.common.data_service.FareAggregationDataService;
import com.novacroft.nemo.tfl.common.service_access.FareAggregationEngineServiceAccess;
import com.novacroft.nemo.tfl.common.transfer.InCompleteJourneyDTO;
import com.novacroft.nemo.tfl.common.transfer.fare_aggregation_engine.RecalculatedOysterChargeResponseDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDayDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.TapDTO;
import com.novacroft.tfl.web_service.model.fair_aggreagation_engine.*;
import com.novacroft.tfl.web_service.model.fair_aggreagation_engine.data.CustomerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.novacroft.nemo.common.utils.OysterCardNumberUtil.getNineDigitNumberAsLong;

@Service
public class FareAggregationDataServiceImpl implements FareAggregationDataService {

    @Autowired
    protected FareAggregationEngineServiceAccess fareAggregationEngineServiceAccess;

    @Autowired
    protected FareAggregationEngineResponseConverter fareAggregationEngineResponseConverter;

    @Override
    public RecalculatedOysterChargeResponseDTO getRecalculatedOysterCharge(JourneyDayDTO journeyDayDTO, String cardNumber) {
        GetRecalculatedOysterChargeResponse calculatedChargeResponse = fareAggregationEngineServiceAccess
                .getRecalculatedOysterCharge(createRequestForChargeRecalculation(journeyDayDTO, cardNumber));
        return fareAggregationEngineResponseConverter.convertModelToDto(calculatedChargeResponse);

    }

    protected GetRecalculatedOysterCharge createRequestForChargeRecalculation(JourneyDayDTO journeyDayDTO, String cardNumber) {
        final ObjectFactory requestFactory = new ObjectFactory();
        final GetRecalculatedOysterCharge reaclculatedOysterChargeRequest = requestFactory.createGetRecalculatedOysterCharge();
        final Card card = requestFactory.createCard();
        card.setPrestigeID(getNineDigitNumberAsLong(cardNumber));
        card.setCustomerType(CustomerType.ADULT);
        Calendar travelDate = Calendar.getInstance();
        travelDate.setTime(journeyDayDTO.getEffectiveTrafficOn());
        card.setTravelDate(DateUtil.convertDateToXMLGregorian(travelDate.getTime()));
        final ArrayOfTap tapArray = requestFactory.createArrayOfTap();
        tapArray.getTap().addAll(createListOfTapsForJourneyDay(journeyDayDTO));

        JAXBElement<ArrayOfTap> tapArrayElement = requestFactory.createCardTaps(tapArray);
        card.setTaps(tapArrayElement);
        JAXBElement<Card> cardElement = requestFactory.createGetRecalculatedOysterChargeCard(card);
        reaclculatedOysterChargeRequest.setCard(cardElement);
        return reaclculatedOysterChargeRequest;
    }

    protected List<Tap> createListOfTapsForJourneyDay(JourneyDayDTO journeyDayDTO) {
        List<Tap> tapsList = new ArrayList<Tap>();
        if (journeyDayDTO != null && journeyDayDTO.getJourneys() != null) {
            final ObjectFactory requestFactory = new ObjectFactory();
            for (JourneyDTO journeyDto : journeyDayDTO.getJourneys()) {
                for (TapDTO tapDTO : journeyDto.getTaps()) {

                    final Tap tap = requestFactory.createTap();
                    if (StringUtil.isNotEmpty(journeyDto.getRouteId())) {
                        tap.setBusRouteId(requestFactory.createTapBusRouteId(journeyDto.getRouteId()));
                    } else {
                        final String deviceCategory =
                                Boolean.TRUE.equals(tapDTO.getTapDisplay().getLocationNationalRailFlag()) ?
                                        DeviceCategoryTypeOfTap.VALIDATOR.name() : DeviceCategoryTypeOfTap.GATE.name();
                        tap.setDeviceCategory(requestFactory.createTapDeviceCategory(deviceCategory));
                        final String nlc =
                                tapDTO.getNationalLocationCode() != null ? tapDTO.getNationalLocationCode().toString() : "";
                        tap.setNLC(requestFactory.createTapNLC(tapDTO.getNationalLocationCode().toString()));
                    }
                    tap.setSequenceNumber(tapDTO.getSequenceNumber());
                    tap.setSequenceRolloverNumber(tapDTO.getRollOverNumber());
                    tap.setSyntheticTapFlag(tapDTO.getSyntheticTap());
                    tap.setTransactionType(tapDTO.getTransactionType());
                    tap.setValidateAt(DateUtil.convertDateToXMLGregorian(tapDTO.getTransactionAt()));
                    tapsList.add(tap);
                }
            }
        }

        return tapsList;
    }

    protected Integer getMissingTransactionType(InCompleteJourneyDTO jouneydto) {

        if (jouneydto.getJourneyNotificationDTO().getLinkedTransactionType() == 61) {
            return 62;
        } else {
            return 61;
        }
    }
}
