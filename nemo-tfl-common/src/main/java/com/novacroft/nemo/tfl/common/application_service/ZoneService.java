package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

import java.util.Date;

/**
 * Specification for zone service
 */
public interface ZoneService {

    boolean isZonesOverlapWithTravelCardZones(CartItemCmdImpl cartItem, Date startDate, Date endDate, Integer startZone,
                                              Integer endZone);

    boolean isZonesOverlapWithProductItemDTOZones(Integer startZone, Integer endZone, ProductItemDTO productItemDTO,
                                                  Date startDate, Date endDate);

    SelectListDTO getAvailableZonesForTravelCardTypeAndStartZone(String pageListName, Integer startZone, String travelCardType);

}
