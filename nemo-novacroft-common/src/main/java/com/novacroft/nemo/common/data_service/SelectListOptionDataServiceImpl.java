package com.novacroft.nemo.common.data_service;

import com.novacroft.nemo.common.converter.impl.SelectListOptionConverterImpl;
import com.novacroft.nemo.common.data_access.SelectListOptionDAO;
import com.novacroft.nemo.common.domain.SelectListOption;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Get list options
 */
@Service(value = "selectListOptionDataService")
public class SelectListOptionDataServiceImpl extends BaseDataServiceImpl<SelectListOption, SelectListOptionDTO>
        implements SelectListOptionDataService {
    @Override
    public SelectListOption getNewEntity() {
        return new SelectListOption();
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<SelectListOptionDTO> findOptionsByListId(Long listId) {
        final String hsql = "select o from SelectListOption o where o.selectListId = ? order by o.displayOrder";
        List<SelectListOption> entityResults = dao.findByQuery(hsql, listId);
        List<SelectListOptionDTO> dtoResults = new ArrayList<SelectListOptionDTO>();
        for (SelectListOption entityOption : entityResults) {
            dtoResults.add(this.converter.convertEntityToDto(entityOption));
        }
        return dtoResults;
    }

    /**
     * @deprecated Do not use this method as it implements the hard-coded text anti-pattern!  Displayed text should either from the
     *             content management system or from an application data table.
     */
    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<SelectListOptionDTO> findOptionsWithMeaningsByListId(Long listId) {
        final String hsql = "select o from SelectListOption o where o.selectListId = ? order by o.displayOrder";
        List<SelectListOption> entityResults = dao.findByQuery(hsql, listId);
        List<SelectListOptionDTO> dtoResults = new ArrayList<SelectListOptionDTO>();
        SelectListOptionDTO selectListOptionDTO = null;
        for (SelectListOption entityOption : entityResults) {
            selectListOptionDTO = this.converter.convertEntityToDto(entityOption);
            selectListOptionDTO.setMeaning(entityOption.getValue());
            dtoResults.add(selectListOptionDTO);
        }
        return dtoResults;
    }

    @Autowired
    public void setConverter(SelectListOptionConverterImpl converter) {
        this.converter = converter;
    }

    @Autowired
    public void setDao(SelectListOptionDAO dao) {
        this.dao = dao;
    }

}
