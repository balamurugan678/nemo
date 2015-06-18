package com.novacroft.nemo.tfl.common.application_service.impl.cyber_source;

import com.novacroft.nemo.common.constant.LocaleConstant;
import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.application_service.cyber_source.CyberSourceSoapService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardDataService;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardDTO;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceSoapRequestDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.novacroft.nemo.tfl.common.util.AddressFormatUtil.formatLine1;
import static com.novacroft.nemo.tfl.common.util.AddressFormatUtil.formatLine2;

/**
 * CyberSource Secure Acceptance soap application service
 */
@Service("cyberSourceSoapService")
public class CyberSourceSoapServiceImpl implements CyberSourceSoapService {

    @Autowired
    protected PaymentCardDataService paymentCardDataService;
    @Autowired
    protected PaymentCardSettlementDataService paymentCardSettlementDataService;
    @Autowired
    protected AddressDataService addressDataService;
    @Autowired
    protected CustomerDataService customerDataService;

    @Override
    public CyberSourceSoapRequestDTO preparePaymentRequestData(OrderDTO orderDTO, PaymentCardSettlementDTO settlementDTO,
                                                               String clientIpAddress) {
        PaymentCardSettlementDTO paymentCardSettlementDTO =
                this.paymentCardSettlementDataService.findById(settlementDTO.getId());
        PaymentCardDTO paymentCardDTO = this.paymentCardDataService.findById(paymentCardSettlementDTO.getPaymentCardId());
        AddressDTO addressDTO = this.addressDataService.findById(paymentCardDTO.getAddressId());
        
        CustomerDTO customerDTO = this.customerDataService.findById(orderDTO.getCustomerId());

        return new CyberSourceSoapRequestDTO(Boolean.TRUE, Boolean.TRUE, String.valueOf(orderDTO.getOrderNumber()), null,
                paymentCardSettlementDTO.getTransactionUuid(), LocaleConstant.UNITED_KINGDOM_CURRENCY_CODE,
                paymentCardSettlementDTO.getAmount(), paymentCardDTO.getToken(), paymentCardDTO.getFirstName(),
                paymentCardDTO.getLastName(), clientIpAddress, customerDTO.getEmailAddress(), addressDTO.getCountryCode(),
                addressDTO.getPostcode(), addressDTO.getTown(),
                formatLine2(addressDTO.getHouseNameNumber(), addressDTO.getStreet()),
                formatLine1(addressDTO.getHouseNameNumber(), addressDTO.getStreet()), customerDTO.getId());
    }
}


