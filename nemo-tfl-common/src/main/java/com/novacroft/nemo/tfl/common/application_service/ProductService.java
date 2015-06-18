package com.novacroft.nemo.tfl.common.application_service;

import java.util.List;

import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.PrePaidTicketDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;


/**
 * Specification for product service
 */
public interface ProductService {
    
    String getProductName(ProductItemDTO productItem);
    
    List<ProductDTO> getProductsByLikeDurationAndStartZone(String productNameLike, Integer startZone);
    
    List<ProductDTO> getProductsByStartZoneForOddPeriod(Integer startZone, String type);

    ProductDTO getProductByFromAndToDurationAndZonesAndPassengerTypeAndDiscountType(CartItemCmdImpl cartItem);

    ProductDTO getProductByFromDurationAndZonesAndPassengerTypeAndDiscountType(CartItemCmdImpl cartItem);

    String getDurationFromPrePaidTicketDTO(PrePaidTicketDTO prePaidTicketDTO);

    ProductDTO getProductByFromAndToDurationAndZonesAndPassengerTypeAndDiscountType(CartItemCmdImpl cartItem, String substitutionTravelCardType);

    SelectListDTO getPassengerTypeList();
    
    SelectListDTO getDiscountTypeList();
}
