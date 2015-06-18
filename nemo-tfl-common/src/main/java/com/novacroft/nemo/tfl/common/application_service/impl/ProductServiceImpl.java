package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.utils.DateUtil.formatStringAsDate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.transfer.SelectListOptionDTO;
import com.novacroft.nemo.tfl.common.application_service.ProductService;
import com.novacroft.nemo.tfl.common.application_service.TravelCardService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.ProductItemType;
import com.novacroft.nemo.tfl.common.converter.impl.PrePaidTicketConverterImpl;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.DiscountTypeDataService;
import com.novacroft.nemo.tfl.common.data_service.PassengerTypeDataService;
import com.novacroft.nemo.tfl.common.data_service.PrePaidTicketDataService;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;
import com.novacroft.nemo.tfl.common.transfer.DiscountTypeDTO;
import com.novacroft.nemo.tfl.common.transfer.DurationPeriodDTO;
import com.novacroft.nemo.tfl.common.transfer.PassengerTypeDTO;
import com.novacroft.nemo.tfl.common.transfer.PrePaidTicketDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.common.util.DurationUtil;

/**
 * Product service implementation
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    protected ProductDataService productDataService;
    @Autowired
    protected SystemParameterService systemParameterService;
    @Autowired
    protected TravelCardService travelCardService;
    @Autowired
    protected PrePaidTicketDataService prePaidTicketDataService;
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected GetCardService getCardService;
    @Autowired
    protected PrePaidTicketConverterImpl prePaidTicketConverter;
    @Autowired
    protected PassengerTypeDataService passengerTypeDataService;
    @Autowired
    protected DiscountTypeDataService discountTypeDataService;

    @Override
    public String getProductName(ProductItemDTO productItem) {
        PrePaidTicketDTO prePaidTicketDTO = null;
        prePaidTicketDTO = prePaidTicketDataService.findById(productItem.getProductId());
        return prePaidTicketDTO.getDescription();
    }

    @Override
    public List<ProductDTO> getProductsByLikeDurationAndStartZone(String duration, Integer startZone) {
        return productDataService.findProductsByLikeDurationAndStartZone(duration, startZone, null);
    }

    @Override
    public ProductDTO getProductByFromAndToDurationAndZonesAndPassengerTypeAndDiscountType(CartItemCmdImpl cartItem) {
        if (cartItem.getPrePaidTicketId() != null) {
            return prePaidTicketConverter.convertToProductDto(prePaidTicketDataService.findById(cartItem.getPrePaidTicketId()),
                            formatStringAsDate(cartItem.getStartDate()));
        } else {
            DurationPeriodDTO duration = DurationUtil.getDurationForOddPeriod(formatStringAsDate(cartItem.getStartDate()),formatStringAsDate(cartItem.getEndDate()));
            return productDataService.findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(duration.getFromDurationCode(),
                            duration.getToDurationCode(), cartItem.getStartZone(), cartItem.getEndZone(), formatStringAsDate(cartItem.getStartDate()),
                            cartItem.getPassengerType(), cartItem.getDiscountType(), cartItem.getTicketType());
        }
    }

    @Override
    public ProductDTO getProductByFromDurationAndZonesAndPassengerTypeAndDiscountType(CartItemCmdImpl cartItem) {
        if (cartItem.getPrePaidTicketId() != null) {
            return prePaidTicketConverter.convertToProductDto(prePaidTicketDataService.findById(cartItem.getPrePaidTicketId()),
                            formatStringAsDate(cartItem.getStartDate()));
        } else {
            if (cartItem.getTicketType().contains(ProductItemType.BUS_PASS.code())) {
            	setZonesIntoBusPass(cartItem);
            	return productDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(cartItem.getTravelCardType(),
                        cartItem.getStartZone(), cartItem.getEndZone(), formatStringAsDate(cartItem.getStartDate()),
                        cartItem.getPassengerType(), cartItem.getDiscountType(),cartItem.getTicketType());
            } else {
	            return productDataService.findByFromDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(cartItem.getTravelCardType(),
	                            cartItem.getStartZone(), cartItem.getEndZone(), formatStringAsDate(cartItem.getStartDate()),
	                            cartItem.getPassengerType(), cartItem.getDiscountType(),cartItem.getTicketType());
            }
        }
    }
    
    protected void setZonesIntoBusPass(CartItemCmdImpl cartItem) {
    	//This method fixes the bus pass bug concerning the zones, when doing a refund
    	//TODO: A proper architecture should be implemented on the bus pass so it supports the zones transparently
    	cartItem.setStartZone(new Integer(1));
    	cartItem.setEndZone(new Integer(4));
    }

    @Override
    public String getDurationFromPrePaidTicketDTO(PrePaidTicketDTO prePaidTicketDTO) {
        return prePaidTicketDTO.getFromDurationDTO().getCode().equals(prePaidTicketDTO.getToDurationDTO().getCode()) ? prePaidTicketDTO
                        .getFromDurationDTO().getCode() : Durations.OTHER.getDurationType();
    }

    @Override
    public List<ProductDTO> getProductsByStartZoneForOddPeriod(Integer startZone, String type) {
        return productDataService.findProductsByStartZoneAndTypeForOddPeriod(startZone, null, type);
    }

    @Override
    public ProductDTO getProductByFromAndToDurationAndZonesAndPassengerTypeAndDiscountType(CartItemCmdImpl cartItem, String duration) {
        return productDataService.findByFromAndToDurationAndZonesAndPassengerTypeAndDiscountTypeAndType(duration, duration, cartItem.getStartZone(),
                        cartItem.getEndZone(), formatStringAsDate(cartItem.getStartDate()), cartItem.getPassengerType(), cartItem.getDiscountType(), cartItem.getTicketType());
    }
    
    @Override
    public SelectListDTO getPassengerTypeList(){
        List<PassengerTypeDTO> passengerTypes = passengerTypeDataService.findAll();
        SelectListDTO selectListDTO = new SelectListDTO();
        selectListDTO.setName("PassengerTypes");
        selectListDTO.setOptions(new ArrayList<SelectListOptionDTO>());
        for (PassengerTypeDTO passengerType : passengerTypes) {
            selectListDTO.getOptions()
                    .add(new SelectListOptionDTO(passengerType.getCode(), passengerType.getName()));
        }
        return selectListDTO;
    }
    
    @Override
    public SelectListDTO getDiscountTypeList(){
        List<DiscountTypeDTO> discountTypes = discountTypeDataService.findAll();
        SelectListDTO selectListDTO = new SelectListDTO();
        selectListDTO.setName("DiscountTypes");
        selectListDTO.setOptions(new ArrayList<SelectListOptionDTO>());
        for (DiscountTypeDTO discountType : discountTypes) {
            selectListDTO.getOptions()
                    .add(new SelectListOptionDTO(discountType.getCode(), discountType.getName()));
        }
        return selectListDTO;
    }
}
