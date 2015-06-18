package com.novacroft.nemo.tfl.common.data_service.impl.cyber_source;

import com.novacroft.cyber_source.web_service.model.transaction.ReplyMessage;
import com.novacroft.cyber_source.web_service.model.transaction.RequestMessage;
import com.novacroft.nemo.common.constant.LocaleConstant;
import com.novacroft.nemo.tfl.common.converter.cyber_source.CyberSourceSoapDeleteTokenReplyConverter;
import com.novacroft.nemo.tfl.common.converter.cyber_source.CyberSourceSoapDeleteTokenRequestConverter;
import com.novacroft.nemo.tfl.common.converter.cyber_source.CyberSourceSoapReplyConverter;
import com.novacroft.nemo.tfl.common.converter.cyber_source.CyberSourceSoapRequestConverter;
import com.novacroft.nemo.tfl.common.data_service.cyber_source.CyberSourceTransactionDataService;
import com.novacroft.nemo.tfl.common.service_access.cyber_source.CyberSourceTransactionServiceAccess;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceSoapReplyDTO;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceSoapRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * CyberSource payment gateway transaction data service
 */
@Service("cyberSourceTransactionDataService")
public class CyberSourceTransactionDataServiceImpl implements CyberSourceTransactionDataService {

    @Autowired
    protected CyberSourceTransactionServiceAccess cyberSourceTransactionServiceAccess;
    @Autowired
    protected CyberSourceSoapReplyConverter cyberSourceSoapReplyConverter;
    @Autowired
    protected CyberSourceSoapRequestConverter cyberSourceSoapRequestConverter;
    @Autowired
    protected CyberSourceSoapDeleteTokenRequestConverter cyberSourceSoapDeleteTokenRequestConverter;
    @Autowired
    protected CyberSourceSoapDeleteTokenReplyConverter cyberSourceSoapDeleteTokenReplyConverter;
    @Value("${CyberSource.soap.merchantId}")
    protected String merchantId;

    @Override
    public CyberSourceSoapReplyDTO runTransaction(CyberSourceSoapRequestDTO requestDTO) {
        RequestMessage requestMessage = this.cyberSourceSoapRequestConverter.convertDtoToModel(requestDTO);
        requestMessage.setMerchantID(this.merchantId);
        requestDTO.setCustomerId(requestDTO.getCustomerId());
        requestMessage.getPurchaseTotals().setCurrency(LocaleConstant.UNITED_KINGDOM_CURRENCY_CODE);
        ReplyMessage replyMessage = this.cyberSourceTransactionServiceAccess.runTransaction(requestMessage);
        return this.cyberSourceSoapReplyConverter.convertModelToDto(replyMessage);
    }

    @Override
    public CyberSourceSoapReplyDTO deleteToken(CyberSourceSoapRequestDTO requestDTO) {
        RequestMessage requestMessage = this.cyberSourceSoapDeleteTokenRequestConverter.convertDtoToModel(requestDTO);
        requestMessage.setMerchantID(this.merchantId);
        requestMessage.setCustomerID(String.valueOf(requestDTO.getCustomerId()));
        ReplyMessage replyMessage = this.cyberSourceTransactionServiceAccess.runTransaction(requestMessage);
        return this.cyberSourceSoapDeleteTokenReplyConverter.convertModelToDto(replyMessage);
    }
}
