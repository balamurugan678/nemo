package com.novacroft.nemo.mock_cubic.service;

import com.novacroft.nemo.common.application_service.ChecksumService;
import com.novacroft.nemo.mock_cubic.command.CurrentActionListFileCmd;
import com.novacroft.nemo.mock_cubic.domain.CurrentAction;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * CurrentActionListService unit tests
 */
public class CurrentActionListServiceImplTest {
    protected static final String TEST_BLANK = "";
    protected static final String TEST_NULL = null;
    protected static final String TEST_PRESTIGE_ID = "1";
    protected static final String TEST_PICK_UP_LOCATION = "2";
    protected static final String TEST_REQUEST_SEQUENCE_NUMBER = "3";
    protected static final String TEST_PRODUCT_CODE = "4";
    protected static final String TEST_PPT_START_DATE = "20-Aug-2013";
    protected static final String TEST_PPT_EXPIRY_DATE = "21-Aug-2013";
    protected static final String TEST_PRE_PAY_VALUE = "5";
    protected static final String TEST_CURRENCY = "0";
    protected static final String TEST_FARE_PAID = "6";
    protected static final String TEST_PAYMENT_METHOD_CODE = "7";
    protected static final String TEST_AUTO_LOAD_STATE = "8";

    @Test
    public void isNotEmptyShouldReturnFalseWithNull() {
        CurrentActionListServiceImpl service = new CurrentActionListServiceImpl();
        CurrentAction CurrentAction = new CurrentAction();
        CurrentAction.setPrestigeId(TEST_NULL);
        assertFalse(service.isNotEmpty(CurrentAction));
    }

    @Test
    public void isNotEmptyShouldReturnFalseWithBlank() {
        CurrentActionListServiceImpl service = new CurrentActionListServiceImpl();
        CurrentAction CurrentAction = new CurrentAction();
        CurrentAction.setPrestigeId(TEST_BLANK);
        assertFalse(service.isNotEmpty(CurrentAction));
    }

    @Test
    public void isNotEmptyShouldReturnTrueWithValue() {
        CurrentActionListServiceImpl service = new CurrentActionListServiceImpl();
        CurrentAction CurrentAction = new CurrentAction();
        CurrentAction.setPrestigeId(TEST_PRESTIGE_ID);
        assertTrue(service.isNotEmpty(CurrentAction));
    }

    @Test
    public void toArrayShouldReturnArray() {
        CurrentAction CurrentAction = getTestCurrentAction();
        CurrentActionListServiceImpl service = new CurrentActionListServiceImpl();
        String[] expectedResult =
                {TEST_PRESTIGE_ID, TEST_REQUEST_SEQUENCE_NUMBER, TEST_PRODUCT_CODE, TEST_FARE_PAID, TEST_CURRENCY,
                        TEST_PAYMENT_METHOD_CODE, TEST_PRE_PAY_VALUE, TEST_PICK_UP_LOCATION, TEST_PPT_START_DATE,
                        TEST_PPT_EXPIRY_DATE, TEST_AUTO_LOAD_STATE};
        String[] result = service.toArray(CurrentAction);
        assertArrayEquals(expectedResult, result);
    }

    @Test
    public void shouldSerialiseToCsv() {
        ChecksumService mockChecksumService = mock(ChecksumService.class);
        when(mockChecksumService.calculateChecksum(any(byte[].class), anyInt())).thenReturn(0);
        List<CurrentAction> CurrentActions = getTestCurrentActionList();
        CurrentActionListServiceImpl service = new CurrentActionListServiceImpl();
        service.crc16ChecksumServiceImpl = mockChecksumService;
        String expectedResult = "1,3,4,6,0,7,5,2,20-Aug-2013,21-Aug-2013,8";
        String result = service.serialiseToCsv(CurrentActions);
        assertTrue(result.contains(expectedResult));
    }

    @Test
    public void shouldAddEmptyRecords() {
        CurrentActionListFileCmd cmd = new CurrentActionListFileCmd();
        CurrentActionListServiceImpl service = new CurrentActionListServiceImpl();
        service.addEmptyRecords(cmd, 8);
        assertTrue(8 == cmd.getCurrentActions().size());
    }

    protected CurrentAction getTestCurrentAction() {
        return new CurrentAction(TEST_PRESTIGE_ID, TEST_REQUEST_SEQUENCE_NUMBER, TEST_PRODUCT_CODE, TEST_FARE_PAID,
                TEST_CURRENCY, TEST_PAYMENT_METHOD_CODE, TEST_PRE_PAY_VALUE, TEST_PICK_UP_LOCATION, TEST_PPT_START_DATE,
                TEST_PPT_EXPIRY_DATE, TEST_AUTO_LOAD_STATE);
    }

    protected List<CurrentAction> getTestCurrentActionList() {
        List<CurrentAction> list = new ArrayList<CurrentAction>();
        list.add(getTestCurrentAction());
        return list;
    }
}
