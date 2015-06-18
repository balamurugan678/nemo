package com.novacroft.nemo.mock_cubic.data_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.mock_cubic.converter.OysterCardPrepayValueConverterImpl;
import com.novacroft.nemo.mock_cubic.data_access.OysterCardPrepayValueDAO;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardPrepayValueDataService;
import com.novacroft.nemo.mock_cubic.domain.card.OysterCardPrepayValue;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPrepayValueDTO;

@Service(value = "oysterCardPrepayValueDataService")
@Transactional(readOnly = true)
public class OysterCardPrepayValueDataServiceImpl extends BaseDataServiceImpl<OysterCardPrepayValue, OysterCardPrepayValueDTO> implements OysterCardPrepayValueDataService {
    @Autowired
    public void setDao(OysterCardPrepayValueDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(OysterCardPrepayValueConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public OysterCardPrepayValue getNewEntity() {
        return new OysterCardPrepayValue();
    }

    @Override
    public List<OysterCardPrepayValueDTO> findAllBy(String test) {
      return null;
    }

    @Override
    public OysterCardPrepayValueDTO findByTest(String test) {
      return null;
    }
    
    @Override
    public OysterCardPrepayValueDTO findByCardNumber(String cardNumber) {
        final String hsql = "from OysterCardPrepayValue c where c.prestigeId = ?";
        OysterCardPrepayValue card = dao.findByQueryUniqueResult(hsql, cardNumber);
        if (card != null) {
            return this.converter.convertEntityToDto(card);
        }
        return null;
    }
}
