package com.novacroft.nemo.mock_cubic.service;

import com.novacroft.nemo.common.application_service.ChecksumService;
import com.novacroft.nemo.mock_cubic.command.AutoLoadChangeCmd;
import com.novacroft.nemo.mock_cubic.domain.AutoLoadChange;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * AutoLoadChangeService unit tests
 */
public class AutoLoadChangeServiceImplTest {
    protected static final String TEST_BLANK = "";
    protected static final String TEST_NULL = null;
    protected static final String TEST_PRESTIGE_ID = "1";
    protected static final String TEST_PICK_UP_LOCATION = "2";
    protected static final String TEST_PICK_UP_TIME = "19-Aug-2013 12:00:00";
    protected static final String TEST_REQUEST_SEQUENCE_NUMBER = "3";
    protected static final String TEST_PRODUCT_CODE = "4";
    protected static final String TEST_PPT_START_DATE = "20-Aug-2013";
    protected static final String TEST_PPT_EXPIRY_DATE = "21-Aug-2013";
    protected static final String TEST_PRE_PAY_VALUE = "5";
    protected static final String TEST_CURRENCY = "0";
    protected static final String TEST_STATUS_OF_ATTEMPTED_ACTION = "OK";
    protected static final String TEST_FAILURE_REASON_CODE = "0";
    protected static final String TEST_PREVIOUS_AUTO_LOAD_CONFIGURATION = "1";
    protected static final String TEST_NEW_AUTO_LOAD_CONFIGURATION = "1";

    @Test
    public void isNotEmptyShouldReturnFalseWithNull() {
        AutoLoadChangeServiceImpl service = new AutoLoadChangeServiceImpl();
        AutoLoadChange autoLoadChange = new AutoLoadChange();
        autoLoadChange.setPrestigeId(TEST_NULL);
        assertFalse(service.isNotEmpty(autoLoadChange));
    }

    @Test
    public void isNotEmptyShouldReturnFalseWithBlank() {
        AutoLoadChangeServiceImpl service = new AutoLoadChangeServiceImpl();
        AutoLoadChange autoLoadChange = new AutoLoadChange();
        autoLoadChange.setPrestigeId(TEST_BLANK);
        assertFalse(service.isNotEmpty(autoLoadChange));
    }

    @Test
    public void isNotEmptyShouldReturnTrueWithValue() {
        AutoLoadChangeServiceImpl service = new AutoLoadChangeServiceImpl();
        AutoLoadChange autoLoadChange = new AutoLoadChange();
        autoLoadChange.setPrestigeId(TEST_PRESTIGE_ID);
        assertTrue(service.isNotEmpty(autoLoadChange));
    }

    @Test
    public void toArrayShouldReturnArray() {
        AutoLoadChange autoLoadChange = getTestAutoLoadChange();
        AutoLoadChangeServiceImpl service = new AutoLoadChangeServiceImpl();
        String[] expectedResult = {TEST_PRESTIGE_ID, TEST_PICK_UP_LOCATION, TEST_PICK_UP_TIME, TEST_REQUEST_SEQUENCE_NUMBER,
                TEST_PREVIOUS_AUTO_LOAD_CONFIGURATION, TEST_NEW_AUTO_LOAD_CONFIGURATION, TEST_STATUS_OF_ATTEMPTED_ACTION,
                TEST_FAILURE_REASON_CODE};
        String[] result = service.toArray(autoLoadChange);
        assertArrayEquals(expectedResult, result);
    }

    @Test
    public void shouldSerialiseToCsv() {
        ChecksumService mockChecksumService = mock(ChecksumService.class);
        when(mockChecksumService.calculateChecksum(any(byte[].class), anyInt())).thenReturn(0);
        List<AutoLoadChange> autoLoadChanges = getTestAutoLoadChangeList();
        AutoLoadChangeServiceImpl service = new AutoLoadChangeServiceImpl();
        service.crc16ChecksumServiceImpl = mockChecksumService;
        String expectedResult = "1,2,19-Aug-2013 12:00:00,3,1,1,OK,0";
        String result = service.serialiseToCsv(autoLoadChanges);
        assertTrue(result.contains(expectedResult));
    }

    @Test
    public void shouldAddEmptyRecords() {
        AutoLoadChangeCmd cmd = new AutoLoadChangeCmd();
        AutoLoadChangeServiceImpl service = new AutoLoadChangeServiceImpl();
        service.addEmptyRecords(cmd, 8);
        assertTrue(8 == cmd.getAutoLoadChanges().size());
    }

    protected AutoLoadChange getTestAutoLoadChange() {
        return new AutoLoadChange(TEST_PRESTIGE_ID, TEST_PICK_UP_LOCATION, TEST_PICK_UP_TIME, TEST_REQUEST_SEQUENCE_NUMBER,
                TEST_PREVIOUS_AUTO_LOAD_CONFIGURATION, TEST_NEW_AUTO_LOAD_CONFIGURATION, TEST_STATUS_OF_ATTEMPTED_ACTION,
                TEST_FAILURE_REASON_CODE);
    }

    protected List<AutoLoadChange> getTestAutoLoadChangeList() {
        List<AutoLoadChange> list = new ArrayList<AutoLoadChange>();
        list.add(getTestAutoLoadChange());
        return list;
    }
}
