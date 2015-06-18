package com.novacroft.nemo.tfl.common.application_service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import com.novacroft.nemo.tfl.common.application_service.CardSelectListService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;

/**
 * Get cards as a select list service implementation
 */
@Service("cardSelectListService")
public class CardSelectListServiceImpl implements CardSelectListService {
    @Autowired
    protected CardDataService cardDataService;

    @Override
    public SelectListDTO getCardsSelectList(String username) {
        List<CardDTO> cardDTOs = this.cardDataService.findByUsername(username);
        SelectListDTO selectListDTO = getCardsList(cardDTOs);
        return selectListDTO;
    }

	private SelectListDTO getCardsList(List<CardDTO> cardDTOs) {
		SelectListDTO selectListDTO = new SelectListDTO();
        selectListDTO.setName("Cards");
        selectListDTO.setOptions(new ArrayList<SelectListOptionDTO>());
        for (CardDTO cardDTO : cardDTOs) {
            selectListDTO.getOptions().add(new SelectListOptionDTO(cardDTO.getId().toString(), cardDTO.getCardNumber()));
        }
		return selectListDTO;
	}

	@Override
	public SelectListDTO getCardsSelectListForCustomer(Long customerId) {
		final  List<CardDTO> cardDTOs = this.cardDataService.findByCustomerId(customerId);
		SelectListDTO selectListDTO = getCardsList(cardDTOs);
		return selectListDTO;
	}
    
    
}
