package com.novacroft.nemo.tfl.services.test_support;

import java.util.ArrayList;
import java.util.List;

import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.services.transfer.Cart;
import com.novacroft.nemo.tfl.services.transfer.ErrorResult;
import com.novacroft.nemo.tfl.services.transfer.Item;
import com.novacroft.nemo.tfl.services.transfer.ListResult;

public final class CartTestUtil {

    public static final Long CUSTOMER_ID = 1L;
    public static final Long CART_ID = 11L;
    public static final Long CART_ID_2 = 21L;
    public static final Long CART_ID_3 = 22L;
    public static final Long EXTERNAL_CUSTOMER_ID = 9999l;
    
    private CartTestUtil() {
    }
    
    public static List<Cart> getTestCartList() {
        List<Cart> cartList = new ArrayList<>();
        cartList.add(getTestCart1());
        return cartList ;
    }
    
    public static ListResult<Cart> getTestCartListResult() {
        ListResult<Cart> cartListResult = new ListResult<Cart>();
        List<Cart> cartList = new ArrayList<>();
        cartList.add(getTestCart1());
        cartListResult.setResultList(cartList);
        return cartListResult ;
    }
    
    public static ListResult<Cart> getTestCartListResultWithError() {
        ListResult<Cart> cartListResult = new ListResult<Cart>();
        ErrorResult errorResult = ErrorResultTestUtil.getTestErrorResult1();
        cartListResult.setErrors(errorResult);
        return cartListResult ;
    }

    public static Cart getTestCart1() {
        Cart cart = new Cart();
        cart.setId(CART_ID);
        cart.setCustomerId(CUSTOMER_ID);
        List<Item> items = new ArrayList<>();
        items.add(ItemTestUtil.getTestTravelCardItem1());
        cart.setCartItems(items);
        return cart;
    }
    
    public static Cart getTestCart2() {
        Cart cart = new Cart();
        cart.setId(com.novacroft.nemo.test_support.CartTestUtil.EXTERNAL_CART_ID_1);
        cart.setCustomerId(com.novacroft.nemo.test_support.CustomerTestUtil.EXTERNAL_CUSTOMER_ID);
        List<Item> items = new ArrayList<>();
        items.add(ItemTestUtil.getTestTravelCardItem1());
        cart.setCartItems(items);
        return cart;
    }
    
    public static Cart getTestCart3() {
        Cart cart = new Cart();
        cart.setId(CART_ID_3);
        cart.setCustomerId(EXTERNAL_CUSTOMER_ID);
        List<Item> items = new ArrayList<>();
        items.add(ItemTestUtil.getTestTravelCardItem1());
        items.add(ItemTestUtil.getTestPayAsYouGoItem());
        items.add(ItemTestUtil.getTestBusPassItem());
        cart.setCartItems(items);
        return cart;
    }
    
    public static Cart getTestCartWithPayAsYouGoItem() {
        Cart cart = new Cart();
        cart.setId(CART_ID_3);
        cart.setCustomerId(EXTERNAL_CUSTOMER_ID);
        List<Item> items = new ArrayList<>();
        items.add(ItemTestUtil.getTestPayAsYouGoItem());
        cart.setCartItems(items);
        return cart;
    }
    
    public static Cart getTestCartWithBusPassItem() {
        Cart cart = new Cart();
        cart.setId(CART_ID_3);
        cart.setCustomerId(EXTERNAL_CUSTOMER_ID);
        List<Item> items = new ArrayList<>();
        items.add(ItemTestUtil.getTestBusPassItem());
        cart.setCartItems(items);
        return cart;
    }

    public static List<CartDTO> getTestCartDTOList() {
        List<CartDTO> cartDTOList = new ArrayList<>();
        cartDTOList.add(com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithItem());
        cartDTOList.add(com.novacroft.nemo.test_support.CartTestUtil.getNewCartDTOWithItem1());        
        return cartDTOList ;
    }

    public static List<Long> getTestCartIdList() {
        List<Long> cartIdList = new ArrayList<>();
        cartIdList.add(CART_ID_2);
        cartIdList.add(CART_ID_3);
        return cartIdList;
    }

    public static Cart getTestCartWithError() {
        Cart cart = getTestCart3();
        ErrorResult errors = ErrorResultTestUtil.getTestErrorResult1();
        cart.setErrors(errors);
        return cart;
    }

}
