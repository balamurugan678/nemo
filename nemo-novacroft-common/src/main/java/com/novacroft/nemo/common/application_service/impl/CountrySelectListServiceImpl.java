package com.novacroft.nemo.common.application_service.impl;

import com.novacroft.nemo.common.application_service.CountrySelectListService;
import com.novacroft.nemo.common.data_service.CountryDataService;
import com.novacroft.nemo.common.transfer.CountryDTO;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ISO Country select list service
 */
@Service("countrySelectListService")
public class CountrySelectListServiceImpl implements CountrySelectListService {
    @Autowired
    protected CountryDataService countryDataService;

    @Override
    public SelectListDTO getSelectList() {
        List<CountryDTO> countryDTOs = this.countryDataService.findAll();
        SelectListDTO selectListDTO = new SelectListDTO();
        selectListDTO.setName("countries");
        selectListDTO.setOptions(new ArrayList<SelectListOptionDTO>());
        for (CountryDTO countryDTO : countryDTOs) {
            selectListDTO.getOptions().add(new SelectListOptionDTO(countryDTO.getCode(), countryDTO.getName()));
        }
        return selectListDTO;
    }
}
