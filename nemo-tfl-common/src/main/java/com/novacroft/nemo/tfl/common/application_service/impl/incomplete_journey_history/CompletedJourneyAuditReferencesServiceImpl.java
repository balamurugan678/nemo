package com.novacroft.nemo.tfl.common.application_service.impl.incomplete_journey_history;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.common.application_service.incomplete_journey_history.CompletedJourneyAuditReferencesService;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.transfer.JourneyCompletedRefundItemDTO;
import com.novacroft.nemo.tfl.common.transfer.LocationDTO;

@Component
public class CompletedJourneyAuditReferencesServiceImpl implements	CompletedJourneyAuditReferencesService {

	@Autowired
	protected LocationDataService locationDataService;
	
	
	@Override
	public void resovleReferences(List<JourneyCompletedRefundItemDTO> refundItemDtoList) {
		if(refundItemDtoList != null) {
			for(JourneyCompletedRefundItemDTO refundDto : refundItemDtoList) {
				refundDto.setStartExitStationName(resolveStation(refundDto.getStartExitStation()));
				refundDto.setPickUpStationName(resolveStation(refundDto.getPickUpStation()));
				refundDto.setProvidedStationName(resolveStation(refundDto.getProvidedStation()));
			}
		}
	}


	protected String resolveStation(Integer stationId){
		LocationDTO  locationDTO = null;
		if(stationId != null) {
			locationDTO  = locationDataService.getActiveLocationById(stationId);
			
		}
		return   locationDTO != null ? locationDTO.getName() : null;
		
	}
}
