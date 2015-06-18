package com.novacroft.nemo.tfl.common.util;

import static com.novacroft.nemo.tfl.common.constant.CartAttribute.BUS_PASS;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.TRAVEL_CARD;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.novacroft.nemo.tfl.common.constant.CartAttribute;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeItemDTO;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CardRefundableDepositItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ShippingMethodItemDTO;

public final class CartUtil {

    private static final Integer ZERO = Integer.valueOf(0);
	private static final Integer ONE = Integer.valueOf(1);

    public static CartSessionData getCartSessionDataDTOFromSession(HttpSession session) {
        return (CartSessionData) session.getAttribute(CartAttribute.SESSION_ATTRIBUTE_SHOPPING_CART_DATA);
    }

    public static void addCartSessionDataDTOToSession(HttpSession session, CartSessionData cartSessionData) {
        session.setAttribute(CartAttribute.SESSION_ATTRIBUTE_SHOPPING_CART_DATA, cartSessionData);
    }

    public static void removeCartSessionDataDTOFromSession(HttpSession session) {
        session.removeAttribute(CartAttribute.SESSION_ATTRIBUTE_SHOPPING_CART_DATA);
    }

    private CartUtil() {
    }

    public static Boolean isItemDTOAdministrationFeeItemDTO(ItemDTO itemDTO) {
        return itemDTO.getClass() == AdministrationFeeItemDTO.class;
    }

    public static Boolean isItemDTOPayAsYouGoItemDTO(ItemDTO itemDTO) {
        return itemDTO.getClass() == PayAsYouGoItemDTO.class;
    }

    public static Boolean isItemDTOProductItemDTO(ItemDTO itemDTO) {
        return itemDTO.getClass() == ProductItemDTO.class;
    }

    public static Boolean isItemDTOGoodwillPaymentItemDTO(ItemDTO itemDTO) {
        return itemDTO.getClass() == GoodwillPaymentItemDTO.class;
    }

    public static Boolean isItemDTOAutoTopUpItemDTO(ItemDTO itemDTO) {
        return itemDTO.getClass() == AutoTopUpConfigurationItemDTO.class;
    }
    
    public static Boolean isItemDTOCardRefundableDepositItemDTO(ItemDTO itemDTO) {
        return itemDTO.getClass() == CardRefundableDepositItemDTO.class;
    }
    
    public static Boolean isItemDTOCardShippingMethodItemDTO(ItemDTO itemDTO) {
        return itemDTO.getClass() == ShippingMethodItemDTO.class;
    }
    
    public static Integer getCartTotal(List<ItemDTO> cartItems) {
        int amount = 0;
        for (ItemDTO item : cartItems) {
            if (null != item.getPrice()) {
                amount += item.getPrice();
            }
        }
        return amount;
    }

    public static Integer getCartRefundTotal(List<ItemDTO> cartItems) {
        int refundTotalAmount = 0;
        int travelCardWithBusPassAmount = 0;
        int administrationFeeAmount = 0;
        int payAsYouGoCreditAmount = 0;
        int goodWillCreditAmount = 0;

        for (ItemDTO itemDTO : cartItems) {
            if (itemDTO instanceof ProductItemDTO) {
                travelCardWithBusPassAmount += getRefundAmountFromItemDTO(itemDTO);
            }
            if (itemDTO instanceof GoodwillPaymentItemDTO) {
                goodWillCreditAmount += getPriceFromItemDTO(itemDTO);
            }
            if (itemDTO instanceof AdministrationFeeItemDTO) {
                administrationFeeAmount = getPriceFromItemDTO(itemDTO);
            }
            if (itemDTO instanceof PayAsYouGoItemDTO) {
                payAsYouGoCreditAmount = getPriceFromItemDTO(itemDTO);
            }
        }
        refundTotalAmount = (travelCardWithBusPassAmount - administrationFeeAmount) + payAsYouGoCreditAmount + goodWillCreditAmount;
        return setCartRefundTotalToZeroIfRefundTotalAmountIsNegative(refundTotalAmount);
    }

    protected static int getPriceFromItemDTO(ItemDTO itemDTO) {
        return itemDTO.getPrice();
    }

