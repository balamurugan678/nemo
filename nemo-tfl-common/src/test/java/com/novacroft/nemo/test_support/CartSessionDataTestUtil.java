package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;

/**
 * Utilities for Cart tests
 */
public final class CartSessionDataTestUtil {

    public static final Long CART_ID_1 = 1L;
    public static final String JSP_PAGE_NAME = "TestJsp.jsp";
    public static final String PAY_AS_YOU_GO_AUTO_TOP_UP = "payAsYouGoAutoTopUp";
    public static final Integer CART_TOTAL = 500;
    public static final Integer WEB_CREDIT_AVAILABLE_AMOUNT = 65;
    public static final Integer WEB_CREDIT_APPLY_AMOUNT = 15;

    public static CartSessionData getTestCartSessionDataDTO1() {
        return getTestCartSessionDataDTO(CART_ID_1);
    }
    
    public static CartSessionData getTestCartSessionDataDTO2() {
        return getTestCartSessionDataDTOForManageAutoTopUpMode(CART_ID_1);
    }

    public static CartSessionData getTestCartSessionDataDTOWithJSPPageName() {
        return getTestCartSessionDataDTOWithJSPPageName(CART_ID_1);
    }

    public static CartSessionData getTestCartSessionDataDTO(Long cartId) {
        return new CartSessionData(cartId);
    }
    
    public static CartSessionData getTestCartSessionDataDTOForQuickBuyMode(Long cartId) {
        CartSessionData cartSessionData = new CartSessionData(cartId);
        cartSessionData.setCartId(cartId);
        cartSessionData.setQuickBuyMode(Boolean.TRUE);
        return cartSessionData;
    }
    
    public static CartSessionData getTestCartSessionDataDTOForQuickBuyModeWithFalse(Long cartId) {
        CartSessionData cartSessionData = new CartSessionData(cartId);
        cartSessionData.setCartId(cartId);
        cartSessionData.setQuickBuyMode(Boolean.FALSE);
        return cartSessionData;
    }
    
    public static CartSessionData getTestCartSessionDataDTOForManageAutoTopUpMode(Long cartId) {
        CartSessionData cartSessionData = new CartSessionData(cartId);
        cartSessionData.setManageAutoTopUpMode(Boolean.TRUE);
        cartSessionData.setTicketType(PAY_AS_YOU_GO_AUTO_TOP_UP);
        return cartSessionData;
    }

    public static CartSessionData getTestCartSessionDataDTOWithJSPPageName(Long cartId) {
        CartSessionData sessionDTO = new CartSessionData(cartId);
        sessionDTO.setPageName(JSP_PAGE_NAME);
        return sessionDTO;
    }
    
    public static CartSessionData getTestCartSessionDataDTO3() {
        CartSessionData sessionDTO = new CartSessionData(CART_ID_1);
        sessionDTO.setCartTotal(CART_TOTAL);
        return sessionDTO;
    }
    
    public static CartSessionData getTestCartSessionDataDTO4() {
        CartSessionData sessionDTO = new CartSessionData(CART_ID_1);
        sessionDTO.setPageName(Page.TRANSFER_PRODUCT);
        sessionDTO.setTransferProductMode(Boolean.TRUE);
        return sessionDTO;
    }

    public static CartSessionData getTestCartSessionDataDTO5() {
        CartSessionData sessionDTO = new CartSessionData(CART_ID_1);
        sessionDTO.setWebCreditAvailableAmount(WEB_CREDIT_AVAILABLE_AMOUNT);
        sessionDTO.setWebCreditApplyAmount(WEB_CREDIT_APPLY_AMOUNT);
        return sessionDTO;
    }
}
