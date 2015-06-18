package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.AdministrationFeeConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.AdministrationFeeDAO;
import com.novacroft.nemo.tfl.common.data_service.AdministrationFeeDataService;
import com.novacroft.nemo.tfl.common.domain.AdministrationFee;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Administration Fee data service implementation
 */
@Service(value = "administrationFeeDataService")
@Transactional(readOnly = true)
public class AdministrationFeeDataServiceImpl extends BaseDataServiceImpl<AdministrationFee, AdministrationFeeDTO> implements AdministrationFeeDataService {
    @Autowired
    public void setDao(AdministrationFeeDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(AdministrationFeeConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public AdministrationFee getNewEntity() {
        return new AdministrationFee();
    }

    @Override
    public AdministrationFeeDTO findByPrice(Integer price) {
        if (price >= 0) {
            AdministrationFee administrationFee = findAdministrationFee(price);
            if (administrationFee == null) {
                administrationFee = findZeroValueAdministrationFee();
            }
            return convertAdministrationFeeToDTO(administrationFee);
        }
        return null;
    }

    public AdministrationFee findAdministrationFee(Integer price) {
        final String hsql = "from AdministrationFee adminfee where adminfee.price = ?";
        return dao.findByQueryUniqueResult(hsql, price);
    }

    public AdministrationFee findZeroValueAdministrationFee() {
        final String hsql = "from AdministrationFee adminfee where adminfee.price = ?";
        return dao.findByQueryUniqueResult(hsql, 0);
    }

    public AdministrationFeeDTO convertAdministrationFeeToDTO(AdministrationFee administrationFee) {
        return this.converter.convertEntityToDto(administrationFee);
    }

    @Override
    public AdministrationFeeDTO findByType(String type) {
        AdministrationFee administrationFee = findAdministrationFee(type);
        if (administrationFee != null) {
            return convertAdministrationFeeToDTO(administrationFee);
        }
        return null;
    }

    public AdministrationFee findAdministrationFee(String type) {
        final String hsql = "from AdministrationFee adminfee where adminfee.type = ?";
        return dao.findByQueryUniqueResult(hsql, type);
    }



}
