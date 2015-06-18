package com.novacroft.nemo.tfl.common.util;

import java.util.ArrayList;
import java.util.List;

import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;

/**
 * Pay as you go item validator utilities
 */
public final class PayAsYouGoItemValidatorUtil {

    
    public static Integer getPayAsYouGoItemPrice(CartCmdImpl cartCmdImpl) {
		for (ItemDTO itemDTO : cartCmdImpl.getCartDTO().getCartItems()) {
			if (itemDTO instanceof PayAsYouGoItemDTO) {
				return itemDTO.getPrice();
			}
		}
		return -1;
	}

    
	public static String getPayAsYouGoItemField(CartCmdImpl cartCmdImpl) {
		List<ItemDTO> cartItems = new ArrayList<ItemDTO>();
		cartItems.addAll(cartCmdImpl.getCartDTO().getCartItems());
		for (int i = 0; i < cartItems.size(); i++) {
			ItemDTO itemDTO = cartItems.get(i);
			if (itemDTO instanceof PayAsYouGoItemDTO) {
				return "cartDTO.cartItems[" + i + "].price";
			}
		}
		Long payAsYouGoId = -1L;
		for (ItemDTO itemDTO : cartCmdImpl.getCartDTO().getCartItems()) {
			if (itemDTO instanceof PayAsYouGoItemDTO) {
				payAsYouGoId = itemDTO.getId();
			}
		}
		if (payAsYouGoId != -1L) {
			return "cartDTO.cartItems.id[" + payAsYouGoId + "].price";
		}
		return "";
	}
	
	
	public static boolean payAsYouGoItemFieldAvailable(CartCmdImpl cartCmdImpl) {
    	for (ItemDTO itemDTO : cartCmdImpl.getCartDTO().getCartItems()) {
			if (itemDTO instanceof PayAsYouGoItemDTO) {
				return true;
			}
		}
		return false;
    }
    
    public static boolean cartItemListNotEmpty(CartCmdImpl cartCmdImpl) {
        return cartCmdImpl.getCartItemList() != null;
    }
    
    public static boolean cartDTONotEmpty(CartCmdImpl cartCmdImpl) {
        return cartCmdImpl.getCartDTO() != null;
    }
}
