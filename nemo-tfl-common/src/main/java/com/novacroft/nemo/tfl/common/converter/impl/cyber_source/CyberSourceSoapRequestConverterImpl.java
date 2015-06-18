package com.novacroft.nemo.tfl.common.converter.impl.cyber_source;

import com.novacroft.cyber_source.web_service.model.transaction.*;
import com.novacroft.nemo.tfl.common.converter.cyber_source.CyberSourceSoapRequestConverter;
import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceSoapRequestDTO;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

import static com.novacroft.nemo.common.utils.CurrencyUtil.formatPenceWithoutCurrencySymbol;

/**
 * CyberSource payment gateway request DTO/model converter
 */
@Service("cyberSourceSoapRequestConverter")
public class CyberSourceSoapRequestConverterImpl implements CyberSourceSoapRequestConverter {
    @Override
    public RequestMessage convertDtoToModel(CyberSourceSoapRequestDTO requestDTO) {
        ObjectFactory objectFactory = new ObjectFactory();
        RequestMessage requestMessage = objectFactory.createRequestMessage();
        requestMessage.setMerchantID(requestDTO.getMerchantId());
        requestMessage.setMerchantReferenceCode(requestDTO.getTransactionUuid());

        RecurringSubscriptionInfo recurringSubscriptionInfo = objectFactory.createRecurringSubscriptionInfo();
        recurringSubscriptionInfo.setSubscriptionID(requestDTO.getPaymentCardToken());
        requestMessage.setRecurringSubscriptionInfo(recurringSubscriptionInfo);

        BillTo billTo = objectFactory.createBillTo();
        billTo.setBuildingNumber(requestDTO.getBillToBuildingNumber());
        billTo.setCity(requestDTO.getBillToCity());
        billTo.setCustomerID(requestDTO.getBillToCustomerId());
        billTo.setEmail(requestDTO.getBillToEmail());
        billTo.setFirstName(requestDTO.getBillToFirstName());
        billTo.setIpAddress(requestDTO.getBillToIpAddress());
        billTo.setLastName(requestDTO.getBillToLastName());
        billTo.setPhoneNumber(requestDTO.getBillToPhoneNumber());
        billTo.setPostalCode(requestDTO.getBillToPostalCode());
        billTo.setStreet1(requestDTO.getBillToStreet1());
        billTo.setStreet2(requestDTO.getBillToStreet2());
        requestMessage.setBillTo(billTo);

        CCAuthService ccAuthService = objectFactory.createCCAuthService();
        ccAuthService.setRun(String.valueOf(Boolean.TRUE));
        requestMessage.setCcAuthService(ccAuthService);

        CCCaptureService ccCaptureService = objectFactory.createCCCaptureService();
        ccCaptureService.setRun(String.valueOf(Boolean.TRUE));
        requestMessage.setCcCaptureService(ccCaptureService);

        MDDField mddField = objectFactory.createMDDField();
        mddField.setId(BigInteger.valueOf(1));
        mddField.setValue(requestDTO.getOrderNumber());

        MerchantDefinedData merchantDefinedData = objectFactory.createMerchantDefinedData();
        merchantDefinedData.getMddField().add(mddField);
        requestMessage.setMerchantDefinedData(merchantDefinedData);

        PurchaseTotals purchaseTotals = objectFactory.createPurchaseTotals();
        purchaseTotals.setGrandTotalAmount(formatPenceWithoutCurrencySymbol(requestDTO.getTotalAmountInPence()));
        requestMessage.setPurchaseTotals(purchaseTotals);

        return requestMessage;
    }
}
