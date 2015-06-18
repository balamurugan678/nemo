package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.ProductItem;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

import java.util.List;

/**
 * product item data service specification
 */
public interface ProductItemDataService extends BaseDataService<ProductItem, ProductItemDTO> {
    List<ProductItemDTO> findByCartIdAndCardId(Long cartId, Long cardId);

    List<ProductItemDTO> findByCustomerOrderId(Long customerOrderId);

    List<ProductItemDTO> findAllByExample(ProductItem productItem);



    
}
