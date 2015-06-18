package com.novacroft.nemo.mock_cubic.service;

import com.novacroft.nemo.common.application_service.ChecksumService;
import com.novacroft.nemo.mock_cubic.command.AdHocDistributionCmd;
import com.novacroft.nemo.mock_cubic.domain.AdHocDistribution;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * AdHocDistributionService unit tests
 */
public class AdHocDistributionServiceImplTest {

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

    @Test
    public void isNotEmptyShouldReturnFalseWithNull() {
        AdHocDistributionServiceImpl service = new AdHocDistributionServiceImpl();
        AdHocDistribution adHocDistribution = new AdHocDistribution();
        adHocDistribution.setPrestigeId(TEST_NULL);
        assertFalse(service.isNotEmpty(adHocDistribution));
    }

    @Test
    public void isNotEmptyShouldReturnFalseWithBlank() {
        AdHocDistributionServiceImpl service = new AdHocDistributionServiceImpl();
        AdHocDistribution adHocDistribution = new AdHocDistribution();
        adHocDistribution.setPrestigeId(TEST_BLANK);
        assertFalse(service.isNotEmpty(adHocDistribution));
    }

    @Test
    public void isNotEmptyShouldReturnTrueWithValue() {
        AdHocDistributionServiceImpl service = new AdHocDistributionServiceImpl();
        AdHocDistribution adHocDistribution = new AdHocDistribution();
        adHocDistribution.setPrestigeId(TEST_PRESTIGE_ID);
        assertTrue(service.isNotEmpty(adHocDistribution));
    }

    @Test
    public void toArrayShouldReturnArray() {
        AdHocDistribution adHocDistribution = getTestAdHocDistribution();
        AdHocDistributionServiceImpl service = new AdHocDistributionServiceImpl();
        String[] expectedResult =
                {TEST_PRESTIGE_ID, TEST_PICK_UP_LOCATION, TEST_PICK_UP_TIME, TEST_REQUEST_SEQUENCE_NUMBER, TEST_PRODUCT_CODE,
                        TEST_PPT_START_DATE, TEST_PPT_EXPIRY_DATE, TEST_PRE_PAY_VALUE, TEST_CURRENCY,
                        TEST_STATUS_OF_ATTEMPTED_ACTION, TEST_FAILURE_REASON_CODE};
        String[] result = service.toArray(adHocDistribution);
        assertArrayEquals(expectedResult, result);
    }

    @Test
    public void shouldSerialiseToCsv() {
        ChecksumService mockChecksumService = mock(ChecksumService.class);
        when(mockChecksumService.calculateChecksum(any(byte[].class), anyInt())).thenReturn(0);
        List<AdHocDistribution> adHocDistributions = getTestAdHocDistributionList();
        AdHocDistributionServiceImpl service = new AdHocDistributionServiceImpl();
        service.crc16ChecksumServiceImpl = mockChecksumService;
        String expectedResult = "1,2,19-Aug-2013 12:00:00,3,4,20-Aug-2013,21-Aug-2013,5,0,OK,0";
        String result = service.serialiseToCsv(adHocDistributions);
        assertTrue(result.contains(expectedResult));
    }

    @Test
    public void shouldAddEmptyRecords() {
        AdHocDistributionCmd cmd = new AdHocDistributionCmd();
        AdHocDistributionServiceImpl service = new AdHocDistributionServiceImpl();
        service.addEmptyRecords(cmd, 8);
        assertTrue(8 == cmd.getAdHocDistributions().size());
    }

    protected AdHocDistribution getTestAdHocDistribution() {
        return new AdHocDistribution(TEST_PRESTIGE_ID, TEST_PICK_UP_LOCATION, TEST_PICK_UP_TIME, TEST_REQUEST_SEQUENCE_NUMBER,
                TEST_PRODUCT_CODE, TEST_PPT_START_DATE, TEST_PPT_EXPIRY_DATE, TEST_PRE_PAY_VALUE, TEST_CURRENCY,
                TEST_STATUS_OF_ATTEMPTED_ACTION, TEST_FAILURE_REASON_CODE);
    }

    protected List<AdHocDistribution> getTestAdHocDistributionList() {
        List<AdHocDistribution> list = new ArrayList<AdHocDistribution>();
        list.add(getTestAdHocDistribution());
        return list;
    }
}
