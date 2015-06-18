package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.CardRefundableDeposit;
import com.novacroft.nemo.tfl.common.transfer.CardRefundableDepositDTO;

/**
 * Card refundable deposit data service specification
 */
public interface CardRefundableDepositDataService extends BaseDataService<CardRefundableDeposit, CardRefundableDepositDTO> {
    CardRefundableDepositDTO findRefundableDepositAmount();
    CardRefundableDepositDTO findByPrice(Integer price) ;
}
