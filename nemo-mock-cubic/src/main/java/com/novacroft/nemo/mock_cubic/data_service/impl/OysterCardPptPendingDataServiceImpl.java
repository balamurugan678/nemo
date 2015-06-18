package com.novacroft.nemo.mock_cubic.data_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.mock_cubic.converter.OysterCardPptPendingConverterImpl;
import com.novacroft.nemo.mock_cubic.data_access.OysterCardPptPendingDAO;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardPptPendingDataService;
import com.novacroft.nemo.mock_cubic.domain.card.OysterCardPptPending;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPptPendingDTO;

import static com.novacroft.nemo.common.utils.StringUtil.isNotBlank;

@Service(value = "oysterCardPptPendingDataService")
@Transactional(readOnly = true)
public class OysterCardPptPendingDataServiceImpl extends BaseDataServiceImpl<OysterCardPptPending, OysterCardPptPendingDTO> implements
                OysterCardPptPendingDataService {

    @Autowired
    public void setDao(OysterCardPptPendingDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(OysterCardPptPendingConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public OysterCardPptPending getNewEntity() {
        return new OysterCardPptPending();
    }

    @Override
    public List<OysterCardPptPendingDTO> findAllBy(String test) {
        return null;
    }

    @Override
    public OysterCardPptPendingDTO findByTest(String test) {
        return null;
    }

    @Override
    public OysterCardPptPendingDTO findByCardNumber(String cardNumber) {
        final String hsql = "from OysterCardPptPending c where c.prestigeId = ?";
        OysterCardPptPending card = dao.findByQueryUniqueResult(hsql, cardNumber);
        if (card != null) {
            return this.converter.convertEntityToDto(card);
        }
        return null;
    }

    @Override
    public OysterCardPptPendingDTO findByRequestSequenceNumber(Long requestSequenceNumber) {
        return findByRequestSequenceNumber(requestSequenceNumber, null);
    }

    @Override
    public OysterCardPptPendingDTO findByRequestSequenceNumber(Long originalRequestSequenceNumber, String prestigeId) {
        OysterCardPptPending exampleEntity1 = createOysterCardPptPending();
        OysterCardPptPending exampleEntity2 = createOysterCardPptPending();
        OysterCardPptPending exampleEntity3 = createOysterCardPptPending();

        exampleEntity1.setRequestSequenceNumber1(originalRequestSequenceNumber);
        exampleEntity2.setRequestSequenceNumber2(originalRequestSequenceNumber);
        exampleEntity3.setRequestSequenceNumber3(originalRequestSequenceNumber);

        if (isNotBlank(prestigeId)) {
            exampleEntity1.setPrestigeId(prestigeId);
            exampleEntity2.setPrestigeId(prestigeId);
            exampleEntity3.setPrestigeId(prestigeId);
        }

        OysterCardPptPending result = dao.findByExampleUniqueResult(exampleEntity1);
        if (result == null) {
            result = dao.findByExampleUniqueResult(exampleEntity2);
            if (result == null) {
                result = dao.findByExampleUniqueResult(exampleEntity3);
            }
        }

        return (result != null) ? this.converter.convertEntityToDto(result) : null;
    }

    protected OysterCardPptPending createOysterCardPptPending() {
        return new OysterCardPptPending();
    }
}
