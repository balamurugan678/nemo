package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.utils.DateUtil.isAfterOrEqual;
import static com.novacroft.nemo.common.utils.DateUtil.isBeforeOrEqual;
import static com.novacroft.nemo.common.utils.DateUtil.parse;
import static com.novacroft.nemo.tfl.common.util.ZoneUtil.isZoneEquals;
import static com.novacroft.nemo.tfl.common.util.ZoneUtil.isZoneGreaterThanEquals;
import static com.novacroft.nemo.tfl.common.util.ZoneUtil.isZoneLessThanEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import com.novacroft.nemo.tfl.common.application_service.ProductService;
import com.novacroft.nemo.tfl.common.application_service.ZoneService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageSelectList;
import com.novacroft.nemo.tfl.common.constant.ProductItemType;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

/**
 * Zone service implementation
 */
@Service("zoneService")
public class ZoneServiceImpl implements ZoneService {
    
    @Autowired
    protected ProductService productService;
    @Autowired
    protected SelectListService selectListService;
    @Autowired
    protected MessageSource messageSource;

    @Override
    public boolean isZonesOverlapWithTravelCardZones(CartItemCmdImpl cartItem, Date startDate, Date endDate, Integer startZone, Integer endZone) {
        return (isZoneOverlap(startZone, endZone, cartItem.getStartZone(), cartItem.getEndZone()) ? 
                        isDateOverlap(startDate, endDate,parse(cartItem.getStartDate()), parse(cartItem.getEndDate())) : false);
    }

    @Override
    public boolean isZonesOverlapWithProductItemDTOZones(Integer startZone, Integer endZone, ProductItemDTO productItemDTO, Date startDate,
                    Date endDate) {
        return (isZoneOverlap(startZone, endZone, productItemDTO.getStartZone(), productItemDTO.getEndZone()) ?
             isDateOverlap(startDate, endDate, productItemDTO.getStartDate(), productItemDTO.getEndDate()): false);
    }

    @Override
    public SelectListDTO getAvailableZonesForTravelCardTypeAndStartZone(String pageListName, Integer startZone, String travelCardType) {
        final SelectListDTO selectListDTO = selectListService.getSelectList(PageSelectList.TRAVEL_CARD_ZONES);
        final  List<Integer> allowedEndZonesList = findAvilableEndZonesForProductAndStartZone(startZone, travelCardType);
        filterEndZonesNotAvailable(selectListDTO, allowedEndZonesList);
        setUpMeaningForOptions(pageListName, selectListDTO);
        return selectListDTO;
    }

    protected boolean isZoneOverlap(Integer startZone1, Integer endZone1, Integer startZone2, Integer endZone2) {
        return isSameZoneOverlap(startZone1, endZone1, startZone2, endZone2) || isStartZoneOverlap(startZone1, endZone1, startZone2, endZone2)
                        || isEndZoneOverlap(startZone1, endZone1, startZone2, endZone2)
                        || isInBetweenZoneOverlap(startZone1, endZone1, startZone2, endZone2)
                        || isSurroundingZoneOverlap(startZone1, endZone1, startZone2, endZone2);
    }

    protected boolean isDateOverlap(Date startDate1, Date endDate1, Date startDate2, Date endDate2) {
        return isStartDateOverlap(startDate1, endDate1, startDate2, endDate2) || isEndDateOverlap(startDate1, endDate1, startDate2, endDate2)
                        || isInBetweenDateOverlap(startDate1, endDate1, startDate2, endDate2)
                        || isSurroundingDateOverlap(startDate1, endDate1, startDate2, endDate2);
    }

    protected boolean isSurroundingZoneOverlap(Integer startZone1, Integer endZone1, Integer startZone2, Integer endZone2) {
        return isZoneLessThanEquals(startZone2, startZone1) && isZoneGreaterThanEquals(endZone2, endZone1);
    }

    protected boolean isSameZoneOverlap(Integer startZone1, Integer endZone1, Integer startZone2, Integer endZone2) {
        return isZoneEquals(startZone1, startZone2) && isZoneEquals(endZone1, endZone2);
    }

