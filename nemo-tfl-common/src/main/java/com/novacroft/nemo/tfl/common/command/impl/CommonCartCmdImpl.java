package com.novacroft.nemo.tfl.common.command.impl;


/**
 * TfL online shopping basket command specification
 */
public class CommonCartCmdImpl {
	
    protected CartCmdImpl cartCmd;
    protected CartItemCmdImpl cartItemCmd;
	
    public CartCmdImpl getCartCmd() {
        return cartCmd;
    }
    public void setCartCmd(CartCmdImpl cartCmd) {
        this.cartCmd = cartCmd;
    }
    public CartItemCmdImpl getCartItemCmd() {
        return cartItemCmd;
    }
    public void setCartItemCmd(CartItemCmdImpl cartItemCmd) {
        this.cartItemCmd = cartItemCmd;
    }
		
}
