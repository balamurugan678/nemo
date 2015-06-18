package com.novacroft.nemo.tfl.common.converter.impl.journey_history;

import javax.xml.bind.JAXBElement;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.converter.journey_history.TapResponseConverter;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.TapResponseDTO;
import com.novacroft.tfl.web_service.model.oyster_journey_history.FAETapResponse;
@Component
public class TapResponseConverterImpl implements TapResponseConverter {

	@Override
	public TapResponseDTO convertModelToDto(FAETapResponse response) {
		TapResponseDTO responseDto = null;
		if(response != null){
			responseDto = new TapResponseDTO();
			responseDto.setBusRouteId(getStringValue(response.getBusRouteId()));
			responseDto.setDeviceCategory(getStringValue(response.getDeviceCategory()));
			responseDto.setIsSyntheticTap(response.isIsSyntheticTap());
			responseDto.setLocationNLC(response.getLocationNLC());
			responseDto.setSequenceNo(response.getSequenceNo());
			responseDto.setRolloverSequenceNo(response.getRolloverSequenceNo());
			responseDto.setTransactionDateTime(DateUtil.convertXMLGregorianToDate(response.getTransactionDateTime()));
			responseDto.setTransactionType(response.getTransactionType());
			responseDto.setTransactionOffset(response.getTransactionOffset());
		}
		return responseDto;
	}

	private String getStringValue(JAXBElement<String> element){
		if(element != null){
			return element.getValue();
		}
		return null;
	}
	
}
