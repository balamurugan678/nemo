package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.Product;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;
import org.springframework.stereotype.Component;

/**
 * Transform between product entity and DTO.
 */
@Component(value = "productConverter")
public class ProductConverterImpl extends BaseDtoEntityConverterImpl<Product, ProductDTO> {
    @Override
    protected ProductDTO getNewDto() {
        return new ProductDTO();
    }
}
