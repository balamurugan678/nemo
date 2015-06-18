package com.novacroft.nemo.tfl.common.data_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.AdministrationFeeItemConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.AdministrationFeeItemDAO;
import com.novacroft.nemo.tfl.common.data_service.AdministrationFeeItemDataService;
import com.novacroft.nemo.tfl.common.domain.AdministrationFeeItem;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeItemDTO;

/**
 * Administration fee item data service implementation
 */
@Deprecated
@Service(value = "administrationFeeItemDataService")
@Transactional(readOnly = true)
public class AdministrationFeeItemDataServiceImpl extends BaseDataServiceImpl<AdministrationFeeItem, AdministrationFeeItemDTO>
        implements AdministrationFeeItemDataService {
    @Autowired
    public void setDao(AdministrationFeeItemDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(AdministrationFeeItemConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public AdministrationFeeItem getNewEntity() {
        return new AdministrationFeeItem();
    }

    @Override
    public AdministrationFeeItemDTO findByCartIdAndCardId(Long cartId, Long cardId) {
        if (cartId != null && cardId != null) {
            AdministrationFeeItem administrationFeeItem = new AdministrationFeeItem();
            administrationFeeItem.setCardId(cardId);
            administrationFeeItem = dao.findByExampleUniqueResult(administrationFeeItem);
            if (administrationFeeItem != null) {
                return converter.convertEntityToDto(administrationFeeItem);
            }
        }
        return null;
    }

    @Override
    public List<AdministrationFeeItemDTO> findByCustomerOrderId(Long customerOrderId) {
        AdministrationFeeItem administrationFeeItem = new AdministrationFeeItem();
        administrationFeeItem.setOrderId(customerOrderId);
        List<AdministrationFeeItem> items = dao.findByExample(administrationFeeItem);
        return convert(items);
    }
    
    @Override
    public AdministrationFeeItemDTO findByCardId(Long cardId) {
        if (cardId != null) {
            AdministrationFeeItem administrationFeeItem = new AdministrationFeeItem();
            administrationFeeItem.setCardId(cardId);
            administrationFeeItem = dao.findByExampleUniqueResult(administrationFeeItem);
            if (administrationFeeItem != null) {
                return converter.convertEntityToDto(administrationFeeItem);
            }
        }
        return null;
    }
}
