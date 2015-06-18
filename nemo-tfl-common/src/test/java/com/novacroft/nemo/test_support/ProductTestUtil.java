package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.constant.TransferConstants;
import com.novacroft.nemo.tfl.common.domain.Product;
import com.novacroft.nemo.tfl.common.transfer.ProductDTO;

/**
 * Utilities for product tests
 */
public final class ProductTestUtil {
    public static final Long PRODUCT_ID_1 = 100L;
    public static final String PRODUCT_CODE_1 = "102";
    public static final String PRODUCT_NAME_1 = "product-name-1";
    public static final Integer TICKET_PRICE_1 = 121600;
    public static final Long PRODUCT_ID_2 = 110L;
    public static final String PRODUCT_CODE_2 = "103";
    public static final String PRODUCT_NAME_2 = "product-name-2";
    public static final Integer TICKET_PRICE_2 = 141600;
    public static final Integer START_ZONE_1 = 1;
    public static final Integer END_ZONE_1 = 5;
    public static final Integer INVALID_ZONE = -1;
    
    public static final Integer TICKET_PRICE_3 = 3860;

    public static ProductDTO getTestProductDTO1() {
        return getTestProductDTO(PRODUCT_ID_1, PRODUCT_CODE_1, PRODUCT_NAME_1, TICKET_PRICE_1);
    }

    public static ProductDTO getTestProductDTO2() {
        return getTestProductDTO(PRODUCT_ID_2, PRODUCT_CODE_2, PRODUCT_NAME_2, TICKET_PRICE_2);
    }
    
    public static ProductDTO getTestProductDTO3() {
        return getTestProductDTO(PRODUCT_ID_2, PRODUCT_CODE_2, PRODUCT_NAME_2, TICKET_PRICE_3);
    }
    
    public static ProductDTO getTestProductDTOWithZones(){
        return getTestProduct2(PRODUCT_ID_1, START_ZONE_1, END_ZONE_1, TICKET_PRICE_1, PRODUCT_NAME_1);
    }

    public static ProductDTO getTestProductDTO(Long productId, String productCode, String productName, Integer ticketPrice) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productId);
        productDTO.setProductCode(productCode);
        productDTO.setProductName(productName);
        productDTO.setTicketPrice(ticketPrice);
        return productDTO;
    }

    public static Product getTestProduct1() {
        return getTestProduct(PRODUCT_ID_1, PRODUCT_CODE_1, PRODUCT_NAME_1, TICKET_PRICE_1);
    }

    public static Product getTestProduct(Long productId, String productCode, String productName, Integer ticketPrice) {
        Product product = new Product();
        product.setId(productId);
        product.setProductCode(productCode);
        product.setProductName(productName);
        product.setTicketPrice(ticketPrice);
        return product;
    }

    public static ProductDTO getTestProductDTOOfTypeBus() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(PRODUCT_ID_1);
        productDTO.setProductCode(PRODUCT_CODE_1);
        productDTO.setProductName(PRODUCT_NAME_1);
        productDTO.setTicketPrice(TICKET_PRICE_1);
        productDTO.setType(TransferConstants.PRODUCT_TYPE_BUS);
        return productDTO;
    }

    public static ProductDTO getTestProductDTOTravelCard() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(PRODUCT_ID_1);
        productDTO.setProductCode(PRODUCT_CODE_1);
        productDTO.setProductName(PRODUCT_NAME_1);
        productDTO.setTicketPrice(TICKET_PRICE_1);
        productDTO.setType(TransferConstants.TRAVELCARD);
        return productDTO;
    }

    public static ProductDTO getTestProduct2(Long productItemId, int startZone, int endZone, int price, String duration) {
        return new ProductDTO(productItemId, price, duration, startZone, endZone);

    }
}
