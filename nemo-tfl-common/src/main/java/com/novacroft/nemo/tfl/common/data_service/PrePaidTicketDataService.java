package com.novacroft.nemo.tfl.common.data_service;

import java.util.Date;
import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.PrePaidTicket;
import com.novacroft.nemo.tfl.common.transfer.PrePaidTicketDTO;

public interface PrePaidTicketDataService extends BaseDataService<PrePaidTicket, PrePaidTicketDTO> {

	PrePaidTicketDTO findByProductCode(String productCode, Date effectiveDate);

	List<PrePaidTicketDTO> findAllByExample(PrePaidTicket example);

	List<PrePaidTicketDTO> findProductsByDurationAndStartZone(String productName, Integer startZone, Date effectiveDate);
	
	PrePaidTicketDTO findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(
			String fromDuration, Integer startZone,
			Integer endZone, Date effectiveDate, String passengerType, String discountType, String type);

	PrePaidTicketDTO findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(
			String fromDuration, String toDuration, Integer startZone,
			Integer endZone, Date effectiveDate, String passengerType, String discountType, String type);

    List<PrePaidTicketDTO> findProductsByStartZoneAndTypeForOddPeriod(Integer startZone, Date effectiveDate, String type);

	PrePaidTicketDTO findByProductCode(String productCode);

	List<PrePaidTicketDTO> findAllByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(String fromDuration, String toDuration, Integer startZone,	Integer endZone, String passengerType, String discountType, String type);	
}
