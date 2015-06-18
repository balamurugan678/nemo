package com.novacroft.nemo.tfl.services.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.common.transfer.CancelOrderResultDTO;
import com.novacroft.nemo.tfl.services.converter.WebsServiceResultConverter;
import com.novacroft.nemo.tfl.services.transfer.WebServiceResult;

@Component("websServiceResultConverterImpl")
public class WebsServiceResultConverterImpl implements WebsServiceResultConverter {

    @Override
    public WebServiceResult convertCancelOrderResultDTOToWebServiceResult(CancelOrderResultDTO cancelOrderResultDTO) {
        WebServiceResult webServiceResult = new WebServiceResult();
        webServiceResult.setId(cancelOrderResultDTO.getId());
        webServiceResult.setOriginalId(cancelOrderResultDTO.getOriginalId());
        webServiceResult.setResult(cancelOrderResultDTO.getResult());
        webServiceResult.setMessage(cancelOrderResultDTO.getMessage());
        return webServiceResult;
    }

}
