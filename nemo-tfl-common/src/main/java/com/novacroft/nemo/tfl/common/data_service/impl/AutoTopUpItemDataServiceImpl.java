package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.AutoTopUpConfigurationItemConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.AutoTopUpItemDAO;
import com.novacroft.nemo.tfl.common.data_service.AutoTopUpItemDataService;
import com.novacroft.nemo.tfl.common.domain.AutoTopUpConfigurationItem;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Auto top-up item data service implementation
 */
@Service(value = "autoTopUpItemDataService")
@Transactional(readOnly = true)
public class AutoTopUpItemDataServiceImpl extends BaseDataServiceImpl<AutoTopUpConfigurationItem, AutoTopUpConfigurationItemDTO>
        implements AutoTopUpItemDataService {
    @Autowired
    public void setDao(AutoTopUpItemDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(AutoTopUpConfigurationItemConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public AutoTopUpConfigurationItem getNewEntity() {
        return new AutoTopUpConfigurationItem();
    }

    @Override
    public AutoTopUpConfigurationItemDTO findByCartIdAndCardId(Long cartId, Long cardId) {
        assert (cartId != null && cardId != null);
        AutoTopUpConfigurationItem autoTopUpItem = new AutoTopUpConfigurationItem();
        autoTopUpItem.setCardId(cardId);
        autoTopUpItem = dao.findByExampleUniqueResult(autoTopUpItem);
        return (autoTopUpItem != null) ? converter.convertEntityToDto(autoTopUpItem) : null;
    }

    @Override
    public List<AutoTopUpConfigurationItemDTO> findByCustomerOrderId(Long customerOrderId) {
        AutoTopUpConfigurationItem autoTopUpItem = new AutoTopUpConfigurationItem();
        autoTopUpItem.setOrderId(customerOrderId);
        List<AutoTopUpConfigurationItem> items = dao.findByExample(autoTopUpItem);
        return convert(items);
    }

}
