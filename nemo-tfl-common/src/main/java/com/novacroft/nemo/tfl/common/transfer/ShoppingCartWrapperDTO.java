package com.novacroft.nemo.tfl.common.transfer;

@Deprecated
public class ShoppingCartWrapperDTO {
    
    private ShoppingCartDTO cart;
    private ShoppingCartItemDTO item;
    public ShoppingCartDTO getCart() {
        return cart;
    }
    public void setCart(ShoppingCartDTO cart) {
        this.cart = cart;
    }
    public ShoppingCartItemDTO getItem() {
        return item;
    }
    public void setItem(ShoppingCartItemDTO item) {
        this.item = item;
    }

}
