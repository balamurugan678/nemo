package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.PayAsYouGoItemConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.PayAsYouGoItemDAO;
import com.novacroft.nemo.tfl.common.data_service.PayAsYouGoItemDataService;
import com.novacroft.nemo.tfl.common.domain.PayAsYouGoItem;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Pay as you go item data service implementation
 */
@Service(value = "payAsYouGoItemDataService")
@Transactional(readOnly = true)
public class PayAsYouGoItemDataServiceImpl extends BaseDataServiceImpl<PayAsYouGoItem, PayAsYouGoItemDTO>
        implements PayAsYouGoItemDataService {
    @Autowired
    public void setDao(PayAsYouGoItemDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(PayAsYouGoItemConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public PayAsYouGoItem getNewEntity() {
        return new PayAsYouGoItem();
    }

    @Override
    public PayAsYouGoItemDTO findByCartIdAndCardId(Long cartId, Long cardId) {
        if (cartId != null && cardId != null) {
            PayAsYouGoItem payAsYouGoItem = new PayAsYouGoItem();
            payAsYouGoItem.setCardId(cardId);
            payAsYouGoItem = dao.findByExampleUniqueResult(payAsYouGoItem);
            if (payAsYouGoItem != null) {
                return converter.convertEntityToDto(payAsYouGoItem);
            }
        }
        return null;
    }

    @Override
    public List<PayAsYouGoItemDTO> findByCustomerOrderId(Long customerOrderId) {
        PayAsYouGoItem payAsYouGoItem = new PayAsYouGoItem();
        payAsYouGoItem.setOrderId(customerOrderId);
        List<PayAsYouGoItem> items = dao.findByExample(payAsYouGoItem);
        return convert(items);
    }

}
