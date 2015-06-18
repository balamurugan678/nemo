package com.novacroft.nemo.tfl.common.application_service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.tfl.common.application_service.AdministrationFeeService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.TicketType;
import com.novacroft.nemo.tfl.common.data_service.AdministrationFeeDataService;
import com.novacroft.nemo.tfl.common.data_service.AdministrationFeeItemDataService;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeDTO;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeItemDTO;


/**
 * Administration fee service implementation
 */
@Service(value = "administrationFeeService")
public class AdministrationFeeServiceImpl implements AdministrationFeeService {
    static final Logger logger = LoggerFactory.getLogger(AdministrationFeeServiceImpl.class);

    @Autowired
    protected AdministrationFeeDataService administrationFeeDataService;
    
    @Autowired
    protected AdministrationFeeItemDataService administrationFeeItemDataService;

    public AdministrationFeeItemDTO getNewAdministrationFeeItemDTO(CartItemCmdImpl cmd) {
        AdministrationFeeItemDTO administrationFeeItem = null;
        AdministrationFeeDTO administrationFee = null;
        administrationFee = administrationFeeDataService.findByType(TicketType.ADMINISTRATION_FEE.code() + cmd.getCartType());
        if (administrationFee != null) {
            administrationFeeItem = createAdministrationFeeItemDTO(cmd.getCardId(), administrationFee.getPrice(), administrationFee.getId());
        }
        return administrationFeeItem;
    }
    
    public AdministrationFeeDTO getAdministrationFeeDTO(String cartType){
        return administrationFeeDataService.findByType(TicketType.ADMINISTRATION_FEE.code() + cartType);
    }
    
    protected AdministrationFeeItemDTO createAdministrationFeeItemDTO(Long cardId, Integer creditBalance, Long administrationFeeId) {
        return new AdministrationFeeItemDTO(cardId, creditBalance, administrationFeeId);
    }
}
