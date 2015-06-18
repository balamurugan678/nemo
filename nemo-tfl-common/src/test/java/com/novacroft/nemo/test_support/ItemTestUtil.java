package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.test_support.CartTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CartTestUtil.PURCHASE_CART_ID_1;

import com.novacroft.nemo.tfl.common.domain.Item;
import com.novacroft.nemo.tfl.common.domain.ProductItem;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeItemDTO;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;

/**
 * Utilities for item tests
 */
public final class ItemTestUtil {
    public static final Long ITEM_ID = 30L;
    public static final Long ITEM_ID1 = 40L;
    public static final Integer PRICE_1 = 565;
    public static final Integer PRICE_2 = 865;
    public static final String DTO = "DTO";
    public static final String DOMAIN_PACKAGE = "com.novacroft.nemo.tfl.common.domain";
    public static final String TRANSFER_PACKAGE = "com.novacroft.nemo.tfl.common.transfer";
    public static final String DOT = ".";
    public static final String BLANK = "";
    public static final Long ZERO = 0L;
    public static final Boolean ticketBackdated = true;
    public static final Long backdatedRefundReasonId = 1L;
    public static final Boolean ticketBackdatedFalse = false;
    public static final Long backdatedRefundReasonIdZero = 0L;
    public static final Boolean deceasedCustomer = true;
	public static final String ANNUAL_TRAVEL_CARD_NAME = "Annual Travelcard Zones";
    public static final String ANNUAL_BUS_PASS_NAME = "Annual Bus Pass";
    public static final String PAY_AS_YOU_GO_NAME = "Pay As You Go";
	
    public static ItemDTO getTestItemDTO1() {
        return getTestItemDTO(ITEM_ID, CARD_ID_1, PURCHASE_CART_ID_1, PRICE_1);
    }

    public static ProductItemDTO getTestProductItemDTO1() {
        return getTestProductItemDTO(ITEM_ID, CARD_ID_1, PURCHASE_CART_ID_1, PRICE_1, ANNUAL_TRAVEL_CARD_NAME);
    }
    
    public static ProductItem getTestProductItem(){
        ProductItem productItem = new ProductItem();
        productItem.setId(ITEM_ID);
        productItem.setCardId(CARD_ID_1);
        productItem.setPrice(PRICE_1);
        return productItem;
    }

    public static ProductItemDTO getTestProductItemDTO2() {
        return getTestProductItemDTO(ITEM_ID1, CARD_ID_1, PURCHASE_CART_ID_1, PRICE_2, ANNUAL_BUS_PASS_NAME);
    }
    
	public static ProductItemDTO getTestProductItemDTO3() {
        return getTestProductItemDTO(ITEM_ID1, CARD_ID_1, PURCHASE_CART_ID_1, PRICE_2, PAY_AS_YOU_GO_NAME);
    }
	
    public static ProductItemDTO getTestProductItemDTOForBackDateAndDeceased() {
        return getTestProductItemDTO(ITEM_ID1, CARD_ID_1, PURCHASE_CART_ID_1, ticketBackdated, backdatedRefundReasonId, deceasedCustomer);
    }
    
    public static ProductItemDTO getTestProductItemDTOForDeceasedCustomer() {
        return getTestProductItemDTO(ITEM_ID1, CARD_ID_1, PURCHASE_CART_ID_1, ticketBackdatedFalse, backdatedRefundReasonIdZero, deceasedCustomer);
    }

    public static PayAsYouGoItemDTO getTestPayAsYouGoItemDTO1() {
        return getTestPayAsYouGoItem(ITEM_ID, CARD_ID_1, PURCHASE_CART_ID_1, PRICE_1);
    }

    public static PayAsYouGoItemDTO getTestPayAsYouGoItemDTO2() {
        return getTestPayAsYouGoItem(ITEM_ID1, CARD_ID_1, PURCHASE_CART_ID_1, PRICE_2);
    }

