package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.common.application_service.ServiceCallLogService;
import com.novacroft.nemo.common.converter.XmlModelConverter;
import com.novacroft.nemo.common.exception.ServiceAccessException;
import com.novacroft.nemo.common.support.NemoUserContext;
import com.novacroft.nemo.common.transfer.ServiceCallLogDTO;
import com.novacroft.nemo.tfl.common.constant.PrivateError;
import com.novacroft.nemo.tfl.common.constant.WebServiceName;
import com.novacroft.nemo.tfl.common.converter.AutoLoadChangeConverter;
import com.novacroft.nemo.tfl.common.data_service.AutoLoadConfigurationChangePushToGateDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.domain.cubic.AutoLoadRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.AutoLoadResponse;
import com.novacroft.nemo.tfl.common.domain.cubic.RequestFailure;
import com.novacroft.nemo.tfl.common.service_access.cubic.CubicServiceAccess;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeRequestDTO;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeResponseDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Call CUBIC Auto-load Configuration Change service
 */
@Service("autoLoadConfigurationChangePushToGateDataService")
public class AutoLoadConfigurationChangePushToGateDataServiceImpl implements AutoLoadConfigurationChangePushToGateDataService {
    protected static final Logger logger = LoggerFactory.getLogger(AutoLoadConfigurationChangePushToGateDataServiceImpl.class);
    @Autowired
    protected XmlModelConverter<AutoLoadRequest> autoLoadRequestConverter;
    @Autowired
    protected AutoLoadChangeConverter autoLoadChangeConverter;
    @Autowired
    protected XmlModelConverter<AutoLoadResponse> autoLoadResponseConverter;
    @Autowired
    protected XmlModelConverter<RequestFailure> requestFailureConverter;
    @Autowired
    protected ServiceCallLogService serviceCallLogService;
    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected NemoUserContext nemoUserContext;
    @Autowired
    protected CubicServiceAccess cubicServiceAccess;

    @Override
    public AutoLoadChangeResponseDTO changeAutoLoadConfigurationRequest(AutoLoadChangeRequestDTO request) {
        AutoLoadRequest autoLoadRequest = this.autoLoadChangeConverter.convertToModel(request);
        String xmlRequest = this.autoLoadRequestConverter.convertModelToXml(autoLoadRequest);
        ServiceCallLogDTO serviceCallLogDTO = this.serviceCallLogService
                .initialiseCallLog(WebServiceName.CUBIC_CHANGE_AUTO_LOAD_CONFIGURATION.code(), nemoUserContext.getUserName(),
                        getCustomerId(request.getPrestigeId()));
        String xmlResponse = this.cubicServiceAccess.callCubic(xmlRequest).toString();
        this.serviceCallLogService.finaliseCallLog(serviceCallLogDTO, xmlRequest, xmlResponse);
        Object modelResponse = this.autoLoadResponseConverter.convertXmlToObject(xmlResponse);
        if (isSuccessResponse(modelResponse)) {
            return this.autoLoadChangeConverter.convertToDto((AutoLoadResponse) modelResponse);
        }
        return this.autoLoadChangeConverter.convertToDto(this.requestFailureConverter.convertXmlToModel(xmlResponse));
    }

    protected boolean isSuccessResponse(Object modelResponse) {
        return modelResponse instanceof AutoLoadResponse;
    }

    protected Long getCustomerId(String cardNumber) {
        CustomerDTO customerDTO = this.customerDataService.findByCardNumber(cardNumber);
        if (customerDTO == null) {
            throw new ServiceAccessException(
                    String.format(PrivateError.FOUND_NO_RECORDS.message(), "customerDataService.findByCardNumber", cardNumber));
        }
        return customerDTO.getId();
    }
}
