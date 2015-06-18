package com.novacroft.nemo.mock_cubic.data_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.mock_cubic.converter.OysterCardHotListReasonsConverterImpl;
import com.novacroft.nemo.mock_cubic.data_access.OysterCardHotListReasonsDAO;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardHotListReasonsDataService;
import com.novacroft.nemo.mock_cubic.domain.card.OysterCardHotListReasons;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardHotListReasonsDTO;
/**
* Oystercard transfer implementation.
*/

@Service(value = "oysterCardHotListReasonsDataService")
@Transactional
public class OysterCardHotListReasonsDataServiceImpl extends BaseDataServiceImpl<OysterCardHotListReasons, OysterCardHotListReasonsDTO> implements OysterCardHotListReasonsDataService {
    @Autowired
    public void setDao(OysterCardHotListReasonsDAO dao) {
      this.dao = dao;
    }

    @Autowired
    public void setConverter(OysterCardHotListReasonsConverterImpl converter) {
      this.converter = converter;
    }
    
    public OysterCardHotListReasons getNewEntity() {
      return new OysterCardHotListReasons();
    }
    
    @Override
    public List<OysterCardHotListReasonsDTO> findAllBy(String test) {
      return null;
    }
    
    @Override
    public OysterCardHotListReasonsDTO findByTest(String test) {
      return null;
    }
    
    @Override
    public OysterCardHotListReasonsDTO findByCardNumber(String cardNumber) {
        final String hsql = "from OysterCardHotListReasons c where c.prestigeId = ?";
        OysterCardHotListReasons card = dao.findByQueryUniqueResult(hsql, cardNumber);
        return (card != null) ? this.converter.convertEntityToDto(card) : null;
    }
}
