package com.novacroft.nemo.tfl.common.form_validator;

import static org.junit.Assert.*;
import static com.novacroft.nemo.test_support.CartTestUtil.*;
import static com.novacroft.nemo.test_support.CartCmdTestUtil.*;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;

public class CancelAndSurrenderCardRefundCartValidatorTest {
    
    private CancelAndSurrenderCardRefundCartValidator validator;
    private CartCmdImpl cmd;

    @Before
    public void setUp() throws Exception {
        validator = new CancelAndSurrenderCardRefundCartValidator();
        cmd = new CartCmdImpl();
    }

    @Test
    public void isAnyOneOfSelectedProductsExpiredOnDateOfRefund() {
        cmd = getTestCartCmd14();
        cmd.setCartDTO(getNewCartDTOWithProductItem());
        assertTrue(validator.isAnyOneOfSelectedProductsExpiredOnDateOfRefund(cmd));
    }
    
    @Test
    public void isNotAnyOneOfSelectedProductsExpiredOnDateOfRefund() {
        cmd = getTestCartCmd14();
        assertFalse(validator.isAnyOneOfSelectedProductsExpiredOnDateOfRefund(cmd));
    }
    
    @Test
    public void isNotAnyOneOfSelectedProductsExpiredOnDateOfRefundIfTicketIsUsed(){
        cmd = getTestCartCmd14();
        cmd.setCartDTO(getNewCartWithUsedTicketItem());
        assertFalse(validator.isAnyOneOfSelectedProductsExpiredOnDateOfRefund(cmd));
    }


}