    protected boolean isStartZoneOverlap(Integer startZone1, Integer endZone1, Integer startZone2, Integer endZone2) {
        return isZoneLessThanEquals(startZone2, startZone1) && isZoneGreaterThanEquals(endZone2, startZone1)
                        && isZoneLessThanEquals(endZone2, endZone1);
    }

    protected boolean isEndZoneOverlap(Integer startZone1, Integer endZone1, Integer startZone2, Integer endZone2) {
        return isZoneGreaterThanEquals(startZone2, startZone1) && isZoneLessThanEquals(startZone2, endZone1)
                        && isZoneGreaterThanEquals(endZone2, endZone1);
    }

    protected boolean isInBetweenZoneOverlap(Integer startZone1, Integer endZone1, Integer startZone2, Integer endZone2) {
        return isZoneLessThanEquals(startZone1, startZone2) && isZoneLessThanEquals(endZone2, endZone1);
    }

    protected boolean isSurroundingDateOverlap(Date startDate1, Date endDate1, Date startDate2, Date endDate2) {
        return isBeforeOrEqual(startDate2, startDate1) && isAfterOrEqual(endDate2, endDate1);
    }

    protected boolean isStartDateOverlap(Date startDate1, Date endDate1, Date startDate2, Date endDate2) {
        return isBeforeOrEqual(startDate2, startDate1) && isBeforeOrEqual(endDate2, endDate1) && isAfterOrEqual(endDate2, startDate1);
    }

    protected boolean isEndDateOverlap(Date startDate1, Date endDate1, Date startDate2, Date endDate2) {
        return isAfterOrEqual(startDate2, startDate1) && isAfterOrEqual(endDate2, endDate1) && isBeforeOrEqual(startDate2, endDate1);
    }

    protected boolean isInBetweenDateOverlap(Date startDate1, Date endDate1, Date startDate2, Date endDate2) {
        return isAfterOrEqual(startDate2, startDate1) && isBeforeOrEqual(endDate2, endDate1);
    }
    
    protected List<SelectListOptionDTO> setUpMeaningForOptions(String prefix, SelectListDTO selectListDTO){
        if (null != selectListDTO) {
            for (SelectListOptionDTO optionDTO : selectListDTO.getOptions()) {
                final String contentValue = messageSource.getMessage(prefix + "." + optionDTO.getValue().toLowerCase()  + ".option", new Object[] {}, null);
                optionDTO.setMeaning(contentValue);
                }
            }
        
        return selectListDTO != null ? selectListDTO.getOptions() : null;
    }
    
    protected List<SelectListOptionDTO> filterEndZonesNotAvailable(SelectListDTO selectListDTO, List<Integer> allowedEndZonesList) {
        if(allowedEndZonesList != null)
        {
            for(ListIterator<SelectListOptionDTO> optionIterator = selectListDTO.getOptions().listIterator() ; optionIterator.hasNext() ;) {
                SelectListOptionDTO selectListOptionDTO = optionIterator.next();
                if(!allowedEndZonesList.contains(Integer.parseInt(selectListOptionDTO.getValue()))){
                    optionIterator.remove();
                }
            }
        }
        return selectListDTO.getOptions();
    }
    
    protected List<Integer> findAvilableEndZonesForProductAndStartZone(Integer startZone, String travelCardType) {
        List<ProductDTO> productsDtoList = null;
        if (Durations.OTHER.getDurationType().equals(travelCardType)) {
            //TODO Fix to cater for both travcelcard and bus pass
            productsDtoList = productService.getProductsByStartZoneForOddPeriod(startZone, ProductItemType.TRAVEL_CARD.databaseCode());
        } else {
            productsDtoList = productService.getProductsByLikeDurationAndStartZone(travelCardType, startZone);
        }
        List<Integer> zonesList = new ArrayList<>();
        if (null != productsDtoList) {
            for (ProductDTO productDTO : productsDtoList) {
                zonesList.add(productDTO.getEndZone());
            }
        }
        return zonesList; 
    }

}
