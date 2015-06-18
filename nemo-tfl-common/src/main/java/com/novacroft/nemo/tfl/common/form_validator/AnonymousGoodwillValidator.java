package com.novacroft.nemo.tfl.common.form_validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;

@Component("AnonymousGoodwillValidator")
public class AnonymousGoodwillValidator extends BaseValidator {

	public static String NO_GOODWIL_ITEM_ADDED = "nogoodwillitemadded";
	public static String STATION_ID = "stationId";
	
	@Override
	public boolean supports(Class<?> clazz) {
		return CartCmdImpl.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CartCmdImpl cartCmdImpl = (CartCmdImpl)target;
		if(!validateNumberOfCartItems(cartCmdImpl)){
			errors.reject(NO_GOODWIL_ITEM_ADDED);
		}
		if(!validateStationSelected(cartCmdImpl)){
			rejectIfMandatorySelectFieldEmpty(errors, STATION_ID);
		}
	}

	protected boolean validateNumberOfCartItems(CartCmdImpl cartCmdImpl){
		return !cartCmdImpl.getCartDTO().getCartItems().isEmpty();
	}
	
	protected boolean validateStationSelected(CartCmdImpl cartCmdImpl){
		return cartCmdImpl.getStationId() != null;
	}
}
