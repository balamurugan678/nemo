package com.novacroft.nemo.tfl.common.converter.incomplete_journey_notification;

import com.novacroft.tfl.web_service.model.incomplete_journey_notification.NotifyAutoFillOfSSRStatusResponse;

public interface NotifyAutoFillOfSSRStatusResponseConverter {

	NotifyAutoFillOfSSRStatusResponseDTO convertModelToDto(	NotifyAutoFillOfSSRStatusResponse response);

}
