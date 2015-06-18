package com.novacroft.nemo.tfl.services.application_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.application_service.impl.BaseService;
import com.novacroft.nemo.common.domain.cubic.PendingItems;
import com.novacroft.nemo.common.domain.cubic.PrePay;
import com.novacroft.nemo.common.domain.cubic.PrePayTicket;
import com.novacroft.nemo.common.domain.cubic.PrePayValue;
import com.novacroft.nemo.tfl.common.application_service.impl.cubic.GetCardServiceImpl;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.LocationDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;
import com.novacroft.nemo.tfl.services.application_service.StationService;
import com.novacroft.nemo.tfl.services.constant.WebServiceResultAttribute;
import com.novacroft.nemo.tfl.services.converter.StationConverter;
import com.novacroft.nemo.tfl.services.transfer.ErrorResult;
import com.novacroft.nemo.tfl.services.transfer.Station;
import com.novacroft.nemo.tfl.services.util.ErrorUtil;

/**
 * Application service for external stations service
 */
@Service("stationService")
public class StationServiceImpl extends BaseService implements StationService {
    @Autowired
    protected LocationDataService locationDataService;
    @Autowired
    protected StationConverter stationConverter;
    @Autowired
    protected GetCardServiceImpl getCardService;
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected CustomerDataService customerDataService;

    @Override
    public List<Station> getStations() {
        return this.stationConverter.convert(this.locationDataService.findAll());
    }

    @Override
    public List<Station> getActiveStations() {
        return this.stationConverter.convert(this.locationDataService.findAllActiveLocations());
    }

    /**
     * Note: as card id is sufficient to identify a particular card object, the customer id is kept but not currently used
     */
    @Override
    public Station findStationForOutstandingOrder(Long externalCustomerId, Long externalCardId) {
        try {
            Long internalCustomerId = customerDataService.getInternalIdFromExternalId(externalCustomerId);
            if (internalCustomerId == null) {
                return new Station(ErrorUtil.addErrorToList(new ErrorResult(), null, WebServiceResultAttribute.CUSTOMER_NOT_FOUND.name(),
                                getContent(WebServiceResultAttribute.CUSTOMER_NOT_FOUND.contentCode())));
            }
            CardDTO cardDTO = cardDataService.findByCustomerIdAndExternalId(internalCustomerId, externalCardId);
            if (cardDTO != null 
                            && cardDTO.getCardNumber() != null) {
                if (isCardDTOHasPendingItems(cardDTO.getCardNumber())) {
                    PendingItems pendingItems = getCardService.getCard(cardDTO.getCardNumber()).getPendingItems();
                    LocationDTO location = null;
                    Integer stationId = null;
                    if (isCardHasPptItems(pendingItems)) {
                        List<PrePayTicket> pptItems = pendingItems.getPpts();
                        stationId = findStationId(pptItems);
                    } else {
                        List<PrePayValue> ppvItems = pendingItems.getPpvs();
                        stationId = findStationId(ppvItems);
                    }
                    location = locationDataService.getActiveLocationById(stationId);
                    return new Station(location.getId(), location.getName(), location.getStatus());
                } else {
                    return new Station();
                }
            } else {
                return new Station(ErrorUtil.addErrorToList(new ErrorResult(), null, WebServiceResultAttribute.CARD_NOT_FOUND.name(),
                                getContent(WebServiceResultAttribute.CARD_NOT_FOUND.contentCode())));
            }
        } catch (Exception e) {
            return new Station(ErrorUtil.addErrorToList(new ErrorResult(), null, WebServiceResultAttribute.FAILURE.name(), e.getMessage()));
        }
    }

    protected boolean isCardDTOHasPendingItems(String cardNumber) {
        CardInfoResponseV2DTO card = getCardService.getCard(cardNumber);
        if (isCardHasAnyPendingItems(card) && (isCardHasPptItems(card.getPendingItems()) || isCardHasPpvItems(card.getPendingItems()))) {
            return true;
        }
        return false;
    }

    protected boolean isCardHasAnyPendingItems(CardInfoResponseV2DTO card) {
        return card.getPendingItems() != null;
    }

    protected boolean isCardHasPptItems(PendingItems pendingItems) {
        return !pendingItems.getPpts().isEmpty();
    }

    protected boolean isCardHasPpvItems(PendingItems pendingItems) {
        return !pendingItems.getPpvs().isEmpty();
    }

    @SuppressWarnings("unchecked")
    protected Integer findStationId(List<?> items) {
        List<PrePay> ppItems = (List<PrePay>) items;
        Integer stationId = null;
        for (PrePay item : ppItems) {
            if (item.getPickupLocation() != null) {
                stationId = item.getPickupLocation();
                break;
            }
        }
        return stationId;
    }

}
