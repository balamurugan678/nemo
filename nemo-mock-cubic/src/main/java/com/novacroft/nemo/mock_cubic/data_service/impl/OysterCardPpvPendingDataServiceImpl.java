package com.novacroft.nemo.mock_cubic.data_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.mock_cubic.application_service.UpdateResponseService;
import com.novacroft.nemo.mock_cubic.converter.OysterCardPpvPendingConverterImpl;
import com.novacroft.nemo.mock_cubic.data_access.OysterCardPpvPendingDAO;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardPpvPendingDataService;
import com.novacroft.nemo.mock_cubic.domain.card.OysterCardPpvPending;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPpvPendingDTO;

@Service(value = "oystercardppvpendingDataService")
@Transactional(readOnly = true)
public class OysterCardPpvPendingDataServiceImpl extends BaseDataServiceImpl<OysterCardPpvPending, OysterCardPpvPendingDTO> implements
                OysterCardPpvPendingDataService {

    @Autowired
    UpdateResponseService updateResponseService;

    @Autowired
    public void setDao(OysterCardPpvPendingDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(OysterCardPpvPendingConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public OysterCardPpvPending getNewEntity() {
        return new OysterCardPpvPending();
    }

    @Override
    public List<OysterCardPpvPendingDTO> findAllBy(String test) {
        return null;
    }

    @Override
    public OysterCardPpvPendingDTO findByTest(String test) {
        return null;
    }

    @Override
    public OysterCardPpvPendingDTO findByCardNumber(String cardNumber) {
        final String hsql = "from OysterCardPpvPending c where c.prestigeId = ?";
        OysterCardPpvPending card = dao.findByQueryUniqueResult(hsql, cardNumber);
 
        return (card != null) ? this.converter.convertEntityToDto(card) : null;
    }

    @Override
    public OysterCardPpvPendingDTO findByRequestSequenceNumber(Long originalRequestSequenceNumber) {
        final String hsql = "from OysterCardPpvPending c where c.requestSequenceNumber = ?";
        OysterCardPpvPending result = dao.findByQueryUniqueResult(hsql, originalRequestSequenceNumber);

        return (result != null) ? this.converter.convertEntityToDto(result) : null;
    }

    @Override
    public OysterCardPpvPendingDTO findByCardNumberAndRequestSequenceNumber(String prestigeId, Long originalRequestSequenceNumber) {
        if (originalRequestSequenceNumber == null) {
            return null;
        }

        OysterCardPpvPending exampleEntity = createOysterCardPpvPending();
        exampleEntity.setPrestigeId(prestigeId);
        exampleEntity.setRequestSequenceNumber(originalRequestSequenceNumber);

        OysterCardPpvPending result = dao.findByExampleUniqueResult(exampleEntity);

        return (result != null) ? this.converter.convertEntityToDto(result) : null;
    }

    protected OysterCardPpvPending createOysterCardPpvPending() {
        return new OysterCardPpvPending();
    }
}
