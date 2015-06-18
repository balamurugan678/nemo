package com.novacroft.nemo.tfl.common.data_service;

import java.util.Date;
import java.util.List;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.PrePaidTicket;
import com.novacroft.nemo.tfl.common.domain.Product;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;

/**
 * Product data service specification
 */
public interface ProductDataService extends BaseDataService<Product, ProductDTO> {

    ProductDTO findByProductName(String productName, boolean exact);
	
	ProductDTO findByProductCode(String productCode, Date effectiveDate);

	List<ProductDTO> findAllByExample(PrePaidTicket example);

	List<ProductDTO> findProductsByLikeDurationAndStartZone(String duration, Integer startZone, Date effectiveDate);
	
    List<ProductDTO> findProductsByStartZoneAndTypeForOddPeriod(Integer startZone, Date effectiveDate, String type);

	ProductDTO findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(String fromDuration, String toDuration, Integer startZone,
			Integer endZone, Date effectiveDate, String passengerType, String discountType, String type);
	
	ProductDTO findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(String fromDuration, Integer startZone,
			Integer endZone, Date effectiveDate, String passengerType, String discountType, String type);

    ProductDTO findById(Long id, Date effectiveDate);
}
