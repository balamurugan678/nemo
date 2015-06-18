package com.novacroft.nemo.common.application_service.impl;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.data_service.SelectListDataService;
import com.novacroft.nemo.common.data_service.SelectListOptionDataService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Select list (list of values) service implementation
 */
@Service(value = "selectListService")
public class SelectListServiceImpl implements SelectListService {
    @Autowired
    protected SelectListDataService selectListDataService;
    @Autowired
    protected SelectListOptionDataService selectListOptionDataService;

    @Override
    public SelectListDTO getSelectList(String name) {
        SelectListDTO selectList = selectListDataService.findByName(name);
        List<SelectListOptionDTO> options = selectListOptionDataService.findOptionsByListId(selectList.getId());
        selectList.setOptions(options);
        return selectList;
    }

    @Override
    public List<SelectListOptionDTO> getSelectListOptions(String name) {
        SelectListDTO selectList = selectListDataService.findByName(name);
        return selectListOptionDataService.findOptionsByListId(selectList.getId());
    }
}
