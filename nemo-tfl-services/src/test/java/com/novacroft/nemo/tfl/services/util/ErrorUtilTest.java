package com.novacroft.nemo.tfl.services.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.services.test_support.ErrorResultTestUtil;
import com.novacroft.nemo.tfl.services.transfer.Error;
import com.novacroft.nemo.tfl.services.transfer.ErrorResult;

public class ErrorUtilTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testAddErrorToList() {
        ErrorResult errorResult = new ErrorResult();
        Error error = new Error();
        ErrorResult errorResultResponse = ErrorUtil.addErrorToList(errorResult, error);
        assertNotNull(errorResultResponse);
        assertEquals(1, errorResultResponse.getErrors().size());
        errorResultResponse = ErrorUtil.addErrorToList(errorResult, error);
        assertNotNull(errorResultResponse);
        assertEquals(2, errorResultResponse.getErrors().size());
    }

    @Test
    public void getError() {
        assertNotNull(ErrorUtil.getError(ErrorResultTestUtil.ERROR_FIELD_1, ErrorResultTestUtil.ERROR_DESCRIPTION_1));
    }

    @Test
    public void addErrorToList() {
        ErrorResult errorResult = new ErrorResult();
        ErrorResult errorResultResponse = ErrorUtil.addErrorToList(errorResult, ErrorResultTestUtil.ERROR_ID, ErrorResultTestUtil.ERROR_FIELD_1,
                        ErrorResultTestUtil.ERROR_DESCRIPTION_1);
        assertNotNull(errorResultResponse);
        assertEquals(1, errorResultResponse.getErrors().size());
        
        errorResultResponse = ErrorUtil.addErrorToList(errorResult, ErrorResultTestUtil.ERROR_ID, ErrorResultTestUtil.ERROR_FIELD_1,
                        ErrorResultTestUtil.ERROR_DESCRIPTION_1);
        assertNotNull(errorResultResponse);
        assertEquals(2, errorResultResponse.getErrors().size());
    }

    @Test
    public void testHasErrorsShouldReturnFalseDueToNullErrorResult() {
        assertFalse(ErrorUtil.hasErrors(null));
    }

    @Test
    public void testHasErrorsShouldReturnFalseDueToNullErrors() {
        assertFalse(ErrorUtil.hasErrors(new ErrorResult()));
    }

    @Test
    public void testHasErrorsShouldReturnFalseDueToEmptyErrors() {
        ErrorResult errorResult = new ErrorResult();
        errorResult.setErrors(new ArrayList<Error>());
        assertFalse(ErrorUtil.hasErrors(errorResult));
    }

    @Test
    public void testHasErrorsShouldReturnTrue() {
        ErrorResult errorResult = ErrorUtil.addErrorToList(null, ErrorResultTestUtil.ERROR_ID, ErrorResultTestUtil.ERROR_FIELD_1,
                        ErrorResultTestUtil.ERROR_DESCRIPTION_1);
        assertTrue(ErrorUtil.hasErrors(errorResult));
    }

}
