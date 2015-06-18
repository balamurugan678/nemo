package com.novacroft.nemo.tfl.common.application_service.impl.financial_services_centre;

import org.springframework.beans.factory.annotation.Autowired;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.tfl.common.converter.financial_services_centre.AddressExportConverter;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.AddressExportDTO;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.PayeeSettlementDTO;

public class PayRquestFileGeneratorService {

	@Autowired
	protected SystemParameterService systemParameterService;
	@Autowired
	protected AddressExportConverter addressExportConverter;

	public PayRquestFileGeneratorService() {
		super();
	}

	protected String getStringParameter(String name) {
	    return this.systemParameterService.getParameterValue(name);
	}

	protected Integer getIntegerParameter(String name) {
	    return this.systemParameterService.getIntegerParameterValue(name);
	}

	protected AddressExportDTO transformAddressForExport(PayeeSettlementDTO  bacsSettlementDTO) {
	    return this.addressExportConverter.convert(bacsSettlementDTO.getAddressDTO());
	}

}