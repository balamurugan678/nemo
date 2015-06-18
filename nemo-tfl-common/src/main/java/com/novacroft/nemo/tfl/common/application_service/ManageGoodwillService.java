package com.novacroft.nemo.tfl.common.application_service;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;

public interface ManageGoodwillService{
	CartCmdImpl addGoodwill(CartCmdImpl cartCmdImpl, BindingResult result, String refundCartType, Model model);
	
	CartCmdImpl deleteGoodwill(CartCmdImpl cartCmdImpl, long cartItemId, String refundCartType, Model model);
}
