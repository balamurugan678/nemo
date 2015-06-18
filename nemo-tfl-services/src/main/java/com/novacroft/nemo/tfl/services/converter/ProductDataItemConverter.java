package com.novacroft.nemo.tfl.services.converter;

import org.springframework.validation.Errors;

import com.novacroft.nemo.tfl.common.transfer.ProductDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.services.transfer.ErrorResult;
import com.novacroft.nemo.tfl.services.transfer.Item;
import com.novacroft.nemo.tfl.services.transfer.PrePaidTicket;

public interface ProductDataItemConverter {
    PrePaidTicket convertToTravelCard(ProductDTO productDTO);

    ProductDTO convertToProductDTO(PrePaidTicket prePaidTicket);
    
    ProductItemDTO convertToProductItemDTO(PrePaidTicket prePaidTicket);

    Item convertToItem(ProductDTO productDTO);
    
    Item convertToItem(ProductDTO productDTO, ProductItemDTO productItemDTO);
    
    ErrorResult convertToErrorResult (Errors errors);
}
