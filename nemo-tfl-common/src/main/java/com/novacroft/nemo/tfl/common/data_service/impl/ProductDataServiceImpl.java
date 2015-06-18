package com.novacroft.nemo.tfl.common.data_service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.PrePaidTicketConverter;
import com.novacroft.nemo.tfl.common.converter.impl.ProductConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.ProductDAO;
import com.novacroft.nemo.tfl.common.data_service.PrePaidTicketDataService;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;
import com.novacroft.nemo.tfl.common.domain.PrePaidTicket;
import com.novacroft.nemo.tfl.common.domain.Product;
import com.novacroft.nemo.tfl.common.transfer.PrePaidTicketDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;

/**
 * Product data service implementation
 */
@Service(value = "productDataService")
@Transactional(readOnly = true)
public class ProductDataServiceImpl extends BaseDataServiceImpl<Product, ProductDTO> implements ProductDataService {
	
	@Autowired
	protected PrePaidTicketDataService prePaidTicketDataService;
	
	@Autowired
    protected PrePaidTicketConverter prePaidTicketConverter;
	
    @Autowired
    public void setDao(ProductDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(ProductConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public Product getNewEntity() {
        return new Product();
    }

    @Override
    public ProductDTO findByProductName(String productName, boolean exact) {
        final String hsql = "from Product p where p.productName " + (exact ? "=" : "like") + " ?";
        productName = (exact ? productName : productName + "%");
        Product product = dao.findByQueryUniqueResult(hsql, productName);
        if (product != null) {
            return this.converter.convertEntityToDto(product);
        }
        return null;
    }

	@Override
	public ProductDTO findByProductCode(String productCode, Date effectiveDate) {
		final PrePaidTicketDTO   prePaidTicketDTO = 	prePaidTicketDataService.findByProductCode(productCode, effectiveDate);
        return prePaidTicketConverter.convertToProductDto(prePaidTicketDTO, effectiveDate);
		
	}

	@Override
	public List<ProductDTO> findAllByExample(PrePaidTicket example) {
		final List<PrePaidTicketDTO>   prePaidTicketDTOList = 	prePaidTicketDataService.findAllByExample(example);
		List<ProductDTO>  productDtoList = new ArrayList<>();
		if(prePaidTicketDTOList != null){
			for(PrePaidTicketDTO prePaidTicketDTO : prePaidTicketDTOList){
				productDtoList.add(prePaidTicketConverter.convertToProductDto(prePaidTicketDTO, null));
			}
		}
	    return productDtoList;
	}

	@Override
	public List<ProductDTO> findProductsByLikeDurationAndStartZone(String duration, Integer startZone, Date effectiveDate) {
		final List<PrePaidTicketDTO>   prePaidTicketDTOList = 	prePaidTicketDataService.findProductsByDurationAndStartZone(duration, startZone, effectiveDate);
		List<ProductDTO>  productDtoList = new ArrayList<>();
		if(prePaidTicketDTOList != null){
			for(PrePaidTicketDTO prePaidTicketDTO : prePaidTicketDTOList){
                productDtoList.add(prePaidTicketConverter.convertToProductDto(prePaidTicketDTO, effectiveDate));
			}
		}
	    return productDtoList;
	}
	
	@Override
    public ProductDTO findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(String fromDuration, String toDuration,
    		Integer startZone, Integer endZone, Date effectiveDate, String passengerType, String discountType, String type) {
		final PrePaidTicketDTO   prePaidTicketDTO = prePaidTicketDataService.findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(fromDuration, toDuration, startZone, endZone, effectiveDate, passengerType, discountType, type);
        return prePaidTicketDTO == null ? null : prePaidTicketConverter.convertToProductDto(prePaidTicketDTO, effectiveDate);
	}
	
	@Override
	public ProductDTO findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(String fromDuration, Integer startZone,
			Integer endZone, Date effectiveDate, String passengerType,String discountType, String type){
		final PrePaidTicketDTO   prePaidTicketDTO = prePaidTicketDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(fromDuration, startZone, endZone, effectiveDate, passengerType, discountType, type);
        return prePaidTicketDTO == null ? null : prePaidTicketConverter.convertToProductDto(prePaidTicketDTO, effectiveDate);
	}

    @Override
    public List<ProductDTO> findProductsByStartZoneAndTypeForOddPeriod(Integer startZone, Date effectiveDate, String type) {
        final List<PrePaidTicketDTO> prePaidTicketDTOList = prePaidTicketDataService.findProductsByStartZoneAndTypeForOddPeriod(startZone,
                        effectiveDate, type);
        List<ProductDTO> productDtoList = new ArrayList<>();
        if (prePaidTicketDTOList != null) {
            for (PrePaidTicketDTO prePaidTicketDTO : prePaidTicketDTOList) {
                productDtoList.add(prePaidTicketConverter.convertToProductDto(prePaidTicketDTO, effectiveDate));
            }
        }
        return productDtoList;
    }

    @Override
    public ProductDTO findById(Long id, Date effectiveDate) {
        final PrePaidTicketDTO prePaidTicketDTO = prePaidTicketDataService.findById(id);
        return prePaidTicketDTO == null ? null : prePaidTicketConverter.convertToProductDto(prePaidTicketDTO, effectiveDate);
    }
	
}
