package com.novacroft.nemo.mock_cubic.data_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.mock_cubic.converter.OysterCardConverterImpl;
import com.novacroft.nemo.mock_cubic.data_access.OysterCardDAO;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardDataService;
import com.novacroft.nemo.mock_cubic.domain.card.OysterCard;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardDTO;

@Service(value = "oysterCardDataService")
@Transactional
public class OysterCardDataServiceImpl extends BaseDataServiceImpl<OysterCard, OysterCardDTO> implements OysterCardDataService {
    
    @Autowired
    public void setDao(OysterCardDAO dao) {
      this.dao = dao;
    }

    @Autowired
    public void setConverter(OysterCardConverterImpl converter) {
      this.converter = converter;
    }
    
    public OysterCard getNewEntity() {
      return new OysterCard();
    }
    
    @Override
    public List<OysterCard> findAllBy(String test) {
      return null;
    }
    
    @Override
    public OysterCard findByTest(String test) {
      return null;
    }
    
    @Override
    public OysterCardDTO findByCardNumber(String cardNumber) {
        final String hsql = "from OysterCard c where c.prestigeId = ?";
        OysterCard card = dao.findByQueryUniqueResult(hsql, cardNumber);
        return (card != null) ? this.converter.convertEntityToDto(card) : null;
    }
}
