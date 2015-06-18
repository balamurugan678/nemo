package com.novacroft.nemo.tfl.common.application_service.impl;

import com.novacroft.nemo.tfl.common.application_service.WebCreditService;
import com.novacroft.nemo.tfl.common.data_service.WebCreditSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.WebCreditSettlementDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Application service to get web credit balance and make payments/refunds using web credit
 */
@Service(value = "webCreditService")
public class WebCreditServiceImpl implements WebCreditService {
    @Autowired
    protected WebCreditSettlementDataService webAccountCreditSettlementDataService;

    /**
     * Positive settlement values are purchases, negative values are refunds.  Web credit is available
     * when there is a net negative balance.
     */
    @Override
    public Integer getAvailableBalance(Long customerId) {
        Integer balance = this.webAccountCreditSettlementDataService.getBalance(customerId);
        return (balance < 0) ? Math.abs(balance) : 0;
    }
    
   @Override
   public void applyWebCreditToOrder(Long orderId, Integer webCreditPenceAmount) {
       this.webAccountCreditSettlementDataService.createOrUpdate(
               new WebCreditSettlementDTO(orderId, webCreditPenceAmount));
   }
}