    private static PayAsYouGoItemDTO getTestPayAsYouGoItem(Long itemId, Long cardId, Long cartId, Integer price) {
        PayAsYouGoItemDTO dto = new PayAsYouGoItemDTO();
        dto.setId(itemId);
        dto.setCardId(cardId);
        dto.setCartId(cartId);
        dto.setPrice(price);
        return dto;
    }

    private static ProductItemDTO getTestProductItemDTO(Long itemId, Long cardId, Long cartId, Integer price, String name) {
        ProductItemDTO dto = new ProductItemDTO();
        dto.setId(itemId);
        dto.setCardId(cardId);
        dto.setCartId(cartId);
        dto.setPrice(price);
		dto.setName(name);
        return dto;
    }
    
    private static ProductItemDTO getTestProductItemDTO(Long itemId, Long cardId, Long cartId, Boolean ticketBackdated, Long backdatedRefundReasonId,
                    Boolean deceasedCustomer) {
        ProductItemDTO dto = new ProductItemDTO();
        dto.setId(itemId);
        dto.setCardId(cardId);
        dto.setCartId(cartId);
        dto.setTicketBackdated(ticketBackdated);
        dto.setBackdatedRefundReasonId(backdatedRefundReasonId);
        dto.setDeceasedCustomer(deceasedCustomer);
        return dto;
    }

    public static ItemDTO getTestItemDTO(Long itemdId, Long cardId, Long cartId, Integer price) {
        ItemDTO dto = new ProductItemDTO();
        dto.setId(itemdId);
        dto.setCardId(cardId);
        dto.setCartId(cartId);
        dto.setPrice(price);
        return dto;
    }

    public static Item getTestItem1() {
        return getTestItem(ITEM_ID, CARD_ID_1, PURCHASE_CART_ID_1, PRICE_1);
    }

    public static Item getTestItem(Long itemId, Long cardId, Long cartId, Integer price) {
        Item item = new Item();
        item.setId(itemId);
        item.setCardId(cardId);
        item.setPrice(price);
        return item;
    }

    public static ItemDTO getTestGoodwillItem1() {
        return getTestGoodwillItem(ITEM_ID, CARD_ID_1, PRICE_2);
    }

    public static ItemDTO getTestGoodwillItem(Long id, Long cardId, int price) {
        GoodwillPaymentItemDTO item = new GoodwillPaymentItemDTO();
        item.setId(id);
        item.setCardId(cardId);
        item.setPrice(price);
        return item;
    }

    public static AutoTopUpConfigurationItemDTO getTestAutoTopUpItemDTO() {
        return getTestAutoTopUpItem(ITEM_ID, CARD_ID_1, PURCHASE_CART_ID_1, PRICE_1);
    }
    
    private static AutoTopUpConfigurationItemDTO getTestAutoTopUpItem(Long itemId, Long cardId, Long cartId,Integer price) {
    	AutoTopUpConfigurationItemDTO dto = new AutoTopUpConfigurationItemDTO();
        dto.setId(itemId);
        dto.setCardId(cardId);
        dto.setCartId(cartId);
        dto.setPrice(price);
        return dto;
    }

    public static ProductItemDTO getNewProductItemDTO1() {
        return getTestProductItemDTO(ITEM_ID1, CARD_ID_1, PURCHASE_CART_ID_1, ticketBackdatedFalse, backdatedRefundReasonIdZero, deceasedCustomer);
    }

    
    private static AdministrationFeeItemDTO getTestAdministrationFeeItem(Long itemId, Long cardId, Long cartId, Integer price) {
        AdministrationFeeItemDTO dto = new AdministrationFeeItemDTO();
        dto.setId(itemId);
        dto.setCardId(cardId);
        dto.setCartId(cartId);
        dto.setPrice(price);
        return dto;
    }
    
    public static AdministrationFeeItemDTO getTestAdministrationFeeItemDTO1() {
        return getTestAdministrationFeeItem(ITEM_ID1, CARD_ID_1, PURCHASE_CART_ID_1, PRICE_1);
    }

}
