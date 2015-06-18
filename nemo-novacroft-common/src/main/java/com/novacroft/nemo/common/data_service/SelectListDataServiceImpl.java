package com.novacroft.nemo.common.data_service;

import com.novacroft.nemo.common.constant.CommonPrivateError;
import com.novacroft.nemo.common.converter.impl.SelectListConverterImpl;
import com.novacroft.nemo.common.data_access.SelectListDAO;
import com.novacroft.nemo.common.domain.SelectList;
import com.novacroft.nemo.common.exception.DataServiceException;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Select list (list of values) data service implementation
 */
@Service(value = "selectListDataService")
public class SelectListDataServiceImpl extends BaseDataServiceImpl<SelectList, SelectListDTO> implements SelectListDataService {
    static final Logger logger = LoggerFactory.getLogger(SelectListDataService.class);

    @Override
    @Transactional(readOnly = true)
    public SelectListDTO findByName(String name) {
        SelectList exampleSelectList = new SelectList();
        exampleSelectList.setName(name);
        List<SelectList> results = dao.findByExample(exampleSelectList);
        if (results.size() > 1) {
            String msg = String.format(CommonPrivateError.MORE_THAN_ONE_RECORD_FOR_NAME.message(), name);
            logger.error(msg);
            throw new DataServiceException(msg);
        }
        if (results.iterator().hasNext()) {
            return this.converter.convertEntityToDto(results.iterator().next());
        } 
        return null;
    }

    @Autowired
    public void setConverter(SelectListConverterImpl converter) {
        this.converter = converter;
    }

    @Autowired
    public void setDao(SelectListDAO dao) {
        this.dao = dao;
    }

    @Override
    public SelectList getNewEntity() {
        return new SelectList();
    }
}
