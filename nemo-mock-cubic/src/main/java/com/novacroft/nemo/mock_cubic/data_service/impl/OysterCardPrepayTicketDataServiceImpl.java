package com.novacroft.nemo.mock_cubic.data_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.mock_cubic.converter.OysterCardPrepayTicketConverterImpl;
import com.novacroft.nemo.mock_cubic.data_access.OysterCardPrepayTicketDAO;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardPrepayTicketDataService;
import com.novacroft.nemo.mock_cubic.domain.card.OysterCardPrepayTicket;
import com.novacroft.nemo.mock_cubic.transfer.OysterCardPrepayTicketDTO;

@Service(value = "oysterCardPrepayTicketDataService")
@Transactional(readOnly = true)
public class OysterCardPrepayTicketDataServiceImpl extends BaseDataServiceImpl<OysterCardPrepayTicket, OysterCardPrepayTicketDTO> implements OysterCardPrepayTicketDataService {
    
    @Autowired
    public void setDao(OysterCardPrepayTicketDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(OysterCardPrepayTicketConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public OysterCardPrepayTicket getNewEntity() {
        return new OysterCardPrepayTicket();
    }

    @Override
    public List<OysterCardPrepayTicketDTO>  findAllBy(String test) {
        return null;
    }

    @Override
    public OysterCardPrepayTicketDTO  findByTest(String test) {
        return null;
    }
    
    @Override
    public OysterCardPrepayTicketDTO findByCardNumber(String cardNumber) {
        final String hsql = "from OysterCardPrepayTicket c where c.prestigeId = ?";
        OysterCardPrepayTicket card = dao.findByQueryUniqueResult(hsql, cardNumber);
        if (card != null) {
            return this.converter.convertEntityToDto(card);
        }
        return null;
    }
}
