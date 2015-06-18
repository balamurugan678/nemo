package com.novacroft.nemo.tfl.common.data_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.GoodwillPaymentItemConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.GoodwillPaymentItemDAO;
import com.novacroft.nemo.tfl.common.data_service.GoodwillPaymentItemDataService;
import com.novacroft.nemo.tfl.common.domain.GoodwillPaymentItem;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;

@Service(value = "goodwillPaymentItemDataServiceImpl")
@Transactional(readOnly = true)
public class GoodwillPaymentItemDataServiceImpl extends BaseDataServiceImpl<GoodwillPaymentItem, GoodwillPaymentItemDTO> implements GoodwillPaymentItemDataService {

    @Autowired
    public void setDao(GoodwillPaymentItemDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(GoodwillPaymentItemConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public GoodwillPaymentItem getNewEntity() {

        return new GoodwillPaymentItem();
    }

    @Override
    public GoodwillPaymentItem findById(String id) {
        final String hsql = "from Goodwillreason goodwill where goodwill.reasonid = ?";
        return dao.findByQueryUniqueResult(hsql, id);

    }

    @Override
    public GoodwillPaymentItem findByCartIdAndCardId(Long cartId, Long cardId) {
        final String hsql = "from Item I where I.cartId = ? and I.cardId = ?";
        return dao.findByQueryUniqueResult(hsql, cartId, cardId);
    }

    @Override
    public List<GoodwillPaymentItemDTO> findAllByCartIdAndCardId(Long cartId, Long cardId) {
        assert (cartId != null && cardId != null);
        GoodwillPaymentItem goodwillPayment = new GoodwillPaymentItem();
        List<GoodwillPaymentItem> goodwillItems = dao.findByExample(goodwillPayment);
        return convert(goodwillItems);
    }

    @Override
    public List<GoodwillPaymentItemDTO> findAllByCartIdAndCustomerId(Long cartId, Long customerId) {
        assert (cartId != null && customerId != null);
        GoodwillPaymentItem goodwillPayment = new GoodwillPaymentItem();
        goodwillPayment.setCustomerId(customerId);
        List<GoodwillPaymentItem> goodwillItems = dao.findByExample(goodwillPayment);
        return convert(goodwillItems);

    }
}
