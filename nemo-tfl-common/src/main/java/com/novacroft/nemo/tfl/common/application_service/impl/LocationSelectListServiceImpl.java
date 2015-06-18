package com.novacroft.nemo.tfl.common.application_service.impl;

import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import com.novacroft.nemo.tfl.common.application_service.LocationSelectListService;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.transfer.LocationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Location select list (station/NLC list of values) service specification
 */
@Service("locationSelectListService")
public class LocationSelectListServiceImpl implements LocationSelectListService {
    @Autowired
    protected LocationDataService locationDataService;
    @SuppressWarnings("rawtypes")
    @Autowired
    protected Comparator selectListOptionMeaningComparator;

    @SuppressWarnings("unchecked")
    @Override
    public SelectListDTO getLocationSelectList() {
        List<LocationDTO> locationDTOs = this.locationDataService.findAllActiveLocations();
        SelectListDTO selectListDTO = new SelectListDTO();
        selectListDTO.setName("Locations");
        selectListDTO.setOptions(new ArrayList<SelectListOptionDTO>());
        for (LocationDTO locationDTO : locationDTOs) {
            selectListDTO.getOptions().add(new SelectListOptionDTO(locationDTO.getId().toString(), locationDTO.getName()));
        }
        Collections.sort(selectListDTO.getOptions(), this.selectListOptionMeaningComparator);
        return selectListDTO;
    }
}
