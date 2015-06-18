package com.novacroft.nemo.tfl.batch.application_service.product_fare_loader;

import com.novacroft.nemo.tfl.batch.domain.product_fare_loader.PrePaidTicketRecord;

public interface PrePaidTicketUploadService {

	void updatePrePaidDataForRecord(PrePaidTicketRecord prePaidTicketRecord);

}
