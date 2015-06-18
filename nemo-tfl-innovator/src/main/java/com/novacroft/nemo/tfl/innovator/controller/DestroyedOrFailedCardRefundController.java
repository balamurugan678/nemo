package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.tfl.common.constant.PageCommand.CART;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.novacroft.nemo.common.validator.BaseValidator;
import com.novacroft.nemo.tfl.common.form_validator.RefundCartValidator;

@Controller
@SessionAttributes(CART)
public abstract class DestroyedOrFailedCardRefundController extends RefundController {
    
    @Autowired
    protected RefundCartValidator refundCartValidator;
    
    @Override
    protected BaseValidator getValidator() {
    	return refundCartValidator;
    }

    @Override
    @InitBinder
    protected void initBinder(final ServletRequestDataBinder binder) {
    	super.initBinder(binder);
    }

    @InitBinder("ajaxCartCmdImpl")
    protected void initAjaxCartCmdImplBinder(WebDataBinder binder) {
        binder.setValidator(addUnlistedProductValidator);
    }
}
