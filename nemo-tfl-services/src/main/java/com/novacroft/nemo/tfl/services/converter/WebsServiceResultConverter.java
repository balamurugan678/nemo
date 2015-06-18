package com.novacroft.nemo.tfl.services.converter;

import com.novacroft.nemo.tfl.common.transfer.CancelOrderResultDTO;
import com.novacroft.nemo.tfl.services.transfer.WebServiceResult;

public interface WebsServiceResultConverter {
    WebServiceResult convertCancelOrderResultDTOToWebServiceResult(CancelOrderResultDTO cancelOrderResultDTO);
}
