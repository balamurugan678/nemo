package com.novacroft.nemo.tfl.common.converter.impl.incomplete_journey_notification;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.common.converter.incomplete_journey_notification.NotifyAutoFillOfSSRStatusResponseConverter;
import com.novacroft.nemo.tfl.common.converter.incomplete_journey_notification.NotifyAutoFillOfSSRStatusResponseDTO;
import com.novacroft.tfl.web_service.model.incomplete_journey_notification.NotifyAutoFillOfSSRStatusResponse;

@Component
public class NotifyAutoFillOfSSRStatusResponseConverterImpl implements	NotifyAutoFillOfSSRStatusResponseConverter {

	@Override
	public NotifyAutoFillOfSSRStatusResponseDTO convertModelToDto(NotifyAutoFillOfSSRStatusResponse response) {
		
		NotifyAutoFillOfSSRStatusResponseDTO  notifyAutoFillOfSSRStatusResponseDTO = new NotifyAutoFillOfSSRStatusResponseDTO();
		notifyAutoFillOfSSRStatusResponseDTO.setAutoFillNotificationSuccessful(response.getNotifyAutoFillOfSSRStatusResult().getValue().isAutoFillNotificationSuccessful());
		return notifyAutoFillOfSSRStatusResponseDTO;
	}

}