    protected static int getRefundAmountFromItemDTO(ItemDTO itemDTO) {
        if (itemDTO instanceof ProductItemDTO) {
            ProductItemDTO productItemDTO = (ProductItemDTO) itemDTO;
            if (productItemDTO.getRefund() != null) {
                return productItemDTO.getRefund().getRefundAmount() != null ? productItemDTO.getRefund().getRefundAmount().intValue() : null;
            }
        }
        return itemDTO.getPrice();
    }

    public static Integer getCardRefundableDepositAmount(List<ItemDTO> cartItems) {
        CardRefundableDepositItemDTO item = (CardRefundableDepositItemDTO) getItemDTOByClass(cartItems, CardRefundableDepositItemDTO.class);
        return null != item ? item.getPrice() : ZERO;
    }

    public static CardRefundableDepositItemDTO getCardRefundableDepositItem(List<ItemDTO> cartItems) {
        return (CardRefundableDepositItemDTO) getItemDTOByClass(cartItems, CardRefundableDepositItemDTO.class);
    }

    public static Integer getAutoTopUpAmount(List<ItemDTO> cartItems) {
        AutoTopUpConfigurationItemDTO item = (AutoTopUpConfigurationItemDTO) getItemDTOByClass(cartItems, AutoTopUpConfigurationItemDTO.class);
        return null != item ? item.getAutoTopUpAmount() : ZERO;
    }

    public static PayAsYouGoItemDTO getPayAsYouGoItem(List<ItemDTO> cartItems) {
        return (PayAsYouGoItemDTO) getItemDTOByClass(cartItems, PayAsYouGoItemDTO.class);
    }

    public static boolean isAutoTopUpPresent(List<ItemDTO> cartItems) {
        return getAutoTopUpAmount(cartItems) != 0;
    }

    public static AutoTopUpConfigurationItemDTO getAutoTopUpItem(List<ItemDTO> cartItems) {
        return (AutoTopUpConfigurationItemDTO) getItemDTOByClass(cartItems, AutoTopUpConfigurationItemDTO.class);
    }

    public static ShippingMethodItemDTO getShippingMethodItem(List<ItemDTO> cartItems) {
        return (ShippingMethodItemDTO) getItemDTOByClass(cartItems, ShippingMethodItemDTO.class);
    }

    public static Integer getShippingMethodAmount(List<ItemDTO> cartItems) {
        ShippingMethodItemDTO item = getShippingMethodItem(cartItems);
        return null != item ? item.getPrice() : ZERO;
    }

    public static AdministrationFeeItemDTO getAdministrationFeeItem(List<ItemDTO> cartItems) {
        return (AdministrationFeeItemDTO) getItemDTOByClass(cartItems, AdministrationFeeItemDTO.class);
    }

    protected static ItemDTO getItemDTOByClass(List<ItemDTO> cartItems, Class<? extends ItemDTO> itemSubclass) {
        for (ItemDTO item : cartItems) {
            if (item.getClass().equals(itemSubclass)) {
                return item;
            }
        }
        return null;
    }
	
    public static Boolean isCartContainsBusPassAndTravelCard(List<ItemDTO> cartItems) {
        Boolean annualBusPass = Boolean.FALSE;
        Boolean travelCard = Boolean.FALSE;
        if (cartItems.size() > ONE) {
            for (ItemDTO itemDTO : cartItems) {
                if (itemDTO.getName().contains(BUS_PASS)) {
                    annualBusPass = Boolean.TRUE;
                }
                if (itemDTO.getName().contains(TRAVEL_CARD)) {
                    travelCard = Boolean.TRUE;
                }
            }
        }
        return (annualBusPass && travelCard);
    }
    
    protected static int setCartRefundTotalToZeroIfRefundTotalAmountIsNegative(Integer cartRefundTotalAmount) {
        if (cartRefundTotalAmount <= 0) {
        	cartRefundTotalAmount = 0;
        }
        return cartRefundTotalAmount;
    }
    
    public static Boolean isDestroyedOrFaildCartType(String cartType) {
        return StringUtils.equalsIgnoreCase(CartType.DESTROYED_CARD_REFUND.code(), cartType) ||
                        StringUtils.equalsIgnoreCase(CartType.FAILED_CARD_REFUND.code(), cartType);
    }
}
