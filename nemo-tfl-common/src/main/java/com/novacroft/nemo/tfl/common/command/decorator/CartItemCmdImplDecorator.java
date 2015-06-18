package com.novacroft.nemo.tfl.common.command.decorator;

import com.novacroft.nemo.tfl.common.command.impl.CartItemCmdImpl;

public class CartItemCmdImplDecorator extends CartItemCmdImpl {
    private Integer itemsInThisCart;

    public Integer getItemsInThisCart() {
        return itemsInThisCart;
    }

    public void setItemsInThisCart(Integer itemsInthisCart) {
        this.itemsInThisCart = itemsInthisCart;
    }

}
